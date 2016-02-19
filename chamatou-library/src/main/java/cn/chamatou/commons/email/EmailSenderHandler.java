package cn.chamatou.commons.email;
/**
 * 消息发送回调
 *
 */
public interface EmailSenderHandler {
	/**
	 * 
	 * @param message 发送的消息体
	 * @param time 发送耗时(毫秒)
	 */
	public void handler(EmailMessage message,long time);
	/**
	 * 发送消息失败后调用
	 * @param e
	 */
	public void sendError(Exception e);
}
