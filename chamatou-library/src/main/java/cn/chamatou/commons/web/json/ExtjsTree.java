package cn.chamatou.commons.web.json;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * Extjs树对象
 * 示例：
 * 		ExtjsTree root=ExtjsTree.createRootNote(1);
		ExtjsTree c1=ExtjsTree.createChildNote(2, root);
		c1.addTreeAttribute(new ExtjsTreeType(ExtjsTreeType.TEXT, "节点1"));
		ExtjsTree c2=ExtjsTree.createChildNote(3, root);
		c2.addTreeAttribute(new ExtjsTreeType(ExtjsTreeType.TEXT, "节点2"));
		root.addChild(c1).addChild(c2);
		ExtjsTree c11=ExtjsTree.createChildNote(4,c1);
		c11.addTreeAttribute(new ExtjsTreeType(ExtjsTreeType.TEXT, "节点3"));
		
		ExtjsTree c12=ExtjsTree.createChildNote(6,c1);
		c12.addTreeAttribute(new ExtjsTreeType(ExtjsTreeType.TEXT, "节点4"));
		c1.addChild(c11).addChild(c12);
		
		TreeBuilder builder=new ExtjsTreeBuilder();
		System.out.println(builder.builder(root));
 */
public class ExtjsTree implements JSONTree{
	private ArrayList<ExtjsTree> child;
	private HashSet<ExtjsTreeType> typeSet;
	private ExtjsTree parent;
	private Object id;
	private ExtjsTree(Object id){
		this.child=new ArrayList<ExtjsTree>();
		typeSet=new HashSet<ExtjsTreeType>();
		this.id=id;
	}
	/**
	 * 获取根节点
	 * @return
	 */
	public static final ExtjsTree createRootNote(Object id){
		ExtjsTree note =new ExtjsTree(id);
		return note;
	}
	/**
	 * 获取子节点
	 * @param parent 父节点
	 * @param autoParent 是否自动设置父节点属性
	 * @return
	 */
	public static final ExtjsTree createChildNote(Object id,ExtjsTree parent,boolean autoParent){
		if(parent==null)throw new IllegalArgumentException("ParentNote is null.");
		ExtjsTree note=new ExtjsTree(id);
		note.setParent(parent);
		if(autoParent){
			
			note.addTreeAttribute(new ExtjsTreeType(ExtjsTreeType.PARENTID, 
					parent.id,parent.id instanceof Integer?false:true));
		}
		return note;
	}
	
	/**
	 * 获取子节点
	 * @param parent 父节点
	 * @return
	 */
	public static final ExtjsTree createChildNote(Object id,ExtjsTree parent){
		return createChildNote(id,parent,false);
	}
	/**
	 * 添加树属性
	 * @param types
	 */
	public void setTreeAttributes(ExtjsTreeType[] types){
		for(ExtjsTreeType type:types){
			typeSet.add(type);
		}
	}
	/**
	 * 添加树属性
	 * @param type
	 */
	public void addTreeAttribute(ExtjsTreeType type){
		typeSet.add(type);
	}
	/**
	 * 设置父组件
	 * @param note
	 */
	public void setParent(ExtjsTree note){
		this.parent=note;
	}
	/**
	 * 添加子树
	 * @param extjsTree
	 */
	@Override
	public JSONTree addChild(JSONTree note){
		if(note instanceof JSONTree){
			ExtjsTree et=(ExtjsTree)note;
			child.add(et);			
		}
		return this;
	}
	/**
	 * 是否子节点
	 * @return
	 */
	public boolean isLeaf(){
		return child.isEmpty();
	}
	
	private String build(String title,String titlevalue){
		StringBuilder builder=new StringBuilder();
		if(getParent()==null){
			builder.append("{'"+title+"':"+"'"+titlevalue+"'");
		}
		if(!typeSet.isEmpty()){
			builder.append("{");
			for(ExtjsTreeType type:typeSet){
				builder.append(type).append(",");
			}
			builder.deleteCharAt(builder.length()-1);
			builder.append("}");
		}
		if(!child.isEmpty()){
			boolean appendLast=false;
			if(builder.charAt(builder.length()-1)=='}'&&getParent()!=null){
				builder.deleteCharAt(builder.length()-1);
				appendLast=true;
			}
			builder.append(",");
			builder.append("'children':[");
			for(ExtjsTree c:child){
				builder.append(c.build()).append(",");
			}
			builder.deleteCharAt(builder.length()-1);
			builder.append("]");
			if(appendLast){
				builder.append("}");
			}
		}
		if(getParent()==null){
			builder.append("}");
		}
		return builder.toString();
	}
	
	@Override
	public String build(){
		return build("success","true");
	}
	public String buildTreeGrid(){
		return build("text",".");
	}
	@Override
	public JSONTree getParent() {
		return this.parent;
	}
	
}
