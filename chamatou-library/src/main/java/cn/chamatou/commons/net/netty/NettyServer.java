package cn.chamatou.commons.net.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.io.Closeable;
import java.io.IOException;

import cn.chamatou.commons.net.Acceptor;
import cn.chamatou.commons.net.Ipv4Description;
import cn.chamatou.commons.net.transf.TransforProtocol;

import com.google.protobuf.ExtensionRegistry;
public class NettyServer implements Acceptor,Ipv4Description,Closeable{
	private String addr;
	private int port;
	private int backlog;
	private boolean keepAlive;
	private boolean isStart;
	private NioEventLoopGroup bossGroup;
	private NioEventLoopGroup workerGroup;
	private ServerBootstrap boot;
	private NettyServerHandlerPool serverHandlerPool;
	public NettyServer(int port,NettyServerHandlerPool serverHandlerPool){
		this.port=port;
		backlog=128;
		keepAlive=true;
		isStart=false;
		this.serverHandlerPool=serverHandlerPool;
	}
	
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void accept() throws IOException {
		if(!isStart){
			try{
				isStart=true;
				bossGroup = new NioEventLoopGroup();
				workerGroup = new NioEventLoopGroup();
				boot = new ServerBootstrap();
				boot.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, backlog)
				.childOption(ChannelOption.SO_KEEPALIVE,keepAlive);	
				boot.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ExtensionRegistry registry=ExtensionRegistry.newInstance();
						ChannelPipeline  p=ch.pipeline();
						p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
						p.addLast("protobufDecode",
								new ProtobufDecoder(TransforProtocol.Request.getDefaultInstance(),registry));
						p.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
						p.addLast("protobufEncoder", new ProtobufEncoder());
						p.addLast("handler",new NettyRequestChannelHandler(serverHandlerPool));
					}
				});
				ChannelFuture cf=null;
				if(addr!=null){
					cf=boot.bind(addr,port).sync();
				}else{
					cf=boot.bind(port).sync();
				}
				System.out.println("server start...");
				cf.channel().closeFuture().sync();
			}catch (InterruptedException e) {
				throw new IOException(e.getMessage());
			}finally{
				workerGroup.shutdownGracefully();
				bossGroup.shutdownGracefully();
			}
		}
	}

	@Override
	public String getIpaddr() {
		return this.addr;
	}

	@Override
	public int getPort() {
		return port;
	}

	public int getBacklog() {
		return backlog;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}
	/*public static void main(String[] args) throws IOException {
		final Server s=new Server(8008);
		s.accept();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(s!=null){
					try {
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}));
	}*/

	@Override
	public void close() throws IOException {
		if(workerGroup!=null&&bossGroup!=null){
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
