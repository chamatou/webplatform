package cn.chamatou.offline.store;

import java.util.Date;

import cn.chamatou.commons.data.jpa.BaseEntity;
import cn.chamatou.commons.data.jpa.RandomIdentifier;

public class Store extends BaseEntity implements RandomIdentifier{
	private static final long serialVersionUID = -7544358383025269514L;
	private String id;
	/**
	 * 门店名称
	 */
	private String storeName;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 区域编码
	 */
	private String areaCode;
	/**
	 * 联系电话
	 */
	private String contact;
	/**
	 * 开始经营时间
	 */
	private Date startOperate;
	/**
	 * 结束经营时间
	 */
	private Date endOperate;
	/**
	 * 门店详细信息
	 */
	private String detail;
	/**
	 * 地理经度
	 */
	private Double latitude;
	/**
	 * 地理纬度
	 */
	private Double longitude;
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}
	
}
