package cn.chamatou.commons.data.jpa.query;
import java.io.Serializable;

/**
 * 数据库查询条件,封装where后面的语句
 *
 */
public interface  QueryCondition extends Serializable{
	/**
	 * 编译where查询字符串
	 * @return
	 */
	String compile();
	/**
	 * 获取查询用到的填充字符
	 * @return
	 */
	Object[] getValues();
	/**
	 * 获取别名
	 * @return
	 */
	String getAlias();
	/**
	 * 设置别名
	 */
	void setAlias(String alias);
	/**
	 * 比较-查询条件
	 *
	 */
	public enum Compare{
		/**
		 * 条件 =
		 */
		EQUAL{
			@Override
			public String toString(){
				return " = ";
			}
		},
		/**
		 * 条件>
		 */
		GT{
			@Override
			public String toString(){
				return " > ";
			}
		},
		/**
		 * 条件 <
		 */
		LS{
			@Override
			public String toString(){
				return " < ";
			}
		},
		/**
		 * 条件 >=
		 */
		GT_AND_EQUAL{
			@Override
			public String toString(){
				return " >= ";
			}
		},
		/**
		 * 条件 <=
		 */
		LS_AND_EQUAL{
			@Override
			public String toString(){
				return " <= ";
			}
		},
		/**
		 * 条件 <>
		 */
		NOT_EQUAL{
			@Override
			public String toString(){
				return " <> ";
			}
		}
	}
	/**
	 * in语句查询条件
	 * @author kaiyi
	 *
	 */
	public enum Contain {
		IN{
			@Override
			public String toString() {
				return " IN ";
			}
		},
		NOT_IN{
			@Override
			public String toString() {
				return " NOT IN ";
			}
		}
	}
	/**
	 * 连接条件
	 */
	public enum Link {
		/**
		 * and条件
		 */
		 AND{

			@Override
			public String toString() {
				return " AND ";
			}
			 
		 },
		 /**
		  *  or 条件
		  */
		 OR{
			@Override
			public String toString() {
					return " OR ";
			}
		 }
	}
	/**
	 * Null 查询
	 *
	 */
	public enum NullQuery{
		IS_NULL{
			@Override
			public String toString() {
				return " IS NULL ";
			}
		},
		IS_NOT_NULL{
			@Override
			public String toString() {
				return " IS NOT NULL ";
			}
		}
	}
}
