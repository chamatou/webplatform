package cn.chamatou.commons.httpclient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;
import io.netty.handler.ssl.SslContext;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class HttpPost extends HttpGet{

	protected Map<String, String> params;
	
	protected HttpPostRequestEncoder bodyRequestEncoder;
	
	public HttpPost(String url){
		super(url);
	}
	public HttpPost(String url,int aggregatorLength){
		super(url);
		this.setAggregatorLength(aggregatorLength);
	}
	/**
	 * 获取post请求参数
	 * @return
	 */
	public Map<String, String> getParams() {
		return params;
	}
	/**
	 * 设置post提交参数
	 * @param params
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	
	
	@Override
	protected ChannelInitializer<SocketChannel> getNettyChannelInitializer(
			SslContext sslCtx, ResponseHandler handler) {
		return new HttpUploadChannelInitializer(sslCtx,new HttpChannelInboundHandlerAdapter(handler), getAggregatorLength());
	}
	@Override
	protected HttpRequest builderParams(HttpRequest request) {
		HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
		try {
			if(params!=null){
				bodyRequestEncoder = new HttpPostRequestEncoder(factory,request,false);
				Set<Entry<String, String>> entrys = params.entrySet();
				for (Entry<String, String> entry : entrys) {
					bodyRequestEncoder.addBodyAttribute(entry.getKey(),entry.getValue());
				}
				return bodyRequestEncoder.finalizeRequest();
			}
		} catch (ErrorDataEncoderException e) {
			e.printStackTrace();
		}
		return super.builderParams(request);
	}
	@Override
	protected void writeRequest(Channel channel, HttpRequest request) {
		super.writeRequest(channel, request);
		if (bodyRequestEncoder != null&& bodyRequestEncoder.isChunked()) {
			channel.write(bodyRequestEncoder);
		}
	}
	@Override
	protected HttpRequest getHttpRequest(String rawPath) {
		if(contentPost){
			//byte[] bytes=content.getBytes(charset);
			ByteBuf buf=Unpooled.copiedBuffer(content,charset);
			HttpRequest request= new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,rawPath,buf);
			request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, buf.readableBytes());
			return request;
		}else{
			return new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,rawPath);
		}
	}
}
