package cn.chamatou.commons.email;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * 邮件发送环境
 */
public class EmailSession {
	private Properties prop;
	private String emailHost;//邮件服务器主机名
	private String emailPort;//邮件服务器端口
	private String userName;//发件者用户名
	private String password;//发件者密码
	private String from;//发件者邮件
	private String fromName;//设置发件人名称
	public EmailSession() {
	
	}
	public EmailSession(String emailHost, String emailPort, String userName,
			String password, String from, String fromName) {
		this.emailHost = emailHost;
		this.emailPort = emailPort;
		this.userName = userName;
		this.password = password;
		this.from = from;
		this.fromName = fromName;
	}

	public Session getSession() {
		if(prop==null){
			prop=new Properties();
			prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.host",emailHost);
			prop.setProperty("mail.smtp.port", emailPort);
			
		}
		return Session.getInstance(prop,new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
	}
	
	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}
	public String getEmailPort() {
		return emailPort;
	}
	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

}
