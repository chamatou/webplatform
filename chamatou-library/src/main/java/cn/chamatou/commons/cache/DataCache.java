package cn.chamatou.commons.cache;

import java.io.Serializable;

/**
 * 操作 lruCache
 */
public class DataCache {
	private static LruCache<String,Serializable> cache;
	private int maxSize;
	public DataCache(){
		maxSize=10;
	}
	public DataCache(int maxSize){
		cache=new LruCache<String,Serializable>(maxSize);
	}
	
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	/**
	 * 获取缓存中的数据
	 * @param key 访问数据的key
	 * @param updateCache 如果无法获取数据，提供数据的获取方法并返回key
	 * @return
	 */
	public Serializable getCacheDate(String key,UpdateCache updateCache){
		if(cache==null){
			cache=new LruCache<String,Serializable>(maxSize);
		}
		Serializable s=cache.get(key);
		if(s==null){
			s=updateCache.getData();
			cache.put(key, s);
		}
		return s;
	}
	
}
