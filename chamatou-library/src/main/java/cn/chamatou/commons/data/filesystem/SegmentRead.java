package cn.chamatou.commons.data.filesystem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;

import cn.chamatou.commons.data.utils.FileSystemUtils;

/**
 * 碎片文件读取器
 *
 */
public class SegmentRead {
	private BufferedInputStream in;
	private int maxSegment;
	private int currSegment;
	private int splitSize;
	private long fileLength;
	private FileDescriptor fd;
	/**
	 * 
	 * @param file
	 * @param splitSize
	 * @throws FileNotFoundException
	 */
	public SegmentRead(File file,int splitSize) throws FileNotFoundException{
		this.in=new BufferedInputStream(new FileInputStream(file));
		fd=new FileDescriptor();
		fd.setFile(file);
		fd.setId(FileSystemUtils.generateFileId(file));
		fd.setMimeType(FileSystemUtils.getMimeType(file));
		fd.setSuffix(FileSystemUtils.getSuffix(file));
		fd.setPath(FileSystems.getDefault().getPath(file.getAbsolutePath()));
		this.fileLength=file.length();
		int total=(int) (this.fileLength/splitSize);
		this.splitSize=splitSize;
		if(this.fileLength%splitSize!=0){
			++total;
		}
		this.maxSegment=total;
		this.currSegment=1;
	}
	/**
	 * 对碎片数组进行填充,每次返回一个填充好的FileSegment,如果已经填充完毕返回null
	 * @return
	 * @throws IOException 
	 */
	public FileSegment read() throws IOException{
		if(currSegment>maxSegment){
			close();
			return null;
		}
		int outSize=(int) (currSegment<maxSegment?splitSize:this.fileLength%splitSize);
		//ByteArrayOutputStream out=new ByteArrayOutputStream(outSize);
		byte[] bits=new byte[outSize];
		int read=-1;
		int off=0;
		int len=outSize;
		while((read=in.read(bits, off, len))!=len){
			off+=read;
			len-=read;
		}
		FileSegment fs=new FileSegment();
		fs.setBytes(bits);
	//System.out.println("read bytes:"+bits.length+":curr:"+currSegment);
		fs.setCurrentLength(outSize);
		fs.setCurrentSegment(currSegment);
		fs.setFileLength(this.fileLength);
		fs.setSplitLength(splitSize);
		fs.setTotalSegment(maxSegment);
		fs.setFile(fd);
		++currSegment;
		return fs;
	}
	public void close(){
		FileSystemUtils.close(in);
	}
}
