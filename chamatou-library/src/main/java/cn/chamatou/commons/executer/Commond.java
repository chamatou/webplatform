package cn.chamatou.commons.executer;

import java.io.Serializable;
/**
 * 命令接口,execute方法中应当是一个非长时的阻塞线程,execute成功返回ture,
 * 失败返回false,失败的操作会在执行队列中再次调用
 *
 */
public interface Commond extends Serializable{
	/**
	 * 执行名称
	 */
	public boolean execute();
}
