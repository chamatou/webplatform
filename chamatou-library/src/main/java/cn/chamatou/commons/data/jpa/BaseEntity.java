package cn.chamatou.commons.data.jpa;

import java.util.Date;
/**
 *  基础类,记录数据写入时间
 */
public abstract class BaseEntity{
	protected Date recordTime;

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
}
