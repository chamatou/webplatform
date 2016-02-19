package cn.chamatou.commons.data.jdbc;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
/**
 * dbcp数据源实现
 * 
 */
public class DBCPDSImpl extends DataSourceFactory {
	//private static final Logger logger = //logger.getLogger(DBCPDSImpl.class);
	private BasicDataSource dataSource;
	
	public DBCPDSImpl(String driveClassName, String jdbcUrl, String username,
			String password) {
		//logger.debug("Begin Create DatasourceFactory");
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driveClassName);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
	}

	@Override
	protected DataSource getInstance() {
		return dataSource;
	}

	@Override
	public void destory() {
		if (dataSource != null) {
			try {
				dataSource.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setPoolConfig(File file) throws FileNotFoundException {
		if (!file.exists()) {
			throw new FileNotFoundException("指定的配置" + file.getAbsolutePath()
					+ "文件未找到,配置DBCP属性失败,采用默认属性配置");
		}
		Properties prop = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			prop.load(in);
			String maxIdle=prop.getProperty("maxIdle");
			String maxActive=prop.getProperty("maxActive");
			String initialSize=prop.getProperty("initialSize");
			String logAbandoned=prop.getProperty("logAbandoned");
			String removeAbandoned=prop.getProperty("removeAbandoned");
			String removeAbandonedTimeout=prop.getProperty("removeAbandonedTimeout");
			String maxWait=prop.getProperty("maxWait");
			try{
				if(maxIdle!=null){
					dataSource.setMaxIdle(Integer.parseInt(maxIdle));
					//logger.debug("set maxIdle:"+maxIdle);
				}
				if(maxActive!=null){
					dataSource.setMaxActive(Integer.parseInt(maxActive));
					//logger.debug("set maxActive:"+maxActive);
				}
				if(initialSize!=null){
					dataSource.setInitialSize(Integer.parseInt(initialSize));
					//logger.debug("set initialSize:"+initialSize);
				}
				if(logAbandoned!=null){
					dataSource.setLogAbandoned(Boolean.valueOf(logAbandoned));
					//logger.debug("set logAbandoned:"+logAbandoned);
				}
				if(removeAbandoned!=null){
					dataSource.setRemoveAbandoned(Boolean.valueOf(removeAbandoned));
					//logger.debug("set removeAbandoned:"+removeAbandoned);
				}
				if(removeAbandonedTimeout!=null){
					dataSource.setRemoveAbandonedTimeout(Integer.parseInt(removeAbandonedTimeout));
					//logger.debug("set removeAbandonedTimeout:"+removeAbandonedTimeout);
				}
				if(maxWait!=null){
					dataSource.setMaxWait(Long.parseLong(maxWait));
					//logger.debug("set maxWait:"+maxWait);
				}
			}catch(NumberFormatException e){
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
