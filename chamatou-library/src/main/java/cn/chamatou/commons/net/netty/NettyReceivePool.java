package cn.chamatou.commons.net.netty;

import java.util.HashMap;

public class NettyReceivePool {
	private static HashMap<String, NettyReceiveHandler> handlerMap=new HashMap<String, NettyReceiveHandler>();
	private static HashMap<String,Integer> endOperNumberMap=new HashMap<String, Integer>();;
	public static final void addReceiveHandler(String operNumber,NettyReceiveHandler handler){
		handlerMap.put(operNumber, handler);	
	}
	
	public static final NettyReceiveHandler getReceiveHandler(String operNumber){
		return handlerMap.remove(operNumber);
	}
	
	public static final void addEndOperNumberMap(String operNumber,Integer what){
		endOperNumberMap.put(operNumber, what);	
	}
	
	public static final Integer getEndOperNumberMap(String operNumber){
		return endOperNumberMap.remove(operNumber);
	}
}
