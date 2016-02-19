package cn.chamatou.commons.httpclient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter{
	private ResponseHandler handler;
	
	HttpChannelInboundHandlerAdapter(ResponseHandler handler){
		this.handler=handler;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof HttpResponse){
			HttpResponse response = (HttpResponse) msg;
			HttpResponseStatus status=response.getStatus();
			HttpHeaders headers=response.headers();
			//ResponseHeaders hrh = new ResponseHeaders(headers);
			//Charset charset=hrh.getCharset();
			//StringBuilder builder=new StringBuilder();
			//ByteArrayOutputStream out=new ByteArrayOutputStream();
			byte[] bytes=null;
			if (msg instanceof HttpContent) {
				HttpContent content = (HttpContent) msg;
				ByteBuf buf = content.content();
				bytes=new byte[buf.capacity()];
				for(int i=0;i<buf.capacity();i++){
					bytes[i]=buf.getByte(i);
				}
				//buf.toString(hrh.getCharset());
				//out.write(buf.array(),0,buf.capacity());
				//builder.append(buf.toString(charset));
				buf.release();
			}
			handler.handler(status.code(), new ResponseHeaders(headers),bytes);
			//out.close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}
}
