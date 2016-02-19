package cn.chamatou.commons.httpclient;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;

/**
 * 普通http请求通道
 *
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel>{
	private SslContext sslCtx;
	
	private ChannelInboundHandlerAdapter handler;
	
	private int aggregatorSize;
	HttpChannelInitializer(SslContext sslCtx,ChannelInboundHandlerAdapter handler,int aggregatorSize){
		this.sslCtx=sslCtx;
		this.handler=handler;
		this.aggregatorSize=aggregatorSize;
	}
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		if (sslCtx != null) {
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		p.addLast(new HttpClientCodec());
		p.addLast(new HttpContentDecompressor());
		p.addLast(new HttpObjectAggregator(aggregatorSize));
		p.addLast(handler);
	}
}
