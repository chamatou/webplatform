package cn.chamatou.commons.net.netty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NettyServerHandlerPool {
	private HashMap<String, List<NettyServerHandler>> pool;
	
	public NettyServerHandlerPool(){
		pool=new HashMap<String, List<NettyServerHandler>>();
	}
	
	public void addHandler(NettyServerHandler handler){
		 String code=handler.getRequestCode();
		 List<NettyServerHandler> handlers=pool.get(code);
		 if(handlers==null){
			 handlers=new ArrayList<NettyServerHandler>();
			 pool.put(code, handlers);
		 }
		 handlers.add(handler);
	}
	
	List<NettyServerHandler> getHandlerList(String code){
		return pool.get(code);
	}
}
