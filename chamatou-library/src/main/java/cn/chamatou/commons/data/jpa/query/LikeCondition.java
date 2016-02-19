package cn.chamatou.commons.data.jpa.query;
/**
 * like查询条件
 *
 */
public abstract class LikeCondition implements QueryCondition{
	private static final long serialVersionUID = -3594611846448950651L;
	/**
	 * like 中 %出现位置 左边
	 */
	public static final int LIKE_POSTION_LEFT=0;
	/**
	 * like 中 %出现位置 右边
	 */
	public static final int LIKE_POSTION_RIGHT=1;
	/**
	 * like 中 %出现位置 两边边
	 */
	public static final int LIKE_POSTION_BOTH=2;
	
	protected String alias;
	protected String column;
	protected String wildCard;
	protected int likePosition;
	protected String value;
	protected boolean yesOrNo;
	/**
	 * 创建like查询条件
	 * @param alias
	 * @param column
	 * @param likePosition 通配符出现位置
	 * @param value 查询参数
	 */
	public LikeCondition(String alias,String column,boolean yesOrNo,int  likePosition,String value){
		this(alias,column,yesOrNo,likePosition,"%",value);
	}
	/**
	 * 创建like查询条件
	 * @param alias
	 * @param column
	 * @param likePosition 通配符出现位置
	 * @param wildCard 通配符
	 * @param value 查询参数
	 */
	public LikeCondition(String alias,String column,boolean yesOrNo,int likePosition,String wildCard,String value){
		this.alias=alias;
		this.column=column;
		this.likePosition=likePosition;
		this.wildCard=wildCard;
		this.value=value;
		this.yesOrNo=yesOrNo;
	}
	
	public Object[] getValues() {
		if(likePosition==LIKE_POSTION_LEFT){
			return new Object[]{wildCard+this.value};
		}else if(likePosition==LIKE_POSTION_RIGHT){
			return new Object[]{this.value+wildCard};
		}else{
			return new Object[]{wildCard+this.value+wildCard};
		}
	}
	@Override
	public String getAlias() {
		return this.alias;
	}
	@Override
	public void setAlias(String alias) {
		this.alias=alias;
	}
}