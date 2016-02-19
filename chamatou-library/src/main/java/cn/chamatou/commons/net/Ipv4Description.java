package cn.chamatou.commons.net;
/**
 * 服务器信息描述 
 *
 */
public interface Ipv4Description {
	/**
	 * 获取服务器IP
	 * @return
	 */
	String getIpaddr();
	/**
	 * 获取服务器端口
	 * @return
	 */
	int getPort();
}
