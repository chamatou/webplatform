package cn.chamatou.commons.web.servlet;
/**
 * HTTP请求参数转换
 * @author kaiyi
 *
 */
public interface HttpRequestHandler{
	/**
	 * 自行对请求参数进行转换
	 * @param name 参数名称
	 * @param params 参数值
	 * @param obj 实体对象
	 */
	public void handler(String name,String[] params,final Object obj);
}
