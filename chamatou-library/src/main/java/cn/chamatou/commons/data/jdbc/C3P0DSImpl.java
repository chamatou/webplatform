package cn.chamatou.commons.data.jdbc;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;


import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * C3P0数据源实现
 *
 */
public class C3P0DSImpl extends DataSourceFactory{
	//private static final Logger logger=//logger.getLogger(C3P0DSImpl.class);
	private ComboPooledDataSource dataSource;
	/**
	 * 初始化C3P0数据源工厂
	 * @param driverClass 数据库驱动类
	 * @param jdbcUrl jdbc链接URL
	 * @param username 用户名
	 * @param password 密码
	 * @throws SQLException 
	 */
	public C3P0DSImpl(String driverClass,String jdbcUrl,String username,String password) throws SQLException{
		//logger.debug("Begin Create DatasourceFactory");
		dataSource=new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(driverClass);
			//logger.debug("Set DriverClass:"+driverClass);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
		dataSource.setJdbcUrl(jdbcUrl);
		//logger.debug("Set JdbcUrl:"+jdbcUrl);
		dataSource.setUser(username);
		dataSource.setPassword(password);
	}
	
	protected DataSource getInstance() {
		return dataSource;
	}

	@Override
	public void destory() {
		if(dataSource!=null){
			//debug级别close语句会抛出调试异常,与数据库机制有关系
			dataSource.close();
		}
	}

	@Override
	public void setPoolConfig(File file) throws FileNotFoundException {
		if(!file.exists()){
			throw new FileNotFoundException("指定的配置"+file.getAbsolutePath()+"文件未找到,配置C3P0属性失败,采用默认属性配置");
		}
		Properties prop=new Properties();
		FileInputStream in=null;
		try{
			in = new FileInputStream(file);
			prop.load(in);
			String initialPoolSize=prop.getProperty("initialPoolSize");
			String minPoolSize=prop.getProperty("minPoolSize");
			String maxPoolSize=prop.getProperty("maxPoolSize");
			String maxIdleTime=prop.getProperty("maxIdleTime");
			String acquireIncrement=prop.getProperty("acquireIncrement");
			String idleConnectionTestPeriod=prop.getProperty("idleConnectionTestPeriod");
			String acquireRetryAttempts=prop.getProperty("acquireRetryAttempts");
			String autoCommitOnClose=prop.getProperty("autoCommitOnClose");
			String acquireRetryDelay=prop.getProperty("acquireRetryDelay");
			String checkoutTimeout=prop.getProperty("checkoutTimeout");
			String numHelperThreads=prop.getProperty("numHelperThreads");
			try{
				if(initialPoolSize!=null){
					dataSource.setInitialPoolSize(Integer.parseInt(initialPoolSize));
					//logger.debug("Set initialPoolSize:"+initialPoolSize);
				}
				if(minPoolSize!=null){
					dataSource.setMinPoolSize(Integer.parseInt(minPoolSize));
					//logger.debug("Set minPoolSize:"+minPoolSize);
				}
				if(maxPoolSize!=null){
					dataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
					//logger.debug("Set setInitialPoolSize:"+initialPoolSize);
				}
				if(maxIdleTime!=null){
					dataSource.setMaxIdleTime(Integer.parseInt(maxIdleTime));
					//logger.debug("Set maxIdleTime:"+maxIdleTime);
				}
				if(acquireIncrement!=null){
					dataSource.setAcquireIncrement(Integer.parseInt(acquireIncrement));
					//logger.debug("Set acquireIncrement:"+acquireIncrement);
				}
				if(idleConnectionTestPeriod!=null){
					dataSource.setIdleConnectionTestPeriod(Integer.parseInt(idleConnectionTestPeriod));
					//logger.debug("Set idleConnectionTestPeriod:"+idleConnectionTestPeriod);
				}
				if(acquireRetryAttempts!=null){
					dataSource.setAcquireRetryAttempts(Integer.parseInt(acquireRetryAttempts));
					//logger.debug("Set acquireRetryAttempts:"+acquireRetryAttempts);
				}
				if(autoCommitOnClose!=null){
					dataSource.setAutoCommitOnClose(Boolean.valueOf(autoCommitOnClose));
					//logger.debug("Set autoCommitOnClose:"+autoCommitOnClose);
				}
				if(acquireRetryDelay!=null){
					dataSource.setAcquireRetryDelay(Integer.parseInt(acquireRetryDelay));
					//logger.debug("Set acquireRetryDelay:"+acquireRetryDelay);
				}
				if(checkoutTimeout!=null){
					dataSource.setCheckoutTimeout(Integer.parseInt(checkoutTimeout));
					//logger.debug("Set checkoutTimeout:"+checkoutTimeout);
				}
				if(numHelperThreads!=null){
					dataSource.setNumHelperThreads(Integer.parseInt(numHelperThreads));
					//logger.debug("Set numHelperThreads:"+numHelperThreads);
				}
				
			}catch(NumberFormatException e){
				//logger.error("NumberFormatException", e);
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
