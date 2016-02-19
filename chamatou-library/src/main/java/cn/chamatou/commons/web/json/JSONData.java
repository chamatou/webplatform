package cn.chamatou.commons.web.json;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cn.chamatou.commons.data.utils.StringUtils;

/**
 * JSON数据
 *
 */
public abstract class JSONData {
	protected Map<String, String> convertMap;
	protected TypeConvert convertHandler;
	protected Collection<?> collection;
	protected Class<?> clz;
	protected String[] names;
	private Method[] methods;
	protected TypeConvert defaultConvert;
	private boolean needBase64Key;
	private boolean needBase64Value;
	/**
	 * JSON数据构造方法
	 * @param clz 类名称
	 * @param collection 包含此类的集合
	 * @param names 类中属性,需要提供public get方法
	 */
	public JSONData(Class<?> clz,Collection<?> collection,String[] names){
		this(clz, collection, names, null, null);
	}
	/**
	 * JSON数据构造方法
	 * @param clz 类名称
	 * @param collection 包含此类的集合
	 * @param names 类中属性,需要提供public get方法
	 * @param convertMap 如果需要将类属性的输出改变为其他名称,传入此参数.key:类属性,value:需要输出的名称
	 */
	public JSONData(Class<?> clz,Collection<?> collection,String[] names,Map<String, String> convertMap){
		this(clz, collection, names, convertMap, null);
	}
	/**
	 * JSON数据构造方法
	 * @param clz 类名称
	 * @param collection 包含此类的集合
	 * @param names 类中属性,需要提供public get方法
	 * @param convertHandler 特殊类型转换
	 */
	public JSONData(Class<?> clz,Collection<?> collection,String[] names,TypeConvert convertHandler){
		this(clz, collection, names, null, convertHandler);
	}
	/**
	 * JSON数据构造方法
	 * @param clz 类名称
	 * @param collection 包含此类的集合
	 * @param names 类中属性,需要提供public get方法
	 * @param convertMap 如果需要将类属性的输出改变为其他名称,传入此参数.key:类属性,value:需要输出的名称
	 * @param convertHandler 特殊类型转换
	 */
	public JSONData(Class<?> clz,Collection<?> collection,String[] names,Map<String, String> convertMap,TypeConvert convertHandler){
		this.clz=clz;
		this.needBase64Key=false;
		this.needBase64Value=false;
		methods=clz.getMethods();
		this.collection=collection;
		this.names=names;
		defaultConvert=new BasicStringConvert();
		if(null!=convertMap&&!convertMap.isEmpty()){
			this.convertMap=convertMap;
		}else{
			this.convertMap=new HashMap<String, String>();
		}
		if(convertHandler==null){
			this.convertHandler=defaultConvert;
		}else{
			this.convertHandler=convertHandler;
		}
	}
	/**
	 * 设置是否对Key进行Base64编码
	 */
	public void needBase64Key(){
		needBase64Key=true;
	}
	/**
	 * 设置是否对Value进行Base64编码
	 */
	public void needBase64Value(){
		needBase64Value=true;
	}
	/**
	 * 获取转换后的名称
	 * @param name
	 * @return
	 */
	protected String getConvertName(String name){
		String cn=convertMap.get(name);
		return cn==null?name:cn;
	}
	/**
	 * 获取Getter方法返回值
	 * @param obj 对象实例
	 * @param name 调用的get方法名称
	 * @return 返回方法调用后的对象,方法调用失败返回空
	 */
	protected Object callGetter(Object obj,String name){
		for(Method method:methods){
			String methodName = method.getName();
			//方法名称为get开头并且截取掉get后与name一致
			if(methodName.startsWith("get")&&
					StringUtils.methodNameTrim(methodName).equals(name)){
				try {
					return method.invoke(obj);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 对输出Key字段进行转换
	 * @param converNames
	 */
	public void setConvertNames(Map<String, String> convertMap){
		if(null!=convertMap&&!convertMap.isEmpty()){
			this.convertMap=convertMap;
		}
	}
	/**
	 * 构建JSON数据
	 */
	public abstract String build();
	/**
	 * 对特殊数据类型进行转换
	 * @param handler
	 */
	public void setHandler(TypeConvert convertHandler){
		if(convertHandler!=null){
			this.convertHandler=convertHandler;			
		}else{
			this.convertHandler=new BasicStringConvert();
		}
	}
	protected void tryDeleteAtLast(StringBuilder builder){
		if(!collection.isEmpty()){
			builder.deleteCharAt(builder.length()-1);
		}
	}
	public boolean isNeedBase64Key() {
		return needBase64Key;
	}
	public void setNeedBase64Key(boolean needBase64Key) {
		this.needBase64Key = needBase64Key;
	}
	public boolean isNeedBase64Value() {
		return needBase64Value;
	}
	public void setNeedBase64Value(boolean needBase64Value) {
		this.needBase64Value = needBase64Value;
	}
	
}
