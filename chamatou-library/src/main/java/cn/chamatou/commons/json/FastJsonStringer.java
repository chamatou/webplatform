package cn.chamatou.commons.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import cn.chamatou.commons.data.utils.StringUtils;
/**
 * 快速构建JSON字符串
 */
public class FastJsonStringer {
	/**
	 * 用Key和Value构建JSON，格式如下 {"json-key":"json-vaue"}
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static final String toJsonString(String key, String value) {
		JSONObject jo = new JSONObject();
		jo.put(key, value);
		return jo.toString();
	}

	/**
	 * 用Map构建JSON字符串，格式如下 {"json-key":"json-vaue"}
	 * 
	 * @param map
	 * @return
	 */
	public static final String toJsonString(Map<String, Object> map) {
		return buildToMap(map, null, null);
	}

	/**
	 * 用Map构建JSON字符串，格式如下 {"json-key":"json-vaue"}
	 * 
	 * @param map
	 * @param keyPolicy
	 *            Key替换策略
	 * @return
	 */
	public static final String toJsonString(Map<String, Object> map, ReplaceKeyPolicy keyPolicy) {
		return buildToMap(map, keyPolicy, null);
	}

	/**
	 * 用Map构建JSON字符串，格式如下 {"json-key":"json-vaue"}
	 * 
	 * @param map
	 * @param valuePolicy
	 *            取值策略
	 * @return
	 */
	public static final String toJsonString(Map<String, Object> map, ValuePolicy valuePolicy) {
		return buildToMap(map, null, valuePolicy);
	}

