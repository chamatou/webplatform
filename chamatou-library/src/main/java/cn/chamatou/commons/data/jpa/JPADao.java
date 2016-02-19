package cn.chamatou.commons.data.jpa;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import cn.chamatou.commons.data.IdentifierGenerat;
import cn.chamatou.commons.data.generator.RandomIdentifierGenerator;
import cn.chamatou.commons.data.jpa.query.OrderBy;
import cn.chamatou.commons.data.jpa.query.QueryCondition;

@Transactional(propagation=Propagation.REQUIRED)
public abstract  class JPADao<T> implements IDao<T>{
	private static final long serialVersionUID = 8235051907290098620L;

	protected Class<?> entityClass = JPAGenerator.getSuperClassGenricType(this.getClass());
	
	protected EntityManager em;
	/**
	 * 要求子类指定存储单元名称
	 * @PersistenceContext(unitName="what_name")
	 * @param em
	 */
	public abstract void setEntityManager(EntityManager em);
	
	private static final IdentifierGenerat idGenerat;
	
	static{
		idGenerat=new RandomIdentifierGenerator();
	}
	
	protected void persist(T entity) {
		if(entity instanceof BaseEntity){
			((BaseEntity)entity).setRecordTime(new Date());
		}
		if(entity instanceof RandomIdentifier){
			((RandomIdentifier)entity).setId(idGenerat.nextIdentifier());
		}
		em.persist(entity);
	}
	
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	@Override
	public T findById(Serializable entityid) {
		if(entityid==null) throw new RuntimeException(this.entityClass.getName()+ ":传入的实体id不能为空");
		return (T) em.find(this.entityClass, entityid);
	}

	protected void delete(Serializable entityid) {
		em.remove(em.getReference(this.entityClass, entityid));
	}

	protected void delete(Serializable... entityids) {
		for(Serializable id : entityids){
			delete(id);
		}
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Long count() {
		return (Long)em.createQuery("SELECT count("+ getCountField(this.entityClass) +") FROM "+ getEntityName(this.entityClass)+ " o ").getSingleResult();
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Long count(QueryCondition condition) {
		if(condition==null){
			throw new NullPointerException("QueryConditon is null.");
		}
		StringBuilder builder=new StringBuilder("SELECT count("+ condition.getAlias() +") FROM "+ getEntityName(this.entityClass));
		builder.append(" ").append(condition.getAlias()).append(" WHERE ");
		builder.append(condition.compile());
		Query query=em.createQuery(builder.toString());
		Object[] values=condition.getValues();
		for(int i=0;i<values.length;i++){
			query.setParameter(i+1, values[i]);
		}
		return (Long)query.getSingleResult();
	}
	
	@Override
	public void clearCache() {
		em.clear();
	}

	protected void update(T entity) {
		em.merge(entity);		
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<T> getEntitys() {
		return getEntitys(null, null);
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<T> getEntitys(QueryCondition condition) {
		return getEntitys(condition, null);
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<T> getEntitys(OrderBy orderby) {
		return getEntitys(null, orderby);
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntitys(QueryCondition condition, OrderBy orderby) {
		StringBuilder builder=new StringBuilder("SELECT ");
		String alias="o";
		if(condition!=null){
			alias=condition.getAlias();
		}
		builder.append(alias).append(" FROM ").append(getEntityName(this.entityClass)).append(" ")
		.append(alias);
		if(condition!=null){
			builder.append(" WHERE ").append(condition.compile());
		}
		if(orderby!=null){
			builder.append(orderby.toOrderByString(alias));
		}
		Query query=em.createQuery(builder.toString());
		if(condition!=null){
			Object[] values=condition.getValues();
			for(int i=0;i<values.length;i++){
				query.setParameter(i+1, values[i]);
			}
		}
		return query.getResultList();
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@Override
	public LimitPage<T> getLimitPage(Integer first, Integer maxResult) {
		return getLimitPage(first, maxResult, null, null);
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@Override
	public LimitPage<T> getLimitPage(Integer first, Integer maxResult,
			QueryCondition condition) {
		return getLimitPage(first, maxResult, condition, null);
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@Override
	public LimitPage<T> getLimitPage(Integer first, Integer maxResult,
			OrderBy orderby) {
		return getLimitPage(first, maxResult, null, orderby);
	}
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	@Override
	public LimitPage<T> getLimitPage(Integer first, Integer maxResult,
			QueryCondition condition, OrderBy orderby) {
		String alias="o";
		if(condition!=null){
			alias=condition.getAlias();
		}
		StringBuilder builder=new StringBuilder("SELECT "+alias+" FROM "+getEntityName(this.entityClass)+" AS "+alias);
		builder.append(condition!=null?" WHERE "+condition.compile():"");
		builder.append(orderby!=null?orderby.toOrderByString(alias):"");
		Query query=em.createQuery(builder.toString());
		if(condition!=null){
			Object[] values=condition.getValues();
			for(int i=0;i<values.length;i++){
				query.setParameter(i+1, values[i]);
			}
		}
		if(first!=null&&maxResult!=null){
			query.setFirstResult(first).setMaxResults(maxResult);
		}
		List<T> list=query.getResultList();
		Long count=null;
		if(condition!=null){
			count=this.count(condition);
		}else{
			count=this.count();
		}
		LimitPage<T> page=null;
		if(first!=null){
			page=new LimitPage<T>(first,count.intValue(), maxResult==null?list.size():maxResult);
		}else{
			page=new LimitPage<T>(count.intValue(),  maxResult==null?list.size():maxResult); 
		}
		page.setResultSet(list);
		return page;
	}
	/**
	 * 获取统计属性,该方法是为了解决hibernate解析联合主键select count(o) from Xxx o语句BUG而增加,
	 * hibernate对此jpql解析后的sql为select count(field1,field2,...),
	 * 显示使用count()统计多个字段是错误的
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	protected static <E> String getCountField(Class<E> clazz){
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for(PropertyDescriptor propertydesc : propertyDescriptors){
				Method method = propertydesc.getReadMethod();
				if(method!=null && method.isAnnotationPresent(EmbeddedId.class)){					
					PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					out = "o."+ propertydesc.getName()+ "." + (!ps[1].getName().equals("class")? ps[1].getName(): ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return out;
	}
	/**
	 * 获取实体的名称
	 * @param <E>
	 * @param clazz 实体类
	 * @return
	 */
	public static <E> String getEntityName(Class<E> clazz){
		String entityname = clazz.getSimpleName();
		Entity entity = clazz.getAnnotation(Entity.class);
		if(entity.name()!=null && !"".equals(entity.name())){
			entityname = entity.name();
		}
		return entityname;
	}
}
