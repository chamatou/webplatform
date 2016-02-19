package cn.chamatou.commons.data.jpa.query;
/**
 * 查询连接条件 
 */
public class LinkCondition implements QueryCondition{
	private static final long serialVersionUID = -2652267449152416204L;

	private QueryCondition left;
	
	private QueryCondition right;
	
	private Link link;
	private boolean group;
	/**
	 * 添加连接条件
	 * @param left 左条件
	 * @param link 连接方式
	 * @param right 右条件
	 */
	public LinkCondition(QueryCondition left,Link link,QueryCondition right){
		this(left, link, right, false);
	}
	/**
	 * 
	 * 添加连接条件
	 * @param left 左条件
	 * @param link 连接方式
	 * @param right 右条件
	 * @param group 是否进行分组,例如 (a.name=? or b.name=?)
	 */
	public LinkCondition(QueryCondition left,Link link,QueryCondition right,Boolean group){
		this.left=left;
		this.right=right;
		this.right.setAlias(left.getAlias());
		this.group=group;
		this.link=link;
	}
	public String compile() {
		StringBuilder builder=new StringBuilder(" ");
		if(group){
			builder.append("(");
		}
		builder.append(left.compile());
		builder.append(link);
		builder.append(right.compile());
		if(group){
			builder.append(")");
		}
		return builder.toString();
	}
	
	@Override
	public Object[] getValues() {
		Object[] values=new Object[left.getValues().length+right.getValues().length];
		System.arraycopy(left.getValues(),0,values,0,left.getValues().length);
		System.arraycopy(right.getValues(),0,values,left.getValues().length,right.getValues().length);
		return values;
	}

	@Override
	public String getAlias() {
		return this.left.getAlias();
	}

	@Override
	public void setAlias(String alias) {
		this.right.setAlias(alias);
	}

}
