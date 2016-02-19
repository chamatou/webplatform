package cn.chamatou.account.entity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import cn.chamatou.commons.data.jpa.BaseEntity;
import cn.chamatou.commons.data.jpa.RandomIdentifier;
@Entity(name=AccountQualification.TABLE_NAME)
public class AccountQualification extends BaseEntity implements RandomIdentifier{
	public static final String TABLE_NAME="account_qualification";
	private static final long serialVersionUID = -1168341583106188664L;
	private String id;
	/**
	 * 账户
	 */
	private Account account;
	/**
	 * 账户角色
	 */
	private int accountRole;
	/**
	 * 资质名称
	 */
	private String qualifyName;
	/**
	 * 资质图片访问地址
	 */
	private String qualifyImage;
	
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
	public int getAccountRole() {
		return accountRole;
	}
	public void setAccountRole(int accountRole) {
		this.accountRole = accountRole;
	}
	public String getQualifyName() {
		return qualifyName;
	}
	public void setQualifyName(String qualifyName) {
		this.qualifyName = qualifyName;
	}
	public String getQualifyImage() {
		return qualifyImage;
	}
	public void setQualifyImage(String qualifyImage) {
		this.qualifyImage = qualifyImage;
	}
}
