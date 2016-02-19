package cn.chamatou.commons.data.jpa.query;
import java.util.Random;
/**
 * 查询条件抽象类
 *
 */
public abstract class AbstractQueryCondition implements QueryCondition{
	private static final long serialVersionUID = 3872717298142011796L;
	protected String alias;
	protected String column;
	protected String subTable;
	protected String subAlias;
	protected String subMatchColumn;
	protected QueryCondition subCondition;
	private boolean subquery;
	protected String function;
	private AbstractQueryCondition(){
		subquery=false;
	}
	/**
	 * 构建一个默认查询,别名为o
	 * @param column 查询字段名
	 */
	protected AbstractQueryCondition(String column){
		this("o",column);
	}
	/**
	 * 构建一个默认查询
	 * @param alias 别名
	 * @param column 字段名 
	 */
	protected AbstractQueryCondition(String alias,String column){
		this();
		this.alias=alias;
		this.column=column;
	}
	/**
	 * 创建子查询
	 * @param alias 主查询字别名
	 * @param column 主查询字段
	 * @param subTable 子查询表名
	 * @param subAlias 子查询别名
	 * @param subMatchColumn 子查询返回匹配字段
	 * @param subCondition 子查询后条件
	 * @param function 子查询返回函数,可以为空
	 */
	protected AbstractQueryCondition(String alias,String column,String subTable,String subAlias,
			String subMatchColumn,QueryCondition subCondition,String function){
		this(alias, subMatchColumn);
		if(subAlias.equalsIgnoreCase(alias)){
			Random random=new Random();
			subAlias=alias+"_"+random.nextInt(10000);
		}
		this.subTable=subTable;
		this.subAlias=subAlias;
		this.subMatchColumn=subMatchColumn;
		this.subCondition=subCondition;
		this.subCondition.setAlias(subAlias);
		this.subquery=true;
		this.function=function;
	}
	/**
	 * 创建子查询
	 * @param column 主查询字段
	 * @param subTable 子查询表名
	 * @param subAlias 子查询别名
	 * @param subMatchColumn 子查询返回匹配字段
	 * @param subCondition 子查询后条件
	 * @param function 子查询返回函数,可以为空
	 */
	protected AbstractQueryCondition(String column,String subTable,String subAlias,
			String subMatchColumn,QueryCondition subCondition,String function){
		this("o", column, subTable, subAlias, subMatchColumn, subCondition,function);
	}
	@Override
	public void setAlias(String alias) {
		this.alias=alias;
	}
	@Override
	public String getAlias() {
		return this.alias;
	}
	/**
	 * 是否为子查询
	 * @return
	 */
	protected boolean isSubquery() {
		return subquery;
	}
	
}
