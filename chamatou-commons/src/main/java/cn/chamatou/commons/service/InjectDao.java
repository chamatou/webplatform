package cn.chamatou.commons.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.chamatou.commons.data.jpa.JPADao;

public class InjectDao<T> extends JPADao<T>{
	private static final long serialVersionUID = -92989541434485566L;

	@PersistenceContext(unitName="chamatou_commons")
	@Override
	public void setEntityManager(EntityManager em) {
		this.em=em;
	}

}
