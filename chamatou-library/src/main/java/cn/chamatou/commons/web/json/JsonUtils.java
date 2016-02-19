package cn.chamatou.commons.web.json;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cn.chamatou.commons.data.utils.CoderUtil;
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
		jd.setPrefix(StringUtils.lowerFirst(clz.getSimpleName()));
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
		jd.setPrefix(StringUtils.lowerFirst(clz.getSimpleName()));
		return StringUtils.singleQuotationMark(jd.build());
	}
	/**
	 * 将JSON解析为指定的类,格式如下,users,mer表示类名,包含的为mer值
	 * {'users':[{'name':'kaiyi'},{'age':'33'}],'mer':[{'name':'kaiyi'},{'age':'33'}]}
	 * @param clz
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static final <T> T parseToClass(Class<T> clz,String json,boolean isDecodeKey,boolean isDecodeValue,CompositeHandler handler){
		try{
			JSONObject jo=new JSONObject(json);
			String clzName=StringUtils.lowerFirst(clz.getSimpleName());
			JSONArray memberArray=(JSONArray) jo.get(clzName);
			Iterator<Object> memberIter=memberArray.iterator();
			JSONObject matchObject=null;
			while(memberIter.hasNext()){
				matchObject=(JSONObject) memberIter.next();
				break;
			}
			return fillClass(clz, matchObject, isDecodeKey, isDecodeValue, handler);
		}catch(JSONException e){
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static final <T> T fillClass(Class<T> clz,JSONObject jsonObject,boolean isDecodeKey,boolean isDecodeValue,CompositeHandler handler) throws InstantiationException, IllegalAccessException, JSONException, IllegalArgumentException, InvocationTargetException{
		T t=clz.newInstance();
		Iterator<String> fieldNames=jsonObject.keys();
		Method[] methods=clz.getMethods();
		while(fieldNames.hasNext()){
			String key=isDecodeKey?CoderUtil.base64Decode(fieldNames.next()):fieldNames.next();
			Method method=getMatchMethod(methods, "set"+StringUtils.upperFirst(key));
			if(method!=null){
				Object value=jsonObject.get(key);
				//value必须是String类型
				if(handler==null){
					if(isDecodeValue){
						value=CoderUtil.base64Decode(value.toString()).toString();
					}
					method.invoke(t,value);						
				}else{
					handler.composite(t, value);
				}
			}
		}
		return t;
	}
	/**
	 * 处理JSON转Object时的复合对象
	 *
	 */
	public interface CompositeHandler{
		/**
		 * 处理复合对象
		 * @param obj 已经初始化好的类
		 * @param value JSONValue
		 */
		void composite(Object obj,Object value);
	};
	//从Methods中获取匹配的方法
	private static Method getMatchMethod(Method[] methods,String methodName){
		for(Method m:methods){
			if(m.getName().equals(methodName)){
				return m;
			}
		}
		return null;
	}
}
