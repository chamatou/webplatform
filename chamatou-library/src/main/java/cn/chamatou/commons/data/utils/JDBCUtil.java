package cn.chamatou.commons.data.utils;
/**
 * JDBC工具类
 *
 */
public class JDBCUtil {
	/**
	 * 关闭数据库相关资源
	 * @param closeable
	 */
	public static final void closeDataSource(AutoCloseable closeable){
		if(closeable!=null){
			try {
				closeable.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}