package cn.chamatou.commons.web.servlet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Http方式文件下载,支持断点续传
 * @Package:net.sourceforge.common.web
 * @Title:FileDownload.java
 * @version:1.0
 */
public class HttpFileDownload {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private File downloadFile;
	private String showName;
	private String suffix;
	public HttpFileDownload(HttpServletRequest request,
			HttpServletResponse response, File downloadFile, String showName,String suffix) throws FileNotFoundException{
		if(!downloadFile.exists())throw new FileNotFoundException();
		this.request=request;
		this.response=response;
		this.downloadFile=downloadFile;
		this.showName=showName;
		this.suffix=suffix;
		
	}
	/**
	 * 开始文件下载
	 * @throws IOException
	 */
	public void downLoad()throws IOException{
		RandomAccessFile raf=new RandomAccessFile(downloadFile, "r");
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0)
			showName = new String(showName.getBytes("UTF-8"), "ISO8859-1");
		else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			showName = URLEncoder.encode(showName, "UTF-8");
		}
		response.reset();
		response.addHeader("Content-Disposition", "attachment;filename="
				+ showName + "." + suffix);
		response.addHeader("Content-Length", String.valueOf(raf.length()));
		response.setContentType("application/octet-stream;charset=utf-8");
		String range=request.getHeader("range");
		long start=0;
		if(range!=null){
			range=range.replaceAll("bytes=", "");
			String[] lengths=range.split("-");
			start=Long.valueOf(lengths[0]);
		}
		raf.seek(start);
		byte[] bits=new byte[1024];
		int len = -1;
		OutputStream out=response.getOutputStream();
		while ((len = raf.read(bits)) != -1) {
			out.write(bits, 0, len);
		}
		out.flush();
		out.close();
		raf.close();
	}
}
