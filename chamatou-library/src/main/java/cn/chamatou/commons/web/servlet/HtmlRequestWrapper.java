package cn.chamatou.commons.web.servlet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 * Http请求参数封装,对http提交参数进行过滤
 */
public class HtmlRequestWrapper extends HttpServletRequestWrapper{
	private HttpRequestParamterFilter filter;
	
	private List<String> exclude;
	public HtmlRequestWrapper(HttpServletRequest request,HttpRequestParamterFilter filter) {
		this(request, filter, new ArrayList<String>(0));
	}
	/**
	 * 
	 * @param request
	 * @param filter 
	 * @param exclude 排除掉不用过滤的参数名,调用
	 */
	public HtmlRequestWrapper(HttpServletRequest request,HttpRequestParamterFilter filter,List<String> exclude) {
		super(request);
		this.filter=filter;
		this.exclude=exclude;
	}
	@Override
	public String getParameter(String paramName) {
		String obj = super.getParameter(paramName);
		if (obj == null){
			return obj;
		}else if(exclude.contains(paramName)){
			return obj;
		}
		return filter.filter(obj);
	}
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> maps = super.getParameterMap();
		Set<String> keys = maps.keySet();
		for (String key : keys) {
			if(!exclude.contains(key)){
				String[] values = maps.get(key);
				if (values != null) {
					String[] newValues = new String[values.length];
					for (int i = 0; i < values.length; i++) {
						if (values[i] == null) {
							newValues[i] = null;
						} else {
							newValues[i] = filter.filter(values[i]);
						}
					}
					maps.put(key, newValues);
				}
			}
		}
		return maps;
	}

	@Override
	public String[] getParameterValues(String paramName) {
		String[] values = super.getParameterValues(paramName);
		if(!exclude.contains(paramName)){
			String[] newValues = null;
			if (values != null) {
				newValues = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					if (values[i] == null) {
						newValues[i] = null;
					} else {
						newValues[i] = filter.filter(values[i]);
					}
				}
			}
			return newValues;			
		}
		return values;
	}
}
