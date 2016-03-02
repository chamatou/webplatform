package cn.chamatou.commons.entity;

import cn.chamatou.commons.data.jpa.BaseEntity;
import cn.chamatou.commons.data.jpa.RandomIdentifier;

/**
 * 手机信息 
 *
 */
public class PhoneInfo extends BaseEntity implements RandomIdentifier{
	
	private static final long serialVersionUID = -3090765642452921083L;
	private String id;
	
	public static enum PhoneType{
		ANDROID,IPHONE,OTHER
	}
	private String imie;
	
	private PhoneType phoneType;
	
	private String location;
	
	
	
	@Override
	public String getId() {
		return null;
	}

	@Override
	public void setId(String id) {
		
	}

}
