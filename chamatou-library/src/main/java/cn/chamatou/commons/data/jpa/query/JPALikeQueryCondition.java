package cn.chamatou.commons.data.jpa.query;
/**
 * JPA方式的Like查询
 *
 */
public class JPALikeQueryCondition extends LikeCondition{
	private static final long serialVersionUID = 8620812507352291666L;
	public JPALikeQueryCondition(String alias, String column, boolean yesOrNo,
			int likePosition, String value) {
		super(alias, column, yesOrNo, likePosition, value);
	}
	public JPALikeQueryCondition(String alias,String column,boolean yesOrNo,int likePosition,String wildCard,String value){
		super(alias, column, yesOrNo, likePosition, wildCard, value);
	}
	public String compile() {
		StringBuilder builder=new StringBuilder(" ");
		builder.append(alias).append(".").append(column);
		if(yesOrNo){
			builder.append(" LIKE ? ");
		}else{
			builder.append(" NOT LIKE ?");
		}
		return builder.toString();
	}
}