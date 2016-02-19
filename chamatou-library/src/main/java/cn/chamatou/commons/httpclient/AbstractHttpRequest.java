package cn.chamatou.commons.httpclient;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.TrustManagerFactory;

/**
 * 抽象的http请求
 */
public abstract class AbstractHttpRequest implements NettyHttpRequest{
	/**
	 * 是否启用gzip方式进行压缩
	 */
	private boolean enableGzip;
	/**
	 * 请求的用户信息
	 */
	private String userAgent;
	/**
	 * 客户端可接收的语言
	 */
	private String acceptLanguage;
	/**
	 * 客户端可接收的内容类型
	 */
	private String accept;
	/**
	 * 浏览器可以接受的字符编码集
	 */
	private String acceptCharset;
	/**
	 * http默认端口
	 */
	private int httpPort;
	/**
	 * https默认端口
	 */
	private int httpsPort;
	/**
	 * 读取尺寸
	 */
	private int aggregatorLength;
	/**
	 * 证书信任工厂
	 **/
	private TrustManagerFactory trustManagerFactory;
	
	public abstract String getContent();
	/**
	 * 设置按内容方式提交请求
	 * @param content
	 */
	public abstract void setContent(String content,Charset charset);
	
	public int getAggregatorLength() {
		return aggregatorLength;
	}

	public void setAggregatorLength(int aggregatorLength) {
		this.aggregatorLength = aggregatorLength;
	}

	protected AbstractHttpRequest(){
		this.tryConnectNumber=5;
		this.connectTimeout=5000;
		httpPort=80;
		httpsPort=443;
		enableGzip=true;
		aggregatorLength=8388608;
		acceptCharset="utf-8";
		acceptLanguage="zh-CN,zh;q=0.8";
		userAgent="Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36";
		accept="text/html,application/xhtml+xml,application/json,text/css,text/javascript,application/xml;q=0.9,image/webp,*/*;q=0.8";
		trustManagerFactory=InsecureTrustManagerFactory.INSTANCE;
	}
	/**
	 * 链接请求超时间隔
	 */
	private int connectTimeout;
	/**
	 * 重连尝试此时
	 */
	private int tryConnectNumber;
	/**
	 * 获取连接超时间隔
	 * 
	 * @return
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * 设置连接超时间隔
	 * 
	 * @param connectTimeout
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 获取连接超时重试次数,默认5次
	 * 
	 * @return
	 */
	public int getTryConnectNumber() {
		return tryConnectNumber;
	}

	/**
	 * 设置链接超时重试次数
	 * 
	 * @param tryConnectNumber
	 */
	public void setTryConnectNumber(int tryConnectNumber) {
		this.tryConnectNumber = tryConnectNumber;
	}
	
	/*public static void main(String[] args) throws URISyntaxException {
		URI uri=new URI("http://www.zg163.net");
		System.out.println(uri.getHost());
		System.out.println(uri.getPort());
	}
	
	*/
	
