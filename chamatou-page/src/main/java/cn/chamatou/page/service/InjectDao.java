package cn.chamatou.page.service;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.chamatou.commons.data.jpa.JPADao;

public class InjectDao<T> extends JPADao<T>{
	private static final long serialVersionUID = -92989541434485566L;
	@PersistenceContext(unitName="chamatou_page")
	@Override
	public void setEntityManager(EntityManager em) {
		this.em=em;
	}
}
