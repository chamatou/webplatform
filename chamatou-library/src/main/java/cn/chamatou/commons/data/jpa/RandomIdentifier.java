package cn.chamatou.commons.data.jpa;

import java.io.Serializable;

public interface RandomIdentifier extends Serializable{
	public String getId();
	public void setId(String id);
}
