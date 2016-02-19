package cn.chamatou.commons.cache;

import java.io.Serializable;

/**
 * 加入缓存 
 *
 */
public interface UpdateCache {
	/**
	 * 获取需要缓存的数据
	 */
	Serializable getData();
}
