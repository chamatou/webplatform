package cn.chamatou.commons.data.filesystem;
import java.io.File;
import java.nio.file.Path;
/**
 * 文件描述 
 *
 */
public class FileDescriptor {
	/** 文件访问唯一标示 */
	private String id;
	/** 文件MimeType */
	private String mimeType;
	/** 文件后缀名 */
	private String suffix;
	/**文件路径描述符*/
	private Path path;
	/**文件路径描述符*/
	private File file;
	/**文件长度*/
	private Long length;
	//远程地址
	private String remoteAddr;
	//远程端口
	private Integer remotePort;
	//http访问端口
	private Integer httpAccessPort;
	
	public Integer getHttpAccessPort() {
		return httpAccessPort;
	}

	public void setHttpAccessPort(Integer httpAccessPort) {
		this.httpAccessPort = httpAccessPort;
	}

	public FileDescriptor(){
		this("",0l,"","",null,null,"",-1,-1);
	}
	
	public FileDescriptor(String id,Long length, String mimeType, String suffix, Path path,
			File file, String remoteAddr, Integer remotePort,Integer httpAccessPort) {
		this.id = id;
		this.length=length;
		this.mimeType = mimeType;
		this.suffix = suffix;
		this.path = path;
		this.file = file;
		this.remoteAddr = remoteAddr;
		this.remotePort = remotePort;
		this.httpAccessPort=httpAccessPort;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public Integer getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(Integer remotePort) {
		this.remotePort = remotePort;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}
	
}
