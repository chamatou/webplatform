package cn.chamatou.commons.log;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日志类
*/
public abstract class Logger {
	private String className;
	private SimpleDateFormat dataFormat;
	private Date date;
	private int DEBUG=1;
	private int INFO=2;
	private int WARN=3;
	private int ERROR=4;
	private int CURR_LEVEL=DEBUG;
	private Logger(Class<?> clz){
		date=new Date();
		dataFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		className=clz.getName();
	}
	/**
	 * 返回日志的写入writer
	 * @return
	 */
	protected abstract Writer newWriter();
	/**
	 * 关闭日志写入writer
	 * @param writer
	 */
	protected abstract void closeWriter(Writer writer);
	
	public static final Logger getDefaultLogger(Class<?> clz){
		return new Logger(clz) {
			
			@Override
			protected Writer newWriter() {
				OutputStreamWriter writer=new OutputStreamWriter(System.out);
				return writer;
			}
			
			@Override
			protected void closeWriter(Writer writer) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	
	public static final Logger gettLogger(Class<?> clz){
		return null;
	}
	
	private void writeMessage(String level,String msg){
		Writer writer=newWriter();
		BufferedWriter bw=new BufferedWriter(writer);
		try {
			date.setTime(System.currentTimeMillis());
			StackTraceElement ste = new Throwable().getStackTrace()[2];
			bw.write("["+level+"]"+" "+dataFormat.format(date)+" method:"+className+"."+ste.getMethodName()+"("+ste.getFileName()+":"+ste.getLineNumber()+") "+msg);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeWriter(bw);
	}
	public void exception(Throwable t){
		BufferedWriter out=null;
		try {
			date.setTime(System.currentTimeMillis());
			String time=dataFormat.format(date);
			out=new BufferedWriter(newWriter());
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
			closeWriter(out);
		}
	}
	public  void info(String msg){
		if(CURR_LEVEL<=INFO){
			writeMessage("info",msg);
		}
	}
	public void debug(String msg){
		if(CURR_LEVEL<=DEBUG){
			writeMessage("debug",msg);
		}
	}
	public void warn(String msg){
		if(CURR_LEVEL<=WARN){
			writeMessage("warn",msg);			
		}
	}
	public void error(String msg){
		if(CURR_LEVEL<=ERROR){
			writeMessage("error",msg);
		}
	}
}
