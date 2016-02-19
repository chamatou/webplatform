package cn.chamatou.commons.web.servlet;
/**
 * http请求参数过滤 
 */
public interface HttpRequestParamterFilter {
	/**
	 * 对传入的参数值进行过滤
	 * @param value
	 * @return
	 */
	public String filter(String value);
}
