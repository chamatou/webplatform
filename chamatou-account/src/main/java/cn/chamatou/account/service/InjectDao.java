package cn.chamatou.account.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.chamatou.commons.data.jpa.JPADao;

public class InjectDao<T> extends JPADao<T>{
	
	@PersistenceContext(unitName="chamatou_account")
	@Override
	public void setEntityManager(EntityManager em) {
		this.em=em;
	}

}
