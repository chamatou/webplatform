package cn.chamatou.commons.data.jpa.query;
/**
 * in查询语句
 */
public class ContainCondition extends AbstractQueryCondition{
	private static final long serialVersionUID = -6793056091054180569L;
	private Object[] values;
	private Contain contain;
	public ContainCondition(String alias,String column,Object[] values,Contain contain) {
		super(alias, column);
		this.values=values;		
		this.contain=contain;
	}
	
	public ContainCondition(String column,Object[] values,Contain contain) {
		this("o",column,values,contain);
	}
	
	public ContainCondition(String alias,String column,Contain contain,String subTable,String subAlias,
			String subMatchColumn,QueryCondition subCondition){
		super(alias, column, subTable, subAlias, subMatchColumn, subCondition,null);
		this.contain=contain;
	}
	public ContainCondition(String column,Contain contain,String subTable,String subAlias,
			String subMatchColumn,QueryCondition subCondition){
		this("o", column, contain, subTable, subAlias, subMatchColumn,subCondition);
	}
	
	@Override
	public String compile() {
		StringBuilder builder=new StringBuilder(" ");
		if(isSubquery()){
			builder.append(alias).append(".").append(column).append(contain).append("(");
			builder.append("SELECT ").append(subAlias).append(".").append(subMatchColumn)
			.append(" FROM ").append(subTable).append(" as ").append(subAlias).append(" WHERE ")
			.append(subCondition.compile()).append(")");
		}else{
			builder.append(alias).append(".").append(column).append(contain).append("(");
			for(int index=0;index<values.length;index++){
				builder.append("?");
				if(index!=values.length-1){
					builder.append(",");
				}
			}
			builder.append(") ");
		}
		return builder.toString();
	}

	@Override
	public Object[] getValues() {
		return isSubquery()?this.subCondition.getValues():this.values;
	}
	
}
