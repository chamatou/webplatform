package cn.chamatou.commons.net;

import java.io.IOException;

public interface Acceptor {
	/**
	 * 开始接收服务器连接
	 * @throws IOException
	 */
	void accept()throws IOException;
}
