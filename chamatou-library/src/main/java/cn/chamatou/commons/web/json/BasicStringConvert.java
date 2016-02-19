package cn.chamatou.commons.web.json;
/**
 * 常规字符回调
 *
 */
public class BasicStringConvert implements TypeConvert{
	@Override
	public String convert(Object obj, String name) {
		return obj==null?"":obj.toString().trim();
	}
}
