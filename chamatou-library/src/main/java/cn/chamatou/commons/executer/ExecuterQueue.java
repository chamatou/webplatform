package cn.chamatou.commons.executer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 远程命令执行器 ,使用该类,需要指定存储器，以免数据丢失.
 * 此方法必须且只能被调用一次
 *
 */
public class ExecuterQueue {
	private ConcurrentLinkedQueue<Commond> queue;
	private CommondStorage storage;
	private ExecutorService exec;
	//移除操作双保险机制
	private ConcurrentLinkedQueue<Commond> unremove;
	private ExecuterQueue(){
		init=false;
	}
	private boolean init;
	/**
	 * 设置存储器
	 * @param storage
	 */
	public void setStorage(CommondStorage storage) {
		if(!init){
			//lock=new ReentrantLock();
			this.storage = storage;
			queue=storage.onRecovery();
			if(queue==null){
				queue=new ConcurrentLinkedQueue<Commond>();
			}
			unremove=new ConcurrentLinkedQueue<Commond>();
			//已处理器大小来的2倍决定线程池大小
			exec=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
		}
		init=true;
	}
	

	
	public int getQueueSize(){
		return queue.size();
	}
	
	private void tryExecute(){
		while(!unremove.isEmpty()){
			ExecuterQueue.this.storage.onRemove(unremove.poll());
		}
		while(!queue.isEmpty()){
			final Commond c=queue.poll();
			if(c!=null){
				exec.execute(new Runnable() {
					@Override
					public void run() {
						if(c.execute()){
							if(!ExecuterQueue.this.storage.onRemove(c)){
								unremove.offer(c);
							}
						}else{
							//执行失败,继续放入队列等待下一次执行
							queue.offer(c);
						}
					}
				});
			}
		}
	}
	public void putCommond(Commond commond){
		try{
			//lock.lock();
			queue.offer(commond);
			storage.onSave(queue);
		}finally{
			//lock.unlock();
			tryExecute();
		}
	}
}
