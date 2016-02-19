package cn.chamatou.commons.net.netty;

import io.netty.channel.ChannelHandlerContext;
import cn.chamatou.commons.net.transf.ResponseWarp;
import cn.chamatou.commons.net.transf.TransforProtocol;

public class NettyDataWriter {
	private ChannelHandlerContext ctx;
	private boolean isWritered;
	
	public NettyDataWriter(ChannelHandlerContext ctx){
		this.ctx=ctx;
		isWritered=false;
	}
	
	public void write(ResponseWarp response){
		TransforProtocol.Response resp=response.toProtocol();
		if(!isWritered){
			ctx.writeAndFlush(resp);
			isWritered=true;
		}
	}
}
