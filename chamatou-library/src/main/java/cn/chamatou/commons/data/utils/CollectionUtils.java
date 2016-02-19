package cn.chamatou.commons.data.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合工具
 *
 */
public class CollectionUtils {
	/**
	 * Set转Arraylist
	 * @param set
	 * @return
	 */
	public static final <T>  List<T> setToArrayList(Set<T> set){
		ArrayList<T> list=new ArrayList<T>();
		for(T t:set){
			list.add(t);
		}
		return list;
	}
	/**
	 * list转Set
	 * @param list
	 * @return
	 */
	public static final <T> Set<T> listToHashset(List<T> list){
		HashSet<T> set=new HashSet<T>();
		for(T t:list){
			list.add(t);
		}
		return set;
	}
}
