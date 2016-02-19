package cn.chamatou.commons.httpclient;
import io.netty.handler.codec.http.HttpHeaders;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map.Entry;

/**
 * Http响应头 
 *
 */
public class ResponseHeaders {
	private HttpHeaders headers;
	
	ResponseHeaders(HttpHeaders headers){
		this.headers=headers;
	}
	/**
	 * 获取http相应头信息
	 * @param name
	 * @return
	 */
	public String getHeard(String name){
		return headers.get(name);
	}
	/**
	 * 获取http响应头列表
	 * @return
	 */
	public List<Entry<String, String>> entries(){
		return headers.entries();
	}
	
	public Charset getCharset(){
		String charset="utf-8";
		String contentType=headers.get(HttpHeaders.Names.CONTENT_TYPE);
		if(contentType!=null){
			String[] types=contentType.split(";");
			for(String type:types){
				String trim=type.trim();
				if(trim.startsWith("charset")||trim.startsWith("CHARSET")){
					String[] cs=trim.split("=");
					if(cs.length==2){
						charset=cs[1];
					}
				}
			}
		}
		return Charset.forName(charset);
	}
	/**
	 * 打印相应头
	 * @param headers
	 */
	public static final void print(ResponseHeaders headers){
		System.out.println("==========Begin Response headers==========");
		List<Entry<String, String>> entries=headers.entries();
		for(Entry<String, String> entry:entries){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		System.out.println("==========End Response headers==========");
	}
}
