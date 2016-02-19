package cn.chamatou.commons.data.jpa.query;
public class MatchCondition extends AbstractQueryCondition{
	private static final long serialVersionUID = -3877893628242772686L;
	private Compare compare;
	private Object value;
	/**
	 * 创建条件匹配查询
	 * @param alias 别名
	 * @param column 字段
	 * @param compare 匹配方式
	 * @param value 匹配值
	 */
	public MatchCondition(String alias,String column,Compare compare,Object value) {
		super(alias,column);
		this.compare=compare;
		this.value=value;
	}
	/**
	 * 创建条件匹配查询
	 * @param column 字段
	 * @param compare 匹配方式
	 * @param value 匹配值
	 */
	public MatchCondition(String column,Compare compare,Object value) {
		super(column);
		this.compare=compare;
		this.value=value;
	}
	/**
	 * 创建匹配子查询
	 * @param column 主查询字段
	 * @param compare 主查询匹配方式
	 * @param subTable 子查询表名
	 * @param subAlias 子查询别名
	 * @param subMatchColumn 子查询返回匹配字段
	 * @param subCondition 子查询后条件
	 * @param function 子查询返回函数,可以为空
	 */
	public MatchCondition(String column,Compare compare,String subTable,String subAlias,
			String subMatchColumn,QueryCondition subCondition,String function){
		super(column, subTable, subAlias, subMatchColumn, subCondition,function);
		this.compare=compare;
	}
	/**
	 * 创建匹配子查询
	 * @param alias 主查询别名
	 * @param column 主查询字段
	 * @param compare 主查询匹配方式
	 * @param subTable 子查询表名
	 * @param subAlias 子查询别名
	 * @param subMatchColumn 子查询返回匹配字段
	 * @param subCondition 子查询后条件
	 * @param function 子查询返回函数,可以为空
	 */
	public MatchCondition(String alias,String column,Compare compare,String subTable,String subAlias,
			String subMatchColumn,QueryCondition subCondition,String function){
		super(alias, column, subTable, subAlias, subMatchColumn, subCondition,function);
	}
	@Override
	public String compile() {
		StringBuilder builder=new StringBuilder(" ");
		if(isSubquery()){
			builder.append(alias).append(".").append(column).append(compare)
			.append("(SELECT ");
			if(this.function!=null){
				builder.append(function).append("(").append(subAlias).append(".").append(subMatchColumn).append(")");
			}else{
				builder.append(subAlias).append(".").append(subMatchColumn);
			}
			builder.append(" FROM ").append(subTable).append(" as ").append(subAlias).append(" WHERE ").append(subCondition.compile()).append(")");
		}else{
			builder.append(alias).append(".").append(column).append(compare).append("?");			
		}
		builder.append(" ");
		return builder.toString();
	}

	@Override
	public Object[] getValues() {
		return isSubquery()?this.subCondition.getValues():new Object[]{value};
	}
}
