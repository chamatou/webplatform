package cn.chamatou.commons.net.netty;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.List;
import cn.chamatou.commons.net.transf.ResponseWarp;
import cn.chamatou.commons.net.transf.TransforProtocol;
@Sharable
public class NettyRequestChannelHandler extends SimpleChannelInboundHandler<TransforProtocol.Request>{
	private NettyServerHandlerPool pool;
	public NettyRequestChannelHandler(NettyServerHandlerPool pool){
		this.pool=pool;
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive");
		super.channelInactive(ctx);
		ctx.close();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelUnregistered");
		super.channelUnregistered(ctx);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println("exceptionCaught");
		ctx.close();
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			TransforProtocol.Request request) throws Exception {
		cn.chamatou.commons.net.transf.RequestWarp req=cn.chamatou.commons.net.transf.RequestWarp.paser(request);
		String code=req.getCode();
		ResponseWarp.ResponseBuilder build=ResponseWarp.createBuild();
		build.setOperNumber(req.getOperNumber());
		build.setContent("goods");
		List<NettyServerHandler> handlers=pool.getHandlerList(code);
		if(handlers!=null&&!handlers.isEmpty()){
			for(NettyServerHandler handler:handlers){
				handler.handler(req,build.build());
			}
		}
	}
	
}
