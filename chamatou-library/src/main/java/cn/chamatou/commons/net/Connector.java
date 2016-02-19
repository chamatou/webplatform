package cn.chamatou.commons.net;

import java.io.IOException;

import cn.chamatou.commons.net.netty.NettyCallFuture;

/**
 * 服务连接,负责连接到服务器 
 *
 */
public interface Connector {
	/**
	 * 连接到服务器
	 * @throws IOException
	 */
	NettyCallFuture connect()throws IOException;
}
