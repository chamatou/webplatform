package cn.chamatou.commons.web.servlet;
/**
 * 对Html字符进行编码
 *
 */
public class HtmlEscapeFilter implements HttpRequestParamterFilter{

	@Override
	public String filter(String value) {
		return HtmlConvertUtils.htmlEscape(value);
	}
}
