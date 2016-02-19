package cn.chamatou.account.entity;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.chamatou.commons.data.jpa.BaseEntity;
import cn.chamatou.commons.data.jpa.RandomIdentifier;
/**
 * 用户访问记录 
 */
@Entity(name=AccountBehavior.TABLE_NAME)
public class AccountBehavior extends BaseEntity  implements RandomIdentifier{
	public static final String TABLE_NAME="account_behavior";
	private static final long serialVersionUID = 8288117989651996591L;
	private String id;
	//账号
	private Account account;
	//进入时间
	private Date entryTime;
	//离开时间
	private Date exitTime;
	//来自页面
	private String formPage;
	//到达页面
	private String toPage;
	//来自IP
	private String ipaddr;
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="account")
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getExitTime() {
		return exitTime;
	}
	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}
	public String getFormPage() {
		return formPage;
	}
	public void setFormPage(String formPage) {
		this.formPage = formPage;
	}
	public String getToPage() {
		return toPage;
	}
	public void setToPage(String toPage) {
		this.toPage = toPage;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
}
