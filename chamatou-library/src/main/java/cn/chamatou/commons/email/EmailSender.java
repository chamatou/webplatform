package cn.chamatou.commons.email;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
/**
 * Email邮件发送
 *
 */
public class EmailSender{
	
	private EmailMessage message;
	private EmailSession session;
	public EmailSender(EmailSession session){
		this.session=session;
	}
	
	
	public EmailMessage getMessage() {
		return message;
	}
	public void send(EmailSenderHandler handler) throws Exception {
		final EmailSenderHandler sh=handler;
		Session s=session.getSession();
		final MimeMessage mimeMessage=new MimeMessage(s);
		mimeMessage.setSubject(message.getSubject());
		if(session.getFromName()==null){
			mimeMessage.setFrom(new InternetAddress(session.getFrom()));			
		}else{
			mimeMessage.setFrom(new InternetAddress(MimeUtility.encodeText(session.getFromName())+"<"+session.getFrom()+">"));
		}
		mimeMessage.addRecipients(Message.RecipientType.TO,InternetAddress.parse(message.getReceiver()));
		if(message.getCarbonCopes()!=null){
			mimeMessage.addRecipients(Message.RecipientType.CC, InternetAddress.parse(message.getCarbonCopes()));
		}
		if(message.getBcc()!=null){
			mimeMessage.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(message.getBcc()));
		}
		if(message.getAttach()!=null){
			MimeMultipart part=new MimeMultipart();
			for(DataSource ds:message.getAttach()){
				MimeBodyPart attach=new MimeBodyPart();
				DataHandler dh=new DataHandler(ds);
				attach.setDataHandler(dh);
				part.addBodyPart(attach);
			}
			MimeBodyPart content=new MimeBodyPart();
			part.addBodyPart(content);
			mimeMessage.setContent(part,"text/html;charset=utf-8");
		}else{
			mimeMessage.setContent(message.getContent(), "text/html;charset=utf-8");
		}
		new Thread(){
			@Override
			public void run() {
				try {
					//Transport ts=session.getSession().getTransport();
					//ts.connect(host, port, user, password);
					//ts.connect(session.getEmailHost(),Integer.parseInt(session.getEmailPort()),session.getUserName(),session.getPassword());
					long start=System.currentTimeMillis();
					Transport.send(mimeMessage);
					sh.handler( message, System.currentTimeMillis()-start);
				} catch (MessagingException e) {
					sh.sendError(e);
				}
			}
		}.start();
		
	}
	public void setMessage(EmailMessage message) {
		this.message=(EmailMessage) message;
		
	}
}
