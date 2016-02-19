package cn.chamatou.commons.httpclient;
import java.io.IOException;

/**
 * 异步Http请求 
 *
 */
public interface NettyHttpRequest {
	/**
	 * 执行http请求
	 * @param handler 响应结果
	 * @throws IOException 网络IO异常
	 */
	void doRequest(ResponseHandler handler)throws IOException;
}
