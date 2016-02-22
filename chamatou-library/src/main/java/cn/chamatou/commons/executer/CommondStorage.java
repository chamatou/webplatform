package cn.chamatou.commons.executer;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 命令存储器 
 *
 */
public interface CommondStorage {
	/**
	 * 当需要存储时调用,参数为所有命令
	 */
	void onSave(ConcurrentLinkedQueue<Commond> queue);
	/**
	 * 参数为执行完毕的命令,移除成功返回true
	 */
	boolean onRemove(Commond commond);
	/**
	 * 当需要恢复时调用,当ExecuterQueue在被首次初始化时调用,可以返回null
	 */
	ConcurrentLinkedQueue<Commond> onRecovery();
}
