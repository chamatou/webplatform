package cn.chamatou.commons.data.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import cn.chamatou.commons.data.utils.JDBCUtil;
import cn.chamatou.commons.data.utils.StatementCreatorUtils;
/**
 * JDBC数据库操作
 *
 */
public class JdbcOperator {
	private Connection conn;
	private boolean autoCommit;
	private Savepoint savePoint;
	private boolean isBeginTranscation;
	private boolean readOnlyStatus;
	public JdbcOperator(Connection conn) throws SQLException{
		this.conn=conn;
		isBeginTranscation=false;
		readOnlyStatus=conn.isReadOnly();
	}
	public JdbcOperator(DataSource dataSource) throws SQLException{
		this(dataSource.getConnection());
	}
	
	/**
	 * 开启只读方式,如果只涉及到只读方式,则进行开启
	 * @throws SQLException 
	 */
	public void openReadOnly() throws SQLException{
		conn.setReadOnly(true);
	}	
	public void resetReadOnly() throws SQLException{
		conn.setReadOnly(readOnlyStatus);
	}
	
	/**
	 * 开启事务,执行增删改时需要开启事务,开启事务后会主动将readonly设置为false
	 * @throws SQLException
	 */
	public void beginTranscation() throws SQLException{
		conn.setReadOnly(false);
		autoCommit=conn.getAutoCommit();
		conn.setAutoCommit(false);
		savePoint=conn.setSavepoint();
		isBeginTranscation=true;
	}
	/**
	 * 提交事务并关闭链接
	 */
	public void commitAndClose() throws SQLException{
		commit();
		close();
	}
	/**
	 * 提交事务
	 * @throws SQLException
	 */
	public void commit() throws SQLException{
		if(isBeginTranscation){
			conn.commit();
			conn.setAutoCommit(autoCommit);
		}
	}
	/**
	 * 回滚事务到存储点
	 * @throws SQLException
	 */
	public void rollback() throws SQLException{
		if(isBeginTranscation){
			conn.rollback(savePoint);			
		}
	}
	public void close() throws SQLException{
		if(isBeginTranscation){
			conn.releaseSavepoint(savePoint);
		}
		conn.setReadOnly(readOnlyStatus);
		JDBCUtil.closeDataSource(conn);
	}
	
	
	/**
	 * 执行数据库查询
	 * @param sql
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> query(String sql,Object[] values) throws SQLException{
		return query(sql, values, 2);
	}
	/**
	 * 查询数据库
	 * @param sql 查询语句
	 * @param values 填充值
	 * @param scale 
	 * @return 返回List中Map的key为列名
	 * @throws SQLException
	 */
	public List<Map<String, Object>> query(String sql,Object[] values,int scale) throws SQLException{
		if(!readOnlyStatus){
			openReadOnly();
		}
		PreparedStatement prepare=generatedPreparedStatement(sql, values, scale,false);
		ResultSet rs=prepare.executeQuery();
		ResultSetMetaData metaData=rs.getMetaData();
		int columnCount=metaData.getColumnCount();
		ArrayList<Map<String, Object>> resultList=new ArrayList<Map<String, Object>>();
		while(rs.next()){
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= columnCount; i++) {					
				map.put(metaData.getCatalogName(i), rs.getObject(i));
			}
			resultList.add(map);
		}
		JDBCUtil.closeDataSource(rs);
		JDBCUtil.closeDataSource(prepare);
		resetReadOnly();
		return resultList;
	}
	/**
	 * 批量插入
	 * @param sql 插入语句
	 * @param valueList 插入列表值
	 * @throws SQLException
	 */
	public void batchInsert(String sql,List<Object[]> valueList) throws SQLException{
		batchInsert(sql, valueList,2);
	}
	
	public void batchInsert(String sql,List<Object[]> valueList,int scale) throws SQLException{
		if(!isBeginTranscation){
			beginTranscation();
		}
		PreparedStatement prepare=conn.prepareStatement(sql);
		for(int i=0;i!=valueList.size();++i){
			Object[] values=valueList.get(i);
			for(int n=0;n!=values.length;++n){
				if(scale>0){
					StatementCreatorUtils.setValue(prepare, n+1, values[n]);
				}else{
					StatementCreatorUtils.setValue(prepare, n+1,scale,values[n]);
				}
			}
			prepare.addBatch();
			if(i%100==0){
				executeBatchAndClear(prepare);
			}
		}
		executeBatchAndClear(prepare);
	}
	
	private void executeBatchAndClear(PreparedStatement prepare)
			throws SQLException {
		prepare.executeBatch();
		commit();
		prepare.clearBatch();
	}
	
	
	/**
	 * 插入单条记录
	 * @param sql
	 * @param values
	 * @return 插入后生成的主键
	 * @throws SQLException
	 */
	public Object insert(String sql,Object[] values) throws SQLException{
		return insert(sql,values,2);
	}
	public Object insert(String sql,Object[] values,int scale) throws SQLException{
		PreparedStatement prepare=generatedPreparedStatement(sql, values, scale,true);
		prepare.executeUpdate();
		ResultSet rs=prepare.getGeneratedKeys();
		Object key=null;
		if(rs.next()){
			key=rs.getObject(1);
		}
		JDBCUtil.closeDataSource(rs);
		JDBCUtil.closeDataSource(prepare);
		return key;
	}
	/**
	 * 执行 insert,delete,update
	 * @param sql
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(String sql,Object[] values) throws SQLException{
		return executeUpdate(sql, values,2);
	}
	/**
	 * 执行 insert,delete,update
	 * @param sql
	 * @param values
	 * @param scale 如果参数中涉及到BigDemice,需要指定小数点位数
	 * @return 影响结果条目
	 * @throws SQLException
	 */
	public int executeUpdate(String sql,Object[] values,int scale) throws SQLException{
		if(!isBeginTranscation){
			beginTranscation();
		}
		PreparedStatement prepare=generatedPreparedStatement(sql, values, scale,false);
		int result = prepare.executeUpdate();
		JDBCUtil.closeDataSource(prepare);
		return result;
	}
	private PreparedStatement generatedPreparedStatement(String sql,Object[] values,int scale,boolean isReturnKeys) throws SQLException{
		PreparedStatement prepare=null;
		if(isReturnKeys){
			prepare=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);			
		}else{
			prepare=conn.prepareStatement(sql);
		}
		if(values!=null&&values.length>0){
			for(int i=0;i!=values.length;++i){
				if(scale>0){
					StatementCreatorUtils.setValue(prepare, i+1, values[i]);
				}else{
					StatementCreatorUtils.setValue(prepare, i+1,scale,values[i]);
				}
			}			
		}
		return prepare;
	}
}
