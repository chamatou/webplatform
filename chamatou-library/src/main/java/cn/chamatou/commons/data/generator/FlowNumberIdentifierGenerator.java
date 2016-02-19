package cn.chamatou.commons.data.generator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cn.chamatou.commons.data.IdentifierGenerat;

public class FlowNumberIdentifierGenerator implements IdentifierGenerat{
	
private int length;
	
	private int index;
	
	private Lock lock;
	
	private SimpleDateFormat sdf;
	
	private String oldDate;
	
	private boolean dateChange;
	
	
	public FlowNumberIdentifierGenerator(int length,int after){
		this(new SimpleDateFormat("yyyyMMdd"), length, after);
	}
	public FlowNumberIdentifierGenerator(SimpleDateFormat sdf,int length,int after){
		index=after;
		this.length=length;
		lock=new ReentrantLock();
		this.sdf=sdf;
		oldDate="";
		dateChange=false;
	}
	@Override
	public String nextIdentifier() {
		lock.lock();
		try{
			StringBuilder flow=new StringBuilder(getDate());
			if(dateChange){
				index=0;
			}
			++index;
			String num=String.valueOf(index);
			int size=length-num.length();
			if(size<0)throw new IndexOutOfBoundsException("length overflow.");
			for(int i=0;i<size;i++){
				flow.append("0");
			}
			flow.append(index);
			return flow.toString();
		}finally{
			lock.unlock();
		}
	}
	private String getDate(){
		String newDate = sdf.format(new Date());
		if(!newDate.equals(oldDate)){
			if(!oldDate.equals("")){
				dateChange=true;
			}
			oldDate=newDate;
		}
		return newDate;
	}
}	
