package cn.chamatou.commons.web.json;
/**
 * JSON树节点构造,调用build方法产生树节点数据
 */
public interface JSONTree {
	/**
	 * 添加子树
	 * @param extjsTree
	 */
	JSONTree addChild(JSONTree extjsTree);
	/**
	 * 是否子节点
	 * @return
	 */
	boolean isLeaf();
	/**
	 * 构建Json数据
	 * @return
	 */
	String build();
	/**
	 * 获取父节点
	 * @return
	 */
	JSONTree getParent();
}