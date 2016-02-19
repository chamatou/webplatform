package cn.chamatou.commons.data.jpa;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class RandomIdentifierEntity implements RandomIdentifier{
	private String id;
	private static final long serialVersionUID = -6657277681213145406L;
	@Id
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}
}