	/**
	 * 用Map构建JSON字符串，格式如下 {"json-key":"json-vaue"}
	 * 
	 * @param map
	 * @param keyPolicy
	 *            Key替换策略
	 * @param valuePolicy
	 *            取值策略
	 * @return
	 */
	public static final String toJsonString(Map<String, Object> map, ReplaceKeyPolicy keyPolicy,
			ValuePolicy valuePolicy) {
		return buildToMap(map, keyPolicy, valuePolicy);
	}
	/**
	 * 用集合构建JSON字符串，格式如下
	 * {"testJsonBean":[{"name":"name_1","age":"33"},{"name":"name_1","age":"33"}]}
	 * @param c 集合类
	 * @param reflectKey 需要反射集合的key
	 * @return
	 */
	public static final <T> String collection2JsonString(Collection<T> c,String[] reflectKey){
		return collection2JsonString(c, reflectKey,null,null);
	}
	/**
	 * 用集合构建JSON字符串，格式如下
	 * {"testJsonBean":[{"name":"name_1","age":"33"},{"name":"name_1","age":"33"}]}
	 * @param c 集合类
	 * @param reflectKey 需要反射集合的key
	 * @param keyPolicy Key替换策略
	 * @return
	 */
	public static final <T> String collection2JsonString(Collection<T> c,String[] reflectKey,ReplaceKeyPolicy keyPolicy){
		return collection2JsonString(c, reflectKey, keyPolicy,null);
	}
	/**
	 用集合构建JSON字符串，格式如下
	 * {"testJsonBean":[{"name":"name_1","age":"33"},{"name":"name_1","age":"33"}]}
	 * @param c
	 * @param reflectKey 需要反射集合的key
	 * @param valuePolicy 取值策略
	 * @return
	 */
	public static final <T> String collection2JsonString(Collection<T> c,String[] reflectKey,ValuePolicy valuePolicy){
		return collection2JsonString(c, reflectKey,null,valuePolicy);
	}
	/**
	 * 用集合构建JSON字符串，格式如下
	 * {"testJsonBean":[{"name":"name_1","age":"33"},{"name":"name_1","age":"33"}]}
	 * @param c 集合类
	 * @param reflectKey 需要反射集合的key
	 * @param keyPolicy Key替换策略
	 * @param valuePolicy  取值策略
	 * @return
	 */
	public static final <T> String collection2JsonString(Collection<T> c,String[] reflectKey,ReplaceKeyPolicy keyPolicy,
			ValuePolicy valuePolicy){
		if(c==null||c.isEmpty())throw new IllegalArgumentException("Collection is null.");
		if(reflectKey==null||reflectKey.length==0)throw new IllegalArgumentException("reflectKeyis null.");
		try {
			Class<?> clz = c.iterator().next().getClass();
			Method[] methods=clz.getMethods();
			String className = StringUtils.lowerFirst(clz.getSimpleName());
			JSONArray array = toJsonArray(c,reflectKey, keyPolicy, valuePolicy, methods);
			JSONObject root = new JSONObject();
			root.put(className, array);
			return root.toString();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从一个类来构建JSON字符，格式如下
	 * {"testJsonBean":[{"age":"33"},{"name":"name_1"}]}
	 * @param t 需要构建JSON的类
	 * @param reflectKey 需要反射集合的key
	 * @return
	 */
	public static final <T> String object2String(T t, String[] reflectKey){
		return object2String(t, reflectKey);
	}
	/**
	 * 从一个类来构建JSON字符，格式如下
	 * {"testJsonBean":[{"age":"33"},{"name":"name_1"}]}
	 * @param t 需要构建JSON的类
	 * @param reflectKey 需要反射集合的key
	 * @param valuePolicy  取值策略
	 * @return
	 */
	public static final <T> String object2String(T t, String[] reflectKey,
			ValuePolicy valuePolicy) {
		return object2String(t, reflectKey,null,valuePolicy);
	}
	/**
	 * 从一个类来构建JSON字符，格式如下
	 * {"testJsonBean":[{"age":"33"},{"name":"name_1"}]}
	 * @param t 需要构建JSON的类
	 * @param reflectKey 需要反射集合的key
	 * @param keyPolicy Key替换策略
	 * @return
	 */
	public static final <T> String object2String(T t, String[] reflectKey,ReplaceKeyPolicy keyPolicy) {
		return object2String(t, reflectKey, keyPolicy,null);
	}
	/**
	 * 从一个类来构建JSON字符，格式如下
	 * {"testJsonBean":[{"age":"33"},{"name":"name_1"}]}
	 * @param t 需要构建JSON的类
	 * @param reflectKey 需要反射集合的key
	 * @param keyPolicy Key替换策略
	 * @param valuePolicy  取值策略
	 * @return
	 */
	public static final <T> String object2String(T t, String[] reflectKey,ReplaceKeyPolicy keyPolicy,
			ValuePolicy valuePolicy) {
		List<T> list=new ArrayList<T>();
		list.add(t);
		return collection2JsonString(list,reflectKey,keyPolicy,valuePolicy);
	}
	/**
	 * 返回JSON数组
	 * @param reflectKey
	 * @param keyPolicy
	 * @param valuePolicy
	 * @param methods
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static final <T> JSONArray toJsonArray(Collection<?> c,String[] reflectKey, ReplaceKeyPolicy keyPolicy, ValuePolicy valuePolicy,
			Method[] methods) throws IllegalAccessException, InvocationTargetException {
		JSONArray array = new JSONArray();
		for(Iterator<?> it=c.iterator();it.hasNext();){
			Object obj=it.next();
			JSONObject jo = new JSONObject();
			for (String key : reflectKey) {
				String methodName="get"+StringUtils.upperFirst(key);
				Method method=getMatchMethod(methods, methodName);
				Object value=method.invoke(obj);
				jo.put(keyPolicy!=null?keyPolicy.replace(key):key,valuePolicy!=null?valuePolicy.getValue(value):value.toString());
			}
			array.put(jo);
		}
		return array;
	}

	private static final String buildToMap(Map<String, Object> map, ReplaceKeyPolicy keyPolicy,
			ValuePolicy valuePolicy) {
		JSONObject root = new JSONObject();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			String l = key;
			if (keyPolicy != null) {
				l = keyPolicy.replace(key);
			}
			String v = map.get(key).toString();
			if (valuePolicy != null) {
				v = valuePolicy.getValue(map.get(key));
			}
			root.put(l, v);
		}
		return root.toString();
	}

	// 从Methods中获取匹配的方法
	private static final Method getMatchMethod(Method[] methods, String methodName) {
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}
	/**
	 * 组合多个JSON字符串,组合的字符串必须以{...}开头和结尾
	 * @param jsons
	 * @return
	 */
	public static final String linkMultiJsons(String... jsons){
		StringBuilder builder=new StringBuilder();
		for(int i=1;i<=jsons.length;i++){
			String j=jsons[i-1];
			if(!j.startsWith("{")||!j.endsWith("}")){
				throw new IllegalArgumentException("Must start with \"{\" an end with \"}\"");
			}
			if(i==1){
				//第一次,替换最后一个}字符为,号
				builder.append(j.substring(0, j.length()-1)).append(",");
			}else if(i==jsons.length){
				//添加最后一个}号
				builder.append(j.substring(1, j.length()));
			}else{
				builder.append(",").append(j.substring(1, j.length()));
			}
		}
		return builder.toString();
	}
}
