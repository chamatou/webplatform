package cn.chamatou.commons.data.jpa.query;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 排序方式
 */
public final class OrderBy {
	/**
	 * OrderBy 排序方式
	 *
	 */
	public enum TYPE{
		/**
		 * 升序
		 */
		ASC{
			@Override
			public String toString() {
				return " asc ";
			}
			
		},
		/**
		 * 降序
		 */
		DESC{
			@Override
			public String toString() {
				return " desc ";
			}
		}
	}
	private LinkedHashMap<String, TYPE> orderbyMap=new LinkedHashMap<String, TYPE>();
	/**
	 * 创建排序,默认降序
	 * @param filedName
	 */
	public OrderBy(String filedName){
		add(filedName, OrderBy.TYPE.DESC);
	}
	/**
	 * 创建排序,指定排序方式
	 * @param filedName 排序的字段
	 * @param orderByType 排序的方式
	 */
	public OrderBy(String filedName,TYPE orderByType){
		add(filedName, orderByType);
	}
	/**
	 * 添加更多的orderby条件
	 * @param filedName
	 * @param method
	 * @return
	 */
	public OrderBy add(String filedName){
		orderbyMap.put(filedName,OrderBy.TYPE.DESC);
		return this;
	}
	/**
	 * 添加更多的orderby条件   
	 * @param filedName
	 * @param method
	 * @return
	 */
	public OrderBy add(String filedName,TYPE orderByType){
		orderbyMap.put(filedName, orderByType);
		return this;
	}
	/**
	 * 返回orderby条件
	 * @param alias
	 * @return
	 */
	public String toOrderByString(String alias){
		Set<String> keySet=orderbyMap.keySet();
		StringBuilder sb=new StringBuilder(" order by ");
		for(String key:keySet){
			sb.append(alias).append(".").append(key).append(" ").append(orderbyMap.get(key)).append(" , ");
		}
		int index=sb.toString().lastIndexOf(",");
		return sb.toString().substring(0, index);
	}
}
