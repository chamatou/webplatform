package cn.chamatou.commons.web.json;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;



public class ExtjsGrid extends JSONData{
	private String gridName;
	private Long total;
	public ExtjsGrid(Class<?> clz, Collection<?> collection, String[] names,
			Map<String, String> convertMap, TypeConvert convertHandler) {
		super(clz, collection, names, convertMap, convertHandler);
	}

	public ExtjsGrid(Class<?> clz, Collection<?> collection, String[] names,
			Map<String, String> convertMap) {
		super(clz, collection, names, convertMap);
	}

	public ExtjsGrid(Class<?> clz, Collection<?> collection, String[] names,
			TypeConvert convertHandler) {
		super(clz, collection, names, convertHandler);
	}

	public ExtjsGrid(Class<?> clz, Collection<?> collection, String[] names) {
		super(clz, collection, names);
	}
	
	/**
	 * 设置表格名称
	 * @param gridName 表格显示名称
	 */
	public void setGridName(String gridName){
		this.gridName=gridName;
	}
	/**
	 * 设置获取的最大数
	 * @param total
	 */
	public void setTotal(Long total){
		this.total=total;
	}
	@Override
	public String build() {
		if(collection.isEmpty()){
			return "{'success':true,'total':0}";
		}
		StringBuilder builder=new StringBuilder("{'success':true,'total':"
				+(total==null?collection.size():total)+",'"+(gridName==null?clz.getSimpleName():gridName)+"':[");
		int index=0;
		for(Iterator<?> it=collection.iterator();it.hasNext();){
			builder.append("{");
			Object obj=it.next();
			for (int n = 0; n < names.length; n++) {
				String methodName=names[n];
				builder.append("'").append(getConvertName(methodName)).append("'").append(":");
				String value="";
				Object callback=callGetter(obj, methodName);
				if(callback!=null){
					value=convertHandler.convert(callback, methodName);	
					if(value==null){
						value=defaultConvert.convert(callback, methodName);
					}
				}
				builder.append("'").append(value).append("'");
				if (n < names.length - 1) {
					builder.append(",");
				}
			}
			builder.append("}");
			if (index < collection.size() - 1) {
				builder.append(",");
			}
			++index;
		}
		builder.append("]}");
		return builder.toString();
	}

}
