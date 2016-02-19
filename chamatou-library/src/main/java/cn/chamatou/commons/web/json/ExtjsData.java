package cn.chamatou.commons.web.json;



import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ExtJS数据实现,普通HTML页面可调用eval()进行解析
 *
 */
public class ExtjsData extends JSONData{
	protected boolean prefixFlag=false;
	protected String prefix;
	public ExtjsData(Class<?> clz, Collection<?> collection, String[] names) {
		super(clz, collection, names);
	}
	
	public ExtjsData(Class<?> clz, Collection<?> collection, String[] names,
			Map<String, String> convertMap, TypeConvert convertHandler) {
		super(clz, collection, names, convertMap, convertHandler);
	}

	public ExtjsData(Class<?> clz, Collection<?> collection, String[] names,
			Map<String, String> convertMap) {
		super(clz, collection, names, convertMap);
	}

	public ExtjsData(Class<?> clz, Collection<?> collection, String[] names,
			TypeConvert convertHandler) {
		super(clz, collection, names, convertHandler);
	}
	/**
	 * 设置输出前缀
	 * @param flag 是否输出
	 */
	public void setPrefixFlag(boolean flag){
		this.prefixFlag=flag;
		this.prefixFlag=true;
	}
	/**
	 * 设置输出前缀
	 * @param prefix
	 */
	public void setPrefix(String prefix){
		this.prefix=prefix;
	}
	/** Extjs数据构建,类型如下,如果需要"user"前缀，需要调用setPrefix方法
	 * "users": [
        { "name": "Lisa", "email": "lisa@simpsons.com", "phone": "555-111-1224" },
        { "name": "Bart", "email": "bart@simpsons.com", "phone": "555-222-1234" },
        { "name": "Homer", "email": "home@simpsons.com", "phone": "555-222-1244" },
        { "name": "Marge", "email": "marge@simpsons.com", "phone": "555-222-1254" }
    ]
	 */
	@Override
	public String build() {
		StringBuilder builder=new StringBuilder();
		if(this.prefixFlag){
			builder.append("'").append(this.prefix).append("':");			
		}
		builder.append("[");
		for(Iterator<?> it=collection.iterator();it.hasNext();){
			builder.append("{");
			Object obj=it.next();
			for(int index=0;index<this.names.length;index++){
				String methodName=names[index];
				builder.append("'").append(getConvertName(methodName)).append("'").append(":");
				Object callBack=this.callGetter(obj,methodName);
				String value="";
				if(callBack!=null){
					value=this.convertHandler.convert(callBack,methodName);
					if(value==null){
						value=defaultConvert.convert(callBack, methodName);
					}
				}
				builder.append("'").append(value).append("'");
				if (index < names.length - 1) {
					builder.append(",");
				}
			}
			builder.append("},");
		}
		tryDeleteAtLast(builder);
		builder.append("]");
		return builder.toString();
	}
	/**
	 * 获取Extjs 返回简单json交互数据
	 * @param isSuccess 是否成功
	 * @param message 返回消息
	 * @return
	 */
	public static final String getFormJson(boolean isSuccess,String message){
		return "{\"success\":"+(isSuccess?"\"true\"":"\"false\"") + ",\"msg\":\"" + message + "\"}";
	}
	/**
	 * 获取Extjs 返回简单json交互数据
	 * @param isSuccess 是否成功
	 * @param message 返回消息
	 * @return
	 */
	public static final String getFormJson(boolean isSuccess,Map<String, Object> message){
		StringBuilder builder=new StringBuilder("{");
		builder.append("\"success\":").append(isSuccess?"\"true\",":"\"false\",");
		if(message.isEmpty()){
			builder.append("\"msg\":\"\"");
		}else{
			Set<String> keys=message.keySet();
			int index=0;
			for(String key:keys){
				Object obj=message.get(key);
				if(obj instanceof Integer){
					builder.append("\"").append(key).append("\":").append(obj.toString());
				}else if(obj instanceof Float){
					builder.append("\"").append(key).append("\":").append(obj.toString());
				}else if(obj instanceof Double){
					builder.append("\"").append(key).append("\":").append(obj.toString());
				}else if(obj instanceof Boolean){
					builder.append("\"").append(key).append("\":").append(obj.toString());
				}else if(obj instanceof Float){
					builder.append("\"").append(key).append("\":").append(obj.toString());
				}else{
					builder.append("\"").append(key).append("\":\"").append(obj.toString()).append("\"");
				}
				if(index!=keys.size()-1){
					builder.append(",");
				}
			}
		}
		builder.append("}");
		return builder.toString();
	}
}
