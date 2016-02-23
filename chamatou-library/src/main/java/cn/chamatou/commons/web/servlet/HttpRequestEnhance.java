package cn.chamatou.commons.web.servlet;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import cn.chamatou.commons.data.utils.StringUtils;

/**
 * Http参数
 */
public class HttpRequestEnhance {
	private HttpServletRequest request;
	public HttpRequestEnhance(HttpServletRequest request){
		this.request=request;
	}
	/**
	 * 获取字符串参数
	 * @param name 参数名
	 * @param defalut 当无法获取时返回的默认参数
	 * @return
	 */
	public String getString(String name,String defaultVal){
		String p=getParameter(name);
		if(p==null){
			return defaultVal;
		}
		return p;
	}
	/**
	 * 获取日期类型
	 * @param name 参数名称
	 * @param format 日期格式化类
	 * @param date 默认日期
	 * @return
	 */
	public Date getDate(String name,SimpleDateFormat format,Date date){
		String p=getParameter(name);
		try{
			return format.parse(p);
		}catch(Exception e){
			return date;
		}
	}
	/**
	 * 获取金额
	 * @param name 参数名称
	 * @param scale 小数点位数
	 * @param decimal 默认金额
	 * @return
	 */
	public BigDecimal getBigDecimal(String name,int scale,BigDecimal decimal){
		String p=getParameter(name);
		try{
			BigDecimal d=new BigDecimal(p);
			d.setScale(scale);
			return d;
		}catch(Exception e){
			return decimal;
		}
	}
	/**
	 * 获取boolean类型
	 * @param name 参数名称
	 * @param match 匹配字符,例如前端传入字符为on
	 * @param defaultVal 默认返回值
	 * @return
	 */
	public Boolean getBoolean(String name,String match,Boolean defaultVal){
		String p=getParameter(name);
		if(p==null){
			return defaultVal;
		}
		return p.equals(match);
	}
	/**
	 * 获取Double类型
	 * @param name 参数名称
	 * @param defaultVal 默认值
	 * @return
	 */
	public Double getDouble(String name,Double defaultVal){
		String p=getParameter(name);
		try{
			return Double.parseDouble(p);
		}catch(Exception e){
			return defaultVal;
		}
	}
	/**
	 * 获取float类
	 * @param name 参数名称
	 * @param defaultVal 默认值
	 * @return
	 */
	public Float getFloat(String name,Float defaultVal){
		String p=getParameter(name);
		try{
			return Float.parseFloat(p);
		}catch(Exception e){
			return defaultVal;
		}
	}
	/**
	 * 获取整型参数
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public Integer getInteger(String name,Integer defaultVal){
		String p=getParameter(name);
		try{
			return Integer.parseInt(p);
		}catch(Exception e){
			return defaultVal;
		}
	}
	public String getParameter(String name){
		return request.getParameter(name);
	}
	/**
	 * 将HttpRequest请求参数转换为指定类,方法调用传入clz文件的set方法,
	 * class需要有无参的构造方法,前台传入参数遵循javabean规则.
	 * 可以构建的参数类型包括String,Short,Integer,Boolean,Double,Float,Long等基础类型,
	 * 如果为特殊类型或多参数类型,需要自行指定转换器
	 * 
	 * @param request
	 * @param clz
	 * @return 转换失败返回nul
	 */
	@SuppressWarnings("unchecked")
	public  <T> T toObject(Class<T> clz,HttpRequestHandler handler) {
		try {
			Object newClz = clz.getClass().newInstance();
			Method[] methods = clz.getMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("set")) {
					String param = StringUtils.methodNameTrim(method.getName());
					String[] values = request.getParameterValues(param);
					if (values != null) {// 参数为空不处理
						if (values.length == 1) {// 处理参数为1的情况
							Class<?>[] paramters = method.getParameterTypes();
							if (paramters.length == 0 || paramters.length > 1) {
								throw new IllegalArgumentException(
										"Setter length error.length:"
												+ paramters.length);
							} else {
								String typeName = paramters[0].getName();
								Object paramObj = null;
								try {
									if (typeName.equals("int")
											|| typeName
													.equals("java.lange.Integer")) {
										paramObj = Integer.valueOf(values[0]);
									} else if (typeName.equals("short")
											|| typeName
													.equals("java.lange.Short")) {
										paramObj = Short.valueOf(values[0]);
									} else if (typeName.equals("double")
											|| typeName
													.equals("java.lange.Double")) {
										paramObj = Double.valueOf(values[0]);
									} else if (typeName.equals("float")
											|| typeName
													.equals("java.lange.Float")) {
										paramObj = Float.valueOf(values[0]);
									} else if (typeName
											.equals("java.lange.String")) {
										paramObj = String.valueOf(values[0]);
									} else if (typeName.equals("boolean")
											|| typeName
													.equals("java.lange.Boolean")) {
										String bv = values[0];
										if (!bv.equalsIgnoreCase("true")
												|| !bv.equalsIgnoreCase("false")) {
											if (bv.equals("0")) {
												bv = "false";
											} else {
												bv = "true";
											}
										}
										paramObj = Boolean.valueOf(bv);
									} else if (typeName.equals("long")
											|| typeName
													.equals("java.lange.Long")) {
										paramObj = Long.valueOf(values[0]);
									}
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
								if (paramObj != null) {
									method.invoke(newClz, paramObj);
								} else if (handler != null) {
									handler.handler("param", values, newClz);
								} else {
									throw new IllegalArgumentException(
											"Can't convert parameter type and ConvertHttpRequestHandler is null.");
								}
							}
						} else {// 处理参数大于1的情况
							if (handler == null) {
								throw new IllegalArgumentException(
										"Mutil parameter and HttpRequestHandler is null.");
							}
						} 
					}
				}
			}
			return (T)newClz;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 判定登陆是否来自手机客户端
	 * 
	 * @return
	 */
	public static final  boolean isMobile(HttpServletRequest request) {
		String mobileGateWayHeaders[] = new String[] { "ZXWAP",// 中兴提供的wap网关的via信息，例如：Via=ZXWAP
																// GateWayZTE
																// Technologies，
				"chinamobile.com",// 中国移动的诺基亚wap网关，例如：Via=WTP/1.1
									// GDSZ-PB-GW003-WAP07.gd.chinamobile.com
									// (Nokia WAP Gateway 4.1
									// CD1/ECD13_D/4.1.04)
				"monternet.com",// 移动梦网的网关，例如：Via=WTP/1.1
								// BJBJ-PS-WAP1-GW08.bj1.monternet.com. (Nokia
								// WAP Gateway 4.1 CD1/ECD13_E/4.1.05)
				"infoX",// 华为提供的wap网关，例如：Via=HTTP/1.1 GDGZ-PS-GW011-WAP2
						// (infoX-WISG Huawei Technologies)，或Via=infoX WAP
						// Gateway V300R001 Huawei Technologies
				"XMS 724Solutions HTG",// 国外电信运营商的wap网关，不知道是哪一家
				"wap.lizongbo.com",// 自己测试时模拟的头信息
				"Bytemobile",// 貌似是一个给移动互联网提供解决方案提高网络运行效率的，例如：Via=1.1 Bytemobile
								// OSN WebProxy/5.1
		};
		/** 电脑上的IE或Firefox浏览器等的User-Agent关键词 */
		String[] pcHeaders = new String[] { "Windows 98", "Windows ME",
				"Windows 2000", "Windows XP", "Windows NT", "Ubuntu" };
		/** 手机浏览器的User-Agent里的关键词 */
		String[] mobileUserAgents = new String[] { "Nokia",// 诺基亚，有山寨机也写这个的，总还算是手机，Mozilla/5.0
															// (Nokia5800
															// XpressMusic)UC
															// AppleWebkit(like
															// Gecko) Safari/530
				"SAMSUNG",// 三星手机
							// SAMSUNG-GT-B7722/1.0+SHP/VPP/R5+Dolfin/1.5+Nextreaming+SMM-MMS/1.2.0+profile/MIDP-2.1+configuration/CLDC-1.1
				"MIDP-2",// j2me2.0，Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2
							// NokiaE75-1 /110.48.125 Profile/MIDP-2.1
							// Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML
							// like Gecko) Safari/413
				"CLDC1.1",// M600/MIDP2.0/CLDC1.1/Screen-240X320
				"SymbianOS",// 塞班系统的，
				"MAUI",// MTK山寨机默认ua
				"UNTRUSTED/1.0",// 疑似山寨机的ua，基本可以确定还是手机
				"Windows CE",// Windows CE，Mozilla/4.0 (compatible; MSIE 6.0;
								// Windows CE; IEMobile 7.11)
				"iPhone",// iPhone是否也转wap？不管它，先区分出来再说。Mozilla/5.0 (iPhone; U;
							// CPU iPhone OS 4_1 like Mac OS X; zh-cn)
							// AppleWebKit/532.9 (KHTML like Gecko) Mobile/8B117
				"iPad",// iPad的ua，Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS
						// X; zh-cn) AppleWebKit/531.21.10 (KHTML like Gecko)
						// Version/4.0.4 Mobile/7B367 Safari/531.21.10
				"Android",// Android是否也转wap？Mozilla/5.0 (Linux; U; Android
							// 2.1-update1; zh-cn; XT800 Build/TITA_M2_16.22.7)
							// AppleWebKit/530.17 (KHTML like Gecko) Version/4.0
							// Mobile Safari/530.17
				"BlackBerry",// BlackBerry8310/2.7.0.106-4.5.0.182
				"UCWEB",// ucweb是否只给wap页面？ Nokia5800
						// XpressMusic/UCWEB7.5.0.66/50/999
				"ucweb",// 小写的ucweb貌似是uc的代理服务器Mozilla/6.0 (compatible; MSIE
						// 6.0;) Opera ucweb-squid
				"BREW",// 很奇怪的ua，例如：REW-Applet/0x20068888 (BREW/3.1.5.20;
						// DeviceId: 40105; Lang: zhcn) ucweb-squid
				"J2ME",// 很奇怪的ua，只有J2ME四个字母
				"YULONG",// 宇龙手机，YULONG-CoolpadN68/10.14 IPANEL/2.0 CTC/1.0
				"YuLong",// 还是宇龙
				"COOLPAD",// 宇龙酷派YL-COOLPADS100/08.10.S100 POLARIS/2.9 CTC/1.0
				"TIANYU",// 天语手机TIANYU-KTOUCH/V209/MIDP2.0/CLDC1.1/Screen-240X320
				"TY-",// 天语，TY-F6229/701116_6215_V0230 JUPITOR/2.2 CTC/1.0
				"K-Touch",// 还是天语K-Touch_N2200_CMCC/TBG110022_1223_V0801
							// MTK/6223 Release/30.07.2008 Browser/WAP2.0
				"Haier",// 海尔手机，Haier-HG-M217_CMCC/3.0 Release/12.1.2007
						// Browser/WAP2.0
				"DOPOD",// 多普达手机
				"Lenovo",// 联想手机，Lenovo-P650WG/S100 LMP/LML Release/2010.02.22
							// Profile/MIDP2.0 Configuration/CLDC1.1
				"LENOVO",// 联想手机，比如：LENOVO-P780/176A
				"HUAQIN",// 华勤手机
				"AIGO-",// 爱国者居然也出过手机，AIGO-800C/2.04 TMSS-BROWSER/1.0.0 CTC/1.0
				"CTC/1.0",// 含义不明
				"CTC/2.0",// 含义不明
				"CMCC",// 移动定制手机，K-Touch_N2200_CMCC/TBG110022_1223_V0801
						// MTK/6223 Release/30.07.2008 Browser/WAP2.0
				"DAXIAN",// 大显手机DAXIAN X180 UP.Browser/6.2.3.2(GUI) MMP/2.0
				"MOT-",// 摩托罗拉，MOT-MOTOROKRE6/1.0 LinuxOS/2.4.20
						// Release/8.4.2006 Browser/Opera8.00 Profile/MIDP2.0
						// Configuration/CLDC1.1 Software/R533_G_11.10.54R
				"SonyEricsson",// 索爱手机，SonyEricssonP990i/R100 Mozilla/4.0
								// (compatible; MSIE 6.0; Symbian OS; 405) Opera
								// 8.65 [zh-CN]
				"GIONEE",// 金立手机
				"HTC",// HTC手机
				"ZTE",// 中兴手机，ZTE-A211/P109A2V1.0.0/WAP2.0 Profile
				"HUAWEI",// 华为手机，
				"webOS",// palm手机，Mozilla/5.0 (webOS/1.4.5; U; zh-CN)
						// AppleWebKit/532.2 (KHTML like Gecko) Version/1.0
						// Safari/532.2 Pre/1.0
				"GoBrowser",// 3g
							// GoBrowser.User-Agent=Nokia5230/GoBrowser/2.0.290
							// Safari
				"IEMobile",// Windows CE手机自带浏览器，
				"WAP2.0"// 支持wap 2.0的
		};
		boolean pcFlag = false;
		boolean mobileFlag = false;
		String via = request.getHeader("Via");
		String userAgent = request.getHeader("user-agent");
		for (int i = 0; via != null && !via.trim().equals("")
				&& i < mobileGateWayHeaders.length; i++) {
			if (via.contains(mobileGateWayHeaders[i])) {
				mobileFlag = true;
				break;
			}
		}
		for (int i = 0; !mobileFlag && userAgent != null
				&& !userAgent.trim().equals("") && i < mobileUserAgents.length; i++) {
			if (userAgent.contains(mobileUserAgents[i])) {
				mobileFlag = true;
				break;
			}
		}
		for (int i = 0; userAgent != null && !userAgent.trim().equals("")
				&& i < pcHeaders.length; i++) {
			if (userAgent.contains(pcHeaders[i])) {
				pcFlag = true;
			}
		}
		if (mobileFlag == true && mobileFlag != pcFlag) {
			return true;
		}
		return false;
	}
	/**
	 * 打印请求头
	 * @param request
	 */
	public static void printRequestHeaders(HttpServletRequest request){
		Enumeration<String> names=request.getHeaderNames();
		System.out.println("--start print request header--");
		for(;names.hasMoreElements();){
			String name=names.nextElement();
			System.out.println("k:"+name+",v:"+request.getHeader(name));
		}
		System.out.println("--end print request header--");
	}
	/**
	 * 打印请求参数
	 * @param request
	 */
	public static void printRequestParameters(HttpServletRequest request){
		Enumeration<String> names=request.getParameterNames();
		System.out.println("--start print request parameter--");
		for(;names.hasMoreElements();){
			String name=names.nextElement();
			System.out.println("k:"+name+",v:"+request.getParameter(name));
		}
		System.out.println("--end print request parameter--");
	}
}
