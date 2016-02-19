package cn.chamatou.commons.web.json;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import cn.chamatou.commons.data.utils.StringUtils;

public class JsonUtils {
	/**
	 * 构建简单json
	 * @param key
	 * @param value
	 * @return
	 */
	public static final String simpleJson(String key,String value){
		JSONObject jo=new JSONObject();
		jo.put(key, value);
		return StringUtils.singleQuotationMark(jo.toString());
	}
	/**
	 * 通过Map构建json,默认调用toString
	 * @param maps
	 * @return
	 */
	public static final <K> String simpleJson(Map<K,?> maps){
		SimpleJsonHandler h=new SimpleJsonHandler() {
			
			@Override
			public String handler(Object obj) {
				return obj.toString();
			}
		};
		return simpleJson(maps, h);
	}
	/**
	 * 通过Map构建json,默认调用Key的toString,通过handler调用value获取字符
	 * @param maps
	 * @param handler
	 * @return
	 */
	public static final <K> String simpleJson(Map<K,?> maps,SimpleJsonHandler handler){
		JSONObject jo=new JSONObject();
		Set<K> keys=maps.keySet();
		for(K k:keys){
			jo.put(k.toString(),handler.handler(maps.get(k)));
		}
		return StringUtils.singleQuotationMark(jo.toString());
	}
	/**
	 * 通过类名生产JSON
	 * @param clz 类名称
	 * @param t 
	 * @param fieldNames
	 * @return
	 */
	public static final <T> String classJson(Class<?> clz,T t,String[] fieldNames){
		List<T> array=new ArrayList<T>();
		array.add(t);
		JQueryData jd=new JQueryData(clz, array, fieldNames);
		jd.setPrefix(StringUtils.lowerFirst(clz.getName()));
		return StringUtils.singleQuotationMark(jd.build());
	}
	/**
	 * 通过类名生产JSON
	 * @param clz
	 * @param t
	 * @param fieldNames
	 * @param convertHandler
	 * @return
	 */
	public static final <T> String classJson(Class<?> clz,T t,String[] fieldNames,TypeConvert convertHandler){
		List<T> array=new ArrayList<T>();
		array.add(t);
		JQueryData jd=new JQueryData(clz, array, fieldNames,convertHandler);
		return StringUtils.singleQuotationMark(jd.build());
	}
}
