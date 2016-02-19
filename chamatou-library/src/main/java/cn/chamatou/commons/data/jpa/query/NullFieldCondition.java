package cn.chamatou.commons.data.jpa.query;
/**
 * Null 条件查询
 *
 */
public class NullFieldCondition extends AbstractQueryCondition{
	private static final long serialVersionUID = -168583922880445818L;
	private NullQuery nullQuery;
	protected NullFieldCondition(String alias, String column) {
		super(alias, column);
	}
	public NullFieldCondition(String column,NullQuery nullQuery){
		super(column);
		this.nullQuery=nullQuery;
	}
	public NullFieldCondition(String column,String alias,NullQuery nullQuery){
		super(alias,column);
		this.nullQuery=nullQuery;
	}
	
	@Override
	public String compile() {
		StringBuilder builder=new StringBuilder();
		builder.append(alias).append(".").append(column).append(nullQuery.toString());
		return builder.toString();
	}

	@Override
	public Object[] getValues() {
		return new Object[]{};
	}
}
