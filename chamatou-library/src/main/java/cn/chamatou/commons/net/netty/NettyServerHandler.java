package cn.chamatou.commons.net.netty;

import cn.chamatou.commons.net.transf.RequestWarp;
import cn.chamatou.commons.net.transf.ResponseWarp;
public interface NettyServerHandler{
	/**
	 * 获取处理编码
	 */
	String getRequestCode();
	/**
	 * 执行消息处理
	 * @param ctx
	 * @param request
	 */
	void handler(RequestWarp request,ResponseWarp resp);
}
