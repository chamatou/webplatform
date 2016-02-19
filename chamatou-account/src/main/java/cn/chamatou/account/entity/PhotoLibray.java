package cn.chamatou.account.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import cn.chamatou.commons.data.jpa.BaseEntity;
import cn.chamatou.commons.data.jpa.RandomIdentifier;
/**
 * 图片库 
 */
@Entity(name=PhotoLibray.TABLE_NAME)
public class PhotoLibray extends BaseEntity implements RandomIdentifier{
	public static final String TABLE_NAME="user_photo_libray";
	private static final long serialVersionUID = -1416252771889501651L;
	public static final String DEFAULT_DIR="PhotoLibray";
	public static final String DEFAULT_THUMBNAIL="thumbnail";
	/*
	 * 目录分为 下列四种,分别存放到account/PhotoLibray/下，缩略图在创建目录下构建thumbnail目录，
	 * 结尾以filename_s2.png,filename_s4.png来代表缩放倍数
	 * 	temp 临时目录,
	 *  gallery 相册
	 *  product 产品
	 *  store 店铺
	 * */
	private String id;
	//图片名称
	private String name;
	//存储路径
	private String path;
	//缩略图访问地址
	private String thumbnail;
	//图片库类型
	private int libaryType;
	//所属用户
	private Account account;
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getLibaryType() {
		return libaryType;
	}

	public void setLibaryType(int libaryType) {
		this.libaryType = libaryType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="account")
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
}
