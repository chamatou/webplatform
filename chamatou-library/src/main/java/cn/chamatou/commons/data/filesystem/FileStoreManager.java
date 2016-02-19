package cn.chamatou.commons.data.filesystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件存储服务 
 *
 */
public interface FileStoreManager {
	/**
	 * 存储文件
	 * @param fileName 文件名称
	 * @param bytes 
	 * @return
	 */
	String store(String accountid,String fileName,byte[] bytes)throws IOException;
	String store(String accountid,File localFile)throws IOException;
	String store(String accountid,String fileName,InputStream in)throws IOException;
	/**
	 * 删除文件
	 * @param id 文件访问标示
	 * @return 删除成功返回true
	 */
	boolean delete(String id)throws IOException;
	/**
	 * 通过唯一标示获取文件
	 * @param id
	 * @return 返回空表示文件未找到
	 */
	InputStream load(String id)throws IOException;
	/**
	 * 通过唯一标示获取文件,适用于小文件直接加载(小于1m的文件)
	 * @param id
	 * @return 返回文件的Base64编码
	 */
	String get(String id)throws IOException;
	/**
	 * 是否支持html方式访问
	 * @return
	 */
	boolean isSupportHtmlAccess();
	/**
	 * 返回文件的html加载方式
	 * @param id
	 * @return
	 */
	String toHtmlLoad(String id);
}
