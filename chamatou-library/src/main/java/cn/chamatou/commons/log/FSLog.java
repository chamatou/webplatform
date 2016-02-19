package cn.chamatou.commons.log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


import cn.chamatou.commons.data.utils.FileSystemUtils;

/**
 * 日志记录器,记录服务器访问日志,常规信息日志,和异常日志,在指定的目录下每日一个文件 
 *
 */
public class FSLog {
	private File dir;
	private SimpleDateFormat dataFormat;
	private Date date;
	private String name;
	public FSLog(String name,File dir){
		this.dir=dir;
		date=new Date();
		dataFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		this.name=name;
	}
	/**
	 * 警告信息
	 * @param warn
	 */
	public void warn(String warn){
		BufferedWriter out=null;
		try {
			date.setTime(System.currentTimeMillis());
			String time=dataFormat.format(date);
			out=new BufferedWriter(new FileWriter(Paths.get(dir.getAbsolutePath(), name+".warn."+time.substring(0, 10)+".log").toFile(),true));
			out.write(time+" "+warn);
			out.newLine();
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			FileSystemUtils.close(out);
		}
	}
	
	/**
	 * 异常日志记录
	 * @param t
	 */
	public void throwable(Throwable t){
		BufferedWriter out=null;
		try {
			date.setTime(System.currentTimeMillis());
			String time=dataFormat.format(date);
			out=new BufferedWriter(new FileWriter(Paths.get(dir.getAbsolutePath(), name+".error."+time.substring(0, 10)+".log").toFile(),true));
			out.write(time);
			out.newLine();
			out.write(t.toString());
			StackTraceElement[] stacks=t.getStackTrace();
			for(StackTraceElement stack:stacks){
				out.write("\tat "+stack);
				out.newLine();
			}
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			FileSystemUtils.close(out);
		}
	}
	/**
	 * 记录普通访问日志
	 * @param info
	 */
	public void info(String info){
		BufferedWriter out=null;
		try {
			date.setTime(System.currentTimeMillis());
			String time=dataFormat.format(date);
			out=new BufferedWriter(new FileWriter(Paths.get(dir.getAbsolutePath(), name+".info."+time.substring(0, 10)+".log").toFile(),true));
			out.write(time+" "+info);
			out.newLine();
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			FileSystemUtils.close(out);
		}
	}
	/**
	 * 记录访问日志
	 */
	public void access(String remoteIp,int remotePort,int req){
		BufferedWriter out=null;
		try {
			date.setTime(System.currentTimeMillis());
			String time=dataFormat.format(date);
			out=new BufferedWriter(new FileWriter(Paths.get(dir.getAbsolutePath(), name+".access."+time.substring(0, 10)+".log").toFile(),true));
			out.write(time+" 请求内容 ip"+remoteIp+" port:"+remotePort+" 请求码:"+req);
			out.newLine();
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			FileSystemUtils.close(out);
		}
	}
}
