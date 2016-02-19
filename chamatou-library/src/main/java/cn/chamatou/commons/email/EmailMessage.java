package cn.chamatou.commons.email;
import javax.activation.DataSource;
/**
 * 电子邮件消息
 *
 */
public class EmailMessage{

	private String[] receiver;//收件人
	private String[] carbonCopes;//抄送
	private String[] bcc;//密送
	private String subject;//消息主体
	private String content;//邮件内容
	private DataSource[] attach;//邮件附件
	
	public EmailMessage(String[] receiver,String subject,String content){
		this.receiver=receiver;
		this.subject=subject;
		this.content=content;
	}
	
	public void setCarbonCopes(String[] carbonCopes) {
		this.carbonCopes = carbonCopes;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	/**
	 * 获取收件人
	 * @return
	 */
	public String getReceiver() {
		return toRfc822String(receiver);
	}
	
	private String toRfc822String(String[] addresses) {
		if(addresses==null)return null;
		StringBuilder builder=new StringBuilder();
		for(String rece:addresses){
			builder.append(rece).append(",");
		}
		return builder.deleteCharAt(builder.length()-1).toString();
	}
	/**
	 * 获取抄送者
	 * @return
	 */
	public String getCarbonCopes() {
		return toRfc822String(carbonCopes);
	}

	public String getBcc() {
		return toRfc822String(bcc);
	}


	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public DataSource[] getAttach() {
		return attach;
	}
	public void setAttach(DataSource[] attach) {
		this.attach = attach;
	}
}
