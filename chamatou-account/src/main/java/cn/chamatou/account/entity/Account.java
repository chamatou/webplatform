package cn.chamatou.account.entity;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.chamatou.commons.data.jpa.BaseEntity;
import cn.chamatou.commons.data.utils.QRCodeUtils;

import com.google.zxing.WriterException;
/**
 * 用户账户实体
 *
 */
@Entity(name=Account.TABLE_NAME)
public class Account extends BaseEntity implements Serializable{
	public static final String TABLE_NAME="account_note";
	private static final long serialVersionUID = 3750708727866590376L;
	public enum Sex{
		MAN{
			@Override
			public String toString() {
				return "男";
			}
		},WOMAN{
			@Override
			public String toString() {
				return "女";
			}
		},UNKONW{
			@Override
			public String toString() {
				return "未知";
			}
		}
	}
	/**
	 * 普通角色
	 */
	public static final int ROLE_NORMAL=1;
	/**
	 * 商户角色
	 */
	public static final int ROLE_BIZ=1<<1;
	/**
	 * 门店角色
	 */
	public static final int ROLE_STORE=1<<2;
	/**
	 * 茶艺师角色
	 */
	public static final int ROLE_TEACHER=1<<3;
	/**账号*/
	private String account;
	/**密码*/
	private String password;
	/**注册时间*/
	private Date registTime;
	/**推荐人*/
	private Account recommender;
	/**注册时IP地址*/
	private String ipaddr;
	/**用户角色*/
	private int accountRole;
	/**头像图片访问地址*/
	private String headImage;
	/**二维码访问地址*/
	private String qcode;
	/**绑定手机*/
	private String phone;
	/**现金*/
	private BigDecimal cash;
	/**拥有卡券数*/
	private int couponNum;
	/**虚拟币*/
	private BigDecimal virtualMoney ;
	/**可提现虚拟币*/
	private BigDecimal extractMoney;
	/**经验值*/
	private int experience;
	/**称号*/
	private String alias;
	/**邮箱*/
	private String email;
	/**性别*/
	private Sex sex;
	
	private Set<CapitalFlow> capitalFlows;
	
	//private Set<AccountBehavior> behaviors; 
	
	private Set<PhotoLibray> photoLibrays;
	
	@OneToMany(mappedBy="account",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	public Set<PhotoLibray> getPhotoLibrays() {
		return photoLibrays;
	}
	public void setPhotoLibrays(Set<PhotoLibray> photoLibrays) {
		this.photoLibrays = photoLibrays;
	}
	/*@OneToMany(mappedBy="account",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	public Set<AccountBehavior> getBehaviors() {
		return behaviors;
	}
	public void setBehaviors(Set<AccountBehavior> behaviors) {
		this.behaviors = behaviors;
	}*/
	@OneToMany(mappedBy="belongAccount",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	public Set<CapitalFlow> getCapitalFlows() {
		return capitalFlows;
	}
	public void setCapitalFlows(Set<CapitalFlow> capitalFlows) {
		this.capitalFlows = capitalFlows;
	}
	
	public Account(){
		this.accountRole=ROLE_NORMAL;
		this.cash=new BigDecimal(0);
		this.couponNum=0;
		this.experience=0;
		this.virtualMoney=new BigDecimal(0);
		this.extractMoney=new BigDecimal(0);
		this.sex=Sex.UNKONW;
	}
	@Id
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="recommender")
	public Account getRecommender() {
		return recommender;
	}
	public void setRecommender(Account recommender) {
		this.recommender = recommender;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public int getAccountRole() {
		return accountRole;
	}
	public void setAccountRole(int role) {
		this.accountRole = role;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getQcode() {
		return qcode;
	}
	public void setQcode(String qcode) {
		this.qcode = qcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public BigDecimal getCash() {
		return cash;
	}
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}
	public int getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(int couponNum) {
		this.couponNum = couponNum;
	}
	public BigDecimal getVirtualMoney() {
		return virtualMoney;
	}
	public void setVirtualMoney(BigDecimal virtualMoney) {
		this.virtualMoney = virtualMoney;
	}
	public BigDecimal getExtractMoney() {
		return extractMoney;
	}
	public void setExtractMoney(BigDecimal extractMoney) {
		this.extractMoney = extractMoney;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	@Enumerated(EnumType.ORDINAL)
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 是否是商家
	 * @return
	 */
	@Transient
	public boolean isBiz(){
		return hasAuth(ROLE_BIZ);
	}
	/**
	 * 是否为门店
	 * @return
	 */
	@Transient
	public boolean isStore(){
		return hasAuth(ROLE_STORE);
	}
	/**
	 * 是否绑定手机
	 * @return
	 */
	@Transient
	public boolean isBindPhone(){
		return this.phone!=null;
	}
	/**
	 * 是否为导师
	 * @return
	 */
	@Transient
	public boolean isThecher(){
		return hasAuth(ROLE_TEACHER);
	}
	@Transient
	public boolean hasAuth(int role){
		return (this.accountRole&role)!=0;
	}
	/**
	 * 生成二维码
	 */
	@Transient
	public byte[] generatorQRCode(){
		try {
			return QRCodeUtils.encodeToBytes(this.account, 800, 800);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
