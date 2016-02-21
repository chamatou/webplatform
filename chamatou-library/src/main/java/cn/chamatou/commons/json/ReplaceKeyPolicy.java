package cn.chamatou.commons.json;
/**
 * KEY替换策略 
 *
 */
public interface ReplaceKeyPolicy {
	/**
	 * 替换Key名称
	 * @param key
	 * @return
	 */
	public String replace(String key);
}
