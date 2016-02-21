package cn.chamatou.commons.json;
/**
 * JSON值转换策略
 */
public interface ValuePolicy {
	/**
	 * 获取对象值
	 * @param obj
	 * @return
	 */
	String getValue(Object obj);
}
