package cn.chamatou.commons.httpclient;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;

import java.io.File;
import java.util.Map.Entry;
import java.util.Set;

import cn.chamatou.commons.data.utils.FileSystemUtils;


/**
 * 
 * POST表单方式文件上传
 *
 */
public class HttpFileUpload extends HttpPost{
	private File file;
	/**
	 * 提交文件名称
	 */
	private String fileKeyName;
	
	private HttpFileUpload(String url) {
		super(url);
	}
	public HttpFileUpload(String url,File file){
		this(url);
		this.file=file;
		fileKeyName=file.getName();
	}
	
	public String getFileKeyName() {
		return fileKeyName;
	}
	/**
	 * 设置文件上传的Key名称
	 * @param fileKeyName
	 */
	public void setFileKeyName(String fileKeyName) {
		this.fileKeyName = fileKeyName;
	}
	public HttpFileUpload(String url,File file,int aggregatorLength){
		this(url,file);
		this.setAggregatorLength(aggregatorLength);
	}
	@Override
	protected HttpRequest builderParams(HttpRequest request) {
		HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
		try {
			bodyRequestEncoder = new HttpPostRequestEncoder(factory,request,true);
			if(params!=null){
				Set<Entry<String, String>> entrys = params.entrySet();
				for (Entry<String, String> entry : entrys) {
					bodyRequestEncoder.addBodyAttribute(entry.getKey(),entry.getValue());
				}
			}
			bodyRequestEncoder.addBodyFileUpload(fileKeyName,file,FileSystemUtils.getMimeType(file), false); 
			return bodyRequestEncoder.finalizeRequest();
		} catch (ErrorDataEncoderException e) {
			e.printStackTrace();
		}
		return super.builderParams(request);
	}
}
