package cn.chamatou.commons.httpclient;
import java.nio.charset.Charset;
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
import io.netty.handler.ssl.SslContext;
/**
 * 异步 Http post请求
 *
 */
public class HttpGet extends AbstractHttpRequest{
	/**
	 * 提交的内容
	 */
	protected String content;
	/**
	 * 内容编码
	 */
	protected Charset charset;
	//是否为内容提交
	protected boolean contentPost;
	/**/
	private String url;
	public HttpGet(String url){
		this.url=url;
		contentPost=false;
	}
	public HttpGet(String url,int aggregatorLength){
		this(url);
		this.setAggregatorLength(aggregatorLength);
	}
	@Override
	public String getContent() {
		return content;
	}
	/**
	 * 设置按内容方式提交请求
	 * @param content
	 */
	@Override
	public void setContent(String content,Charset charset) {
		this.content = content;
		this.charset=charset;
		contentPost=true;
	}
	@Override
	public String connectUrl() {
		return this.url;
	}

	@Override
	protected ChannelInitializer<SocketChannel> getNettyChannelInitializer(
			SslContext sslCtx, ResponseHandler handler) {
		return new HttpChannelInitializer(sslCtx, new HttpChannelInboundHandlerAdapter(handler),getAggregatorLength());
	}
	@Override
	protected HttpRequest builderParams(HttpRequest request) {
		//GET方式无需构建post请求数据
		return request;
	}

	@Override
	protected void writeRequest(Channel channel,HttpRequest request) {
		if(channel.isActive()){
			channel.write(request); 
		}
	}
	@Override
	protected HttpRequest getHttpRequest(String rawPath) {
		if(contentPost){
			//byte[] bytes=content.getBytes(charset);
			ByteBuf buf=Unpooled.copiedBuffer(content,charset);
			HttpRequest request= new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,rawPath,buf);
			request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, buf.readableBytes());
			return request;
		}else{
			return new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,rawPath);
		}
	}
}