	@Override
	public void doRequest(ResponseHandler handler) throws IOException {
		EventLoopGroup group = null;
		try {
			URI uri=new URI(connectUrl());
			String host=uri.getHost();
			int port=uri.getPort();
			String schema=uri.getScheme()==null?"http":uri.getScheme();
			if (port == -1) {
	            if ("http".equalsIgnoreCase(schema)) {
	                port = httpPort;
	            } else if ("https".equalsIgnoreCase(schema)) {
	                port = httpsPort;
	            }
	        }
			if (!"http".equalsIgnoreCase(schema) && !"https".equalsIgnoreCase(schema)) {
	            throw new IOException("只支持  HTTP 或 HTTPS.");
	        }
			final boolean ssl = "https".equalsIgnoreCase(schema);
	        final SslContext sslCtx;
	        if (ssl) {
	            sslCtx = SslContextBuilder.forClient().sslProvider(SslProvider.JDK)
	                .trustManager(getTrustManagerFactory()).build();
	        } else {
	            sslCtx = null;
	        }
	        group=new NioEventLoopGroup();
	        Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.handler(getNettyChannelInitializer(sslCtx, handler));
			final ChannelFuture cf = b.connect(host, port);
			boolean isConnect = false;// 是否链接到对方服务器
			for (int i = 0; i < tryConnectNumber; i++) {
				if (cf.awaitUninterruptibly(connectTimeout)) {
					isConnect = true;
					break;
				}
			}
			if (!isConnect) {
				throw new IOException("无法连接对方服务器,host:" + host);
			}
			String query=uri.getQuery();
			String rawPath=uri.getRawPath();
			if(query!=null){
				rawPath=rawPath+"?"+query;
			}
			
			HttpRequest request =getHttpRequest(rawPath);
			HttpHeaders headers=request.headers();
			headers.set(HttpHeaders.Names.HOST, host);
			headers.set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.CLOSE);
			if(enableGzip){
				headers.set(HttpHeaders.Names.ACCEPT_ENCODING,HttpHeaders.Values.GZIP);				
			}
			headers.set(HttpHeaders.Names.CACHE_CONTROL,HttpHeaders.Values.NO_CACHE);
			headers.set(HttpHeaders.Names.ACCEPT_CHARSET,acceptCharset);
			headers.set(HttpHeaders.Names.USER_AGENT,userAgent);
			headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE,acceptLanguage);
			headers.set(HttpHeaders.Names.ACCEPT,accept);
			if(!extHeader.isEmpty()){
				Set<String> keys=extHeader.keySet();
				for(String key:keys){
					headers.set(key,extHeader.get(key));
				}
			}
			request=builderParams(request);
			
			Channel channel=cf.sync().channel();
			writeRequest(channel,request);
			channel.flush();
			channel.closeFuture().sync();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			if(group!=null){
				group.shutdownGracefully();
			}
		}
	}
	private Map<String, String> extHeader=new HashMap<String, String>();
	/**
	 * 设置额外的请求头
	 */
	public void setRequestHeader(String name,String value){
		extHeader.put(name, value);
	}
	public void setRequestHeaders(Map<String, String> hearders){
		extHeader=hearders;
	}
	/**
	 * 获取http请求路径,例如http://localhost:8080/source/test.cc?ab=cd&yinxi=yinxi
	 * @return
	 */
	public abstract String connectUrl();
	/**
	 * 获取消息处理器
	 * @param sslCtx netty的SSL上下文
	 * @param handler 消息处理完毕回调
	 * @return
	 */
	protected abstract ChannelInitializer<SocketChannel> getNettyChannelInitializer(SslContext sslCtx,ResponseHandler handler);
	/**
	 * 获取请求方式
	 * @return
	 */
	protected abstract HttpRequest getHttpRequest(String rawPath);
	/**
	 * 构建请求参数
	 * @return
	 */
	protected abstract HttpRequest builderParams(HttpRequest request);
	/**
	 * 写请求参数
	 * @param channel
	 */
	protected abstract void writeRequest(Channel channel,HttpRequest reqeust);
	
	
	public boolean isEnableGzip() {
		return enableGzip;
	}
	/**
	 * 设置是否启用gzip 默认true
	 * @param enableGzip
	 */
	public void setEnableGzip(boolean enableGzip) {
		this.enableGzip = enableGzip;
	}
	public String getUserAgent() {
		return userAgent;
	}
	/**
	 * 设置用户信息
	 * @param userAgent
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getAcceptLanguage() {
		return acceptLanguage;
	}
	/**
	 * 设置可处理的语言
	 * @param acceptLanguage
	 */
	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}
	public String getAccept() {
		return accept;
	}
	/**
	 * 设置客户端可处理的文档类型
	 * @param accept
	 */
	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptCharset() {
		return acceptCharset;
	}
	/**
	 * 设置浏览器的编码集,默认utf-8
	 * @param acceptCharset
	 */
	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}

	public TrustManagerFactory getTrustManagerFactory() {
		return trustManagerFactory;
	}
	/**
	 * 设置证书信任工厂
	 * @param trustManagerFactory
	 */
	public void setTrustManagerFactory(TrustManagerFactory trustManagerFactory) {
		this.trustManagerFactory = trustManagerFactory;
	}
	
}
