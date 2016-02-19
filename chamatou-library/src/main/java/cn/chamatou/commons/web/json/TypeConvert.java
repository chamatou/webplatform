package cn.chamatou.commons.web.json;
/**
 * JSON数据回调接口
 * 构建JSON数据时通过此接口定义的generate方法来处理特殊字段的值转换
 */
public interface TypeConvert {
	/**
	 * 返回此obj的字符串表现形式
	 * @param obj 对象
	 * @param name 调用方法名称
	 * @return
	 */
	String convert(Object obj,String methodName);
}
