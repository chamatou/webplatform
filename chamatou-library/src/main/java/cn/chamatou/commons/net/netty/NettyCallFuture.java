package cn.chamatou.commons.net.netty;

import cn.chamatou.commons.net.transf.RequestWarp;
import io.netty.channel.ChannelFuture;

public class NettyCallFuture {
	private ChannelFuture future;
	
	
	
	public void setFuture(ChannelFuture future) {
		this.future = future;
	}
	public boolean isSuccess() {
		return future.isSuccess();
	}
	public NettyCallFuture sync(RequestWarp request) {
		//while(ReceivePool.getReceiveHandler(operNumber))
		try {
			future.sync();
			while(NettyReceivePool.getEndOperNumberMap(request.getOperNumber())==null){
				//System.out.println("null");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}
	public NettyCallFuture send(RequestWarp request,NettyReceiveHandler handler) {
		NettyReceivePool.addReceiveHandler(request.getOperNumber(), handler);
		future.channel().writeAndFlush(request.toProtocol());
		return this;
	}
}
