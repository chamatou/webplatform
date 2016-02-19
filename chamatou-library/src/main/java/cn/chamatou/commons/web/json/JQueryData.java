package cn.chamatou.commons.web.json;

import java.util.Collection;
import java.util.Map;
/**
 * jquery能解析的JSON数据
 * @Package:net.sourceforge.common.webjson.data
 * @Title:JQueryData.java
 * @version:1.0
 * @Description:格式类似
 * {"comments":[{"content":"很不错嘛","id":1,"nickname":"纳尼"},{"content":"哟西哟西","id":2,"nickname":"小强"}]}
 */
public class JQueryData extends ExtjsData{
	public JQueryData(Class<?> clz, Collection<?> collection, String[] names) {
		super(clz, collection, names);
	}
	
	public JQueryData(Class<?> clz, Collection<?> collection, String[] names,
			Map<String, String> convertMap, TypeConvert convertHandler) {
		super(clz, collection, names, convertMap, convertHandler);
		
	}

	public JQueryData(Class<?> clz, Collection<?> collection, String[] names,
			Map<String, String> convertMap) {
		super(clz, collection, names, convertMap);
	}
	public JQueryData(Class<?> clz, Collection<?> collection, String[] names,
			TypeConvert convertHandler) {
		super(clz, collection, names, convertHandler);
	}
	@Override
	public String build() {
		//{"comments":[{"content":"很不错嘛","id":1,"nickname":"纳尼"},{"content":"哟西哟西","id":2,"nickname":"小强"}]}
		String jsonData=super.build();
		if(prefixFlag){
			jsonData="{"+jsonData+"}";
		}
		return jsonData.replaceAll("'","\"");
	}

}
