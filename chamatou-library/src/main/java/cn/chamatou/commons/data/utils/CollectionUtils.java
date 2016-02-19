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
	/**
	 * 从Set中获取匹配的项目
	 * @param array
	 * @param match
	 * @return 无法匹配返回null
	 */
	public static final <T> T getSetContains(Set<T> list,T match){
		for(T a:list){
			if(a.equals(match)){
				return a;
			}
		}
		return null;
	}
	/**
	 * 从List中获取匹配的项目
	 * @param array
	 * @param match
	 * @return 无法匹配返回null
	 */
	public static final <T> T getListContains(List<T> list,T match){
		for(T a:list){
			if(a.equals(match)){
				return a;
			}
		}
		return null;
	}
	/**
	 * 从数组中获取匹配的项目
	 * @param array
	 * @param match
	 * @return 无法匹配返回null
	 */
	public static final <T> T getArrayContains(T[] array,T match){
		for(T a:array){
			if(a.equals(match)){
				return a;
			}
		}
		return null;
	}
	/**
	 * 从数组中获取匹配项
	 * @param array
	 * @param match
	 * @return
	 */
	public static final <T> boolean arrayContains(T[] array,T match){
		for(T a:array){
			if(a.equals(match)){
				return true;
			}
		}
		return false;
	}
}
