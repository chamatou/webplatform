package cn.chamatou.commons.data.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	
	public static final void close(final Closeable c){
		if(c!=null){
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static final void copyFile(InputStream in,OutputStream out) throws IOException{
		byte[] bytes=new byte[1024];
		int len=-1;
		while((len=in.read(bytes, 0, bytes.length))!=-1){
			out.write(bytes, 0, len);
		}
	}
}
