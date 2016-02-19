package cn.chamatou.commons.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.chamatou.commons.data.jpa.BaseEntity;
import cn.chamatou.commons.data.jpa.RandomIdentifier;
/**
 * 页访问记录 
 *
 */
@Entity(name=PageAccessLog.TABLE_NAME)
public class PageAccessLog extends BaseEntity implements RandomIdentifier{
	public static final String TABLE_NAME="page_access_log";
	private static final long serialVersionUID = 981912144604225089L;
	private String id;
	/**
	 * 访问时间
	 */
	private Date accessTime;
	/**
	 * 来自页面名称
	 */
	private String formPage;
	/**
	 * 来自页面标志(应该由客户端生成后提交)
	 */
	private String formFlag;
	/**
	 * 到何页面
	 */
	private String toPage;
	/**
	 * 到何页面标志(应该由客户端生成后提交)
	 */
	private String toFlag;
	/**
	 * 来自的ipv4地址
	 */
	private String ipv4Addr;
	/**
	 * 账户ID
	 */
	private String accountId;
	@Id
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id=id;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	@Column(length=50)
	public String getFormPage() {
		return formPage;
	}

	public void setFormPage(String formPage) {
		this.formPage = formPage;
	}
	@Column(length=40)
	public String getFormFlag() {
		return formFlag;
	}

	public void setFormFlag(String formFlag) {
		this.formFlag = formFlag;
	}
	@Column(length=50)
	public String getToPage() {
		return toPage;
	}

	public void setToPage(String toPage) {
		this.toPage = toPage;
	}
	@Column(length=40)
	public String getToFlag() {
		return toFlag;
	}

	public void setToFlag(String toFlag) {
		this.toFlag = toFlag;
	}
	@Column(length=15)
	public String getIpv4Addr() {
		return ipv4Addr;
	}

	public void setIpv4Addr(String ipv4Addr) {
		this.ipv4Addr = ipv4Addr;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
}
