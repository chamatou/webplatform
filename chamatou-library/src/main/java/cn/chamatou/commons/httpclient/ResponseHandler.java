package cn.chamatou.commons.httpclient;
/**
 * Http响应处理结果
 *
 */
public interface ResponseHandler {
	/**
	 *  Http响应处理结果回调
	 * @param statusCode 响应码
	 * @param headers 响应头
	 * @param content 响应内容
	 */
	void handler(int statusCode,ResponseHeaders headers,byte[] content)throws Exception;
	
}
