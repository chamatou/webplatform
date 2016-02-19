package cn.chamatou.commons.data.jdbc;
import java.io.File;
import java.io.FileNotFoundException;
import javax.sql.DataSource;

/**
 * 数据源工厂,调用getDataSource来获取数据源
 *
 */
public abstract class DataSourceFactory {
	/**
	 * 释放连接池
	 */
	public abstract void destory();
	/**
	 * 获取数据源
	 * @return
	 */
	public DataSource getDataSource(){
		return getInstance();
	}
	/**
	 * 设置数据源配置
	 * @param file
	 */
	public abstract void setPoolConfig(File file)throws FileNotFoundException ;
	/**
	 * 获取数据源
	 * @return
	 */
	protected abstract DataSource getInstance();
}
