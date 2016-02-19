package cn.chamatou.commons.data.jpa;
import java.io.Serializable;
import java.util.List;
import cn.chamatou.commons.data.jpa.query.OrderBy;
import cn.chamatou.commons.data.jpa.query.QueryCondition;
public interface IDao<T> extends Serializable{
	/**  
	 * 持久化对象
	 * @param entity
	
	public void persist(T entity); */
	/**
	 * 通过实体ID获取对象
	 * @param entityid
	 * @return
	 */
	public T findById(Serializable entityid);
	/**
	 * 通过实体ID删除对象
	 * @param entityid
	 
	public void delete(Serializable entityid);*/
	/**
	 * 通过实体ID删除对象
	 * @param entityid
	
	public void delete(Serializable... entityids); */
	/**
	 * 获取实体总数量
	 */
	public Long count();
	/**
	 * 根据条件获取实体总数
	 * @param condition
	 * @return
	 */
	public Long count(QueryCondition condition);
	/**
	 * 清除缓存
	 */
	public void clearCache();
	/**
	 * 更新实体
	 * @param entity
	
	public void update(T entity); */
	/**
	 * 获取集合
	 * @return
	 */
	public List<T> getEntitys();
	/**
	 * 通过查询条件获取集合
	 * @param condition
	 * @return
	 */
	public List<T> getEntitys(QueryCondition condition);
	/**
	 * 通过排序条件获取集合
	 * @param orderby
	 * @return
	 */
	public List<T> getEntitys(OrderBy orderby);
	/**
	 * 通过查询条件，排序条件获取集合
	 * @param condition
	 * @param orderby
	 * @return
	 */
	public List<T> getEntitys(QueryCondition condition,OrderBy orderby);
	/**
	 * 分页查询
	 * @param first 开始位置
	 * @param maxResult 最大返回量
	 * @return
	 */
	public LimitPage<T> getLimitPage(Integer first,Integer maxResult);
	/**
	 * 分页查询
	 * @param first 开始位置
	 * @param maxResult 最大返回量
	 * @param condition 查询条件
	 * @return
	 */
	public LimitPage<T> getLimitPage(Integer first,Integer maxResult,QueryCondition condition);
	/**
	 * 分页查询
	 * @param first 开始位置
	 * @param maxResult 最大返回量
	 * @param orderby 进行排序
	 * @return
	 */
	public LimitPage<T> getLimitPage(Integer first,Integer maxResult,OrderBy orderby);
	/**
	 * 分页数据
	 * @param first 开始位置
	 * @param maxResult 最大返回量
	 * @param condition 查询条件
	 * @param orderby 排序方式
	 * @return
	 */
	public LimitPage<T> getLimitPage(Integer first,Integer maxResult,QueryCondition condition,OrderBy orderby);
}
