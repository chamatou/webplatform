package cn.chamatou.commons.net.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.io.IOException;

import cn.chamatou.commons.net.Connector;
import cn.chamatou.commons.net.Ipv4Description;
import cn.chamatou.commons.net.transf.RequestWarp;
import cn.chamatou.commons.net.transf.TransforProtocol;
import cn.chamatou.commons.net.transf.RequestWarp.RequestBuilder;
import cn.chamatou.commons.net.transf.TransforProtocol.Response;

import com.google.protobuf.ExtensionRegistry;

public class NettyClient implements Connector,Ipv4Description{
	private String host;
	private Integer port;
	private boolean keepAlive=false;
	private EventLoopGroup workerGroup;
	private Bootstrap b;
	private NettyCallFuture future;
	private boolean isConnect;
	private int waitTime;
	
	public boolean isKeepAlive() {
		return keepAlive;
	}
	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}
	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
		this.isConnect=false;
		this.keepAlive=true;
		waitTime=1000;
	}
	/**
	 * 设置连接超时等待时间
	 * @param time
	 */
	public void setWaitTime(int time){
		waitTime=time;
	}
	
	@Override
	public NettyCallFuture connect() throws IOException {
		if(isConnect){
			return future;
		}
		if(host==null||port==null){
			throw new IOException("Remote Host or Port is null");
		}
		future=new NettyCallFuture();
		workerGroup = new NioEventLoopGroup();
		b = new Bootstrap();
		b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, keepAlive);
        b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ExtensionRegistry registry = ExtensionRegistry.newInstance();
		        ChannelPipeline p = ch.pipeline();
		        p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
		        p.addLast("protobufDecode", 
		        new ProtobufDecoder(TransforProtocol.Response.getDefaultInstance(), registry));
		        p.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
		        p.addLast("protobufEncoder", new ProtobufEncoder());
		        p.addLast("handler",new SimpleChannelInboundHandler<TransforProtocol.Response>() {
					
					@Override
					public void exceptionCaught(ChannelHandlerContext ctx,
							Throwable cause) throws Exception {
						//服务端连接时断线
						isConnect=false;
					}

					@Override
					protected void channelRead0(ChannelHandlerContext ctx,
							Response response) throws Exception {
						cn.chamatou.commons.net.transf.ResponseWarp resp=cn.chamatou.commons.net.transf.ResponseWarp.paser(response);
						NettyReceivePool.getReceiveHandler(resp.getOperNumber()).doReceive(resp, future);
						NettyReceivePool.addEndOperNumberMap(resp.getOperNumber(), resp.getCode());
					}
				});
			}
		});
        final ChannelFuture cf=b.connect(host, port);
        if(!cf.awaitUninterruptibly(waitTime)){
        	isConnect=false;
    		workerGroup.shutdownGracefully();
        	throw new IOException("Connect fail.Server ip:"+this.host+" port:"+this.port);
        }
        try {
			b.connect(host, port).sync();
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
		future.setFuture(cf);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					cf.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					 workerGroup.shutdownGracefully();
				}
			}
		}).start();
		isConnect=true;
		return future;
	}
	@Override
	public String getIpaddr() {
		return this.host;
	}
	@Override
	public int getPort() {
		return this.port;
	}
	public boolean isConnect(){
		return isConnect;
	}
	public void close(){
		isConnect=false;
		workerGroup.shutdownGracefully();
	}
	/*public static void main(String[] args) throws IOException {
		Client client=new Client("127.0.0.1", 8008);
		CallFuture future=client.connect();
		cn.chamatou.commons.net.transf.RequestWarp.RequestBuilder rb=cn.chamatou.commons.transf.RequestWarp.createBuild("332211");
		rb.addParameter("p1", "1");
		rb.addParameter("p2", "2");
		rb.setContent("content");
		RequestWarp req=rb.build();
		future.send(req, new ReceiveHandler() {
			
			@Override
			public void doReceive(cn.chamatou.commons.transf.ResponseWarp response,
					CallFuture future) {
				System.out.println("client:"+response.getOperNumber()+","+response.getContent());
			}
		}).sync(req);
		client.close();
	}*/
}
