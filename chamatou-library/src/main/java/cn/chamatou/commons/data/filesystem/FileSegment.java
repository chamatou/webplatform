package cn.chamatou.commons.data.filesystem;
/**
 * 文件碎片
 * 用法：
 * 读取文件并分片
 * SegmentRead read=new SegmentRead(file, splitSize);
 * FileSegment segment=null;
 * while((segment=read.read())!=null){
 * 		//处理读取到的碎片文件
 * }
 * 将分片文件写入
 * SegmentWrite writer=new SegmentWrite(saveFile);
 * writer.write(segment);
 */
public class FileSegment {
	//分片参考长度
	private int splitLength;
	//文件长度
	private long fileLength;
	//总片段数
	private int totalSegment;
	//当前片段
	private int currentSegment;
	//文件描述符
	private FileDescriptor file;
	//当前分片长度
	private long currentLength;
	//文件碎片字节
	private byte[] bytes;
	
	public FileSegment(){
		
	}
	/**
	 * 构建文件碎片
	 * @param splitLength 分片长度
	 * @param fileLength 文件长度
	 * @param totalSegment 
	 * @param currentSegment
	 * @param file
	 * @param currentLength
	 * @param bytes
	
	public FileSegment(int splitLength, long fileLength, int totalSegment,
			int currentSegment, FileDescriptor file, long currentLength,
			byte[] bytes) {
		this.splitLength = splitLength;
		this.fileLength = fileLength;
		this.totalSegment = totalSegment;
		this.currentSegment = currentSegment;
		this.file = file;
		this.currentLength = currentLength;
		this.bytes = bytes;
	} */
	public int getSplitLength() {
		return splitLength;
	}
	public void setSplitLength(int splitLength) {
		this.splitLength = splitLength;
	}
	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	public int getTotalSegment() {
		return totalSegment;
	}
	public void setTotalSegment(int totalSegment) {
		this.totalSegment = totalSegment;
	}
	public int getCurrentSegment() {
		return currentSegment;
	}
	public void setCurrentSegment(int currentSegment) {
		this.currentSegment = currentSegment;
	}
	public FileDescriptor getFile() {
		return file;
	}
	public void setFile(FileDescriptor file) {
		this.file = file;
	}
	public long getCurrentLength() {
		return currentLength;
	}
	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
