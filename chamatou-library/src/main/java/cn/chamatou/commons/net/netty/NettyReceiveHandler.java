package cn.chamatou.commons.net.netty;

import cn.chamatou.commons.net.transf.ResponseWarp;


public interface NettyReceiveHandler {
	void doReceive(ResponseWarp response,NettyCallFuture future);
}
