package cn.chamatou.commons.data.utils;
/**
 * 字符处理工具类
 */
public  final class StringUtils {
	@SuppressWarnings("restriction")
	private static String lineSeparator = java.security.AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction("line.separator"));
	/**
	 * 截取掉Java方法名set,get,并将首字母小写
	 * @return
	 */
	public final static String methodNameTrim(String methodName){
		if(methodName.startsWith("get")||methodName.startsWith("set")){
			methodName=methodName.substring("get".length(), methodName.length());
			char[] first=new char[]{methodName.charAt(0)};
			return new String(first).toLowerCase()+methodName.substring(1,methodName.length());
		}else{
			return methodName;
		}
	}
	/**首字母大写*/
	public final static String upperFirst(String str){
		String temp=str.toUpperCase();
		return temp.substring(0, 1)+str.substring(1, str.length());
	}
	/**首字母小写*/
	public final static String lowerFirst(String str){
		String temp=str.toLowerCase();
		return temp.substring(0, 1)+str.substring(1, str.length());
	}
	/**
	 * 计算text的长度（一个中文算两个字符）
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}
	public final static String newLine(){
		return StringUtils.lineSeparator;
	}
}
