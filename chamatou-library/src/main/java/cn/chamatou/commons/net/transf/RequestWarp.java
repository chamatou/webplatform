package cn.chamatou.commons.net.transf;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
/**
 * 请求
 *
 */
public class RequestWarp implements Serializable{
	private static final long serialVersionUID = 3279113051309456896L;
	private RequestWarp(){}
	
	public static class RequestBuilder{
		private RequestWarp request;
		private RequestBuilder(String code){
			request=new RequestWarp();
			request.code=code;
			request.params=new HashMap<String, String>();
			request.content="";
			request.header=new HashMap<String,String>();
			request.operNumber=UUID.randomUUID().toString();
		}
		
		public void addParameter(String name,String value){
			if(name!=null&&!name.equals("")&&value!=null&&!value.equals(""))
				request.params.put(name, value);
		}
		public void addHeader(String name,String value){
			if(name!=null&&!name.equals("")&&value!=null&&!value.equals(""))
				request.header.put(name, value);
		}
		public void setContent(String content){
			request.content=content;
		}
		public void setOperNumber(String number){
			request.operNumber=number;
		}
		public RequestWarp build(){
			return request;
		}
	}
	
	public static final RequestBuilder createBuild(String code){
		return new RequestBuilder(code);
	}
	
	/**
	 * 请求码
	 */
	private String code;
	/**
	 * 请求参数
	 */
	private Map<String, String> header;
	/**
	 * 请求参数
	 */
	private Map<String, String> params;
	/**
	 * 请求内容
	 */
	private String content;
	/**
	 * 操作号
	 */
	private String operNumber;
	
	public String getOperNumber() {
		return operNumber;
	}
	public void setOperNumber(String operNumber) {
		this.operNumber = operNumber;
	}
	public boolean hasHeader(){
		return header!=null&&!header.isEmpty();
	}
	/**
	 * 是否含有请求参数
	 * @return
	 */
	public boolean hasParameter(){
		return params!=null&&!params.isEmpty();
	}
	/**
	 * 是否含有内容
	 * @return
	 */
	public boolean hasContent(){
		return content!=null&&!content.isEmpty();
	}
	/**
	 * 获取请求码
	 * @return
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 获取请求参数
	 * @return
	 */
	public Map<String, String> getParams() {
		return params;
	}
	/**
	 * 获取请求内容
	 * @return
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 获取请求头
	 * @return
	 */
	public Map<String, String> getHeader() {
		return header;
	}
	/**
	 * 将协议转换为请求
	 * @param tr
	 * @return
	 */
	public static final RequestWarp paser(TransforProtocol.Request tr){
		RequestBuilder builder=RequestWarp.createBuild(tr.getCode());
		builder.setContent(tr.getContent());
		builder.setOperNumber(tr.getOperNumber());
		if(tr.getHeadersCount()>0){
			List<TransforProtocol.Entry> entrys=tr.getHeadersList();
			for(TransforProtocol.Entry entry:entrys){
				builder.addHeader(entry.getName(), entry.getValue());
			}
		}
		if(tr.getParamsCount()>0){
			List<TransforProtocol.Entry> entrys=tr.getParamsList();
			for(TransforProtocol.Entry entry:entrys){
				builder.addParameter(entry.getName(), entry.getValue());
			}
		}
		return builder.build();
	}
	/**
	 * 转换请求为传输协议
	 * @return
	 */
	public  TransforProtocol.Request toProtocol(){
		TransforProtocol.Request.Builder builder=TransforProtocol.Request.newBuilder();
		builder.setCode(code);
		builder.setContent(content);
		builder.setOperNumber(operNumber);
		if(hasHeader()){
			Set<String> keys=header.keySet();
			for(String key:keys){
				TransforProtocol.Entry.Builder entry=TransforProtocol.Entry.newBuilder();
				entry.setName(key);
				entry.setValue(header.get(key));
				builder.addHeaders(entry.build());
			}
		}
		if(hasParameter()){
			Set<String> keys=params.keySet();
			for(String key:keys){
				TransforProtocol.Entry.Builder entry=TransforProtocol.Entry.newBuilder();
				entry.setName(key);
				entry.setValue(params.get(key));
				builder.addParams(entry.build());
			}
		}
		return builder.build();
	}
}
