package cn.chamatou.commons.data.filesystem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
public class SegmentWrite {
	//当前已写入碎片文件序列号
	private static ConcurrentHashMap<String, Integer> currWriteNumber;
	//未写入磁盘碎片文件等待
	private static ConcurrentHashMap<String, FileSegment> waitWriterMapping;
	private static ConcurrentHashMap<String,FileOutputStream> outStreamMap;
	private static ConcurrentHashMap<String, Long> clearMap;//对象清理池
	private File file;
	static{
		currWriteNumber=new ConcurrentHashMap<String, Integer>();
		waitWriterMapping=new ConcurrentHashMap<String, FileSegment>();
		outStreamMap=new ConcurrentHashMap<String, FileOutputStream>();
		clearMap=new ConcurrentHashMap<String,Long>();
		//内存清理池,清除超过5分钟都尚未使用的fileId,已释放相应
		final long fiveMintue=1000*60*5;
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				//从clearMap中轮训出超过5分钟尚未执行的操作
			//System.out.println("准备执行SegmentWrite对象池清理");
				ArrayList<String> idList=new ArrayList<String>();
				Set<String> keys=clearMap.keySet();
				long now=System.currentTimeMillis();
				for(String key:keys){
					long old=clearMap.get(key);
					if(now-old>fiveMintue){
						idList.add(key);
					}
				}
			//System.out.println("获取到待清理的对象数:"+idList.size());
				for(String id:idList){
					currWriteNumber.remove(id);
					waitWriterMapping.remove(id);
					outStreamMap.remove(id);
					clearMap.remove(id);
				}
				idList.clear();
			//System.out.println("执行SegmentWrite对象池清理完毕");
			}
		},fiveMintue,fiveMintue);
	}
	/**
	 * 
	 * @param file 要写入的文件
	 */
	public SegmentWrite(File file){
		this.file=file;
	}
	/**
	 * 如果以完全写入,返回true
	 * @return
	 * @throws IOException 
	 */
	public boolean write(FileSegment segment){
		try{
			String fileId=segment.getFile().getId();
			Integer curr=currWriteNumber.get(segment.getFile().getId());
			int segmentNumber=segment.getCurrentSegment();
			byte[] bytes=segment.getBytes();
			if(curr==null){
				//如果尚未对当前碎片执行过任何写入
				outStreamMap.put(fileId,new FileOutputStream(file,true));
				curr=1;
				currWriteNumber.put(fileId,curr);
				clearMap.put(fileId, System.currentTimeMillis());
			}
			FileOutputStream out=outStreamMap.get(fileId);
			if(curr==segmentNumber){//如果当前写入序列号与碎片序列号相同,执行写入操作
				out.write(bytes);
				//out.flush();
				//写入完毕后将curr递增
				++curr;
				currWriteNumber.put(fileId, curr);
				//提取余下的片段
				while(waitWriterMapping.get(fileId+"_"+curr)!=null){
					FileSegment nextSegment=waitWriterMapping.get(fileId+"_"+curr);
					bytes=nextSegment.getBytes();
					out.write(bytes);
					//out.flush();
					++curr;
					currWriteNumber.put(fileId, curr);
				}
			}else{
				waitWriterMapping.put(fileId+"_"+segment.getCurrentSegment(),segment);
			}
			if(curr>segment.getTotalSegment()){
				//文件已全部写完,关闭输出流,返回true
				out.close();
				outStreamMap.remove(fileId);
				currWriteNumber.remove(fileId);
				clearMap.remove(fileId);
				return true;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
}
