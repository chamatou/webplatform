package cn.chamatou.account.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.chamatou.commons.data.jpa.BaseEntity;

/**
 * 账户资金流水
 * 
 */
@Entity(name=CapitalFlow.TABLE_NAME)
public class CapitalFlow extends BaseEntity implements Serializable,Comparable<CapitalFlow>{
	public static final String TABLE_NAME="user_capital_flow";
	private static final long serialVersionUID = 2537260944329562596L;
	/**
	 * 支付状态
	 *
	 */
	public enum State{
		/**
		 * 等待
		 */
		WAIT{
			@Override
			public String toString() {
				return "等待支付";
			}
		},
		TO_BACK{
			@Override
			public String toString() {
				return "退回";
			}
		},
		/**
		 * 成功
		 */
		SUCCESS{
			@Override
			public String toString() {
				return "成功支付";
			}
		},
		/**
		 * 取消
		 */
		CANCEL{
			@Override
			public String toString() {
				return "取消支付";
			}
		}
	}
	/**
	 * 科目,用来干的什么
	 */
	public enum Course{
		/**
		 * 购物
		 */
		BUY_PRODUCT{
			@Override
			public String toString() {
				return "购物";
			}
		},
		/**
		 * 充值
		 */
		RECHARGE{
			@Override
			public String toString() {
				return "充值";
			}
		},
		/**
		 * 赠予
		 */
		GRANT{
			@Override
			public String toString() {
				return "赠予";
			}
		},
		COMMISSION{
			@Override
			public String toString() {
				return "佣金";
			}
		}
		
	}
	/**
	 * 类型
	 *
	 */
	public enum Type{
		/**
		 * 现金
		 */
		CASH{
			@Override
			public String toString() {
				return "现金";
			}
		},
		/**
		 * 可变现积分
		 */
		EXTC_SCORE{
			@Override
			public String toString() {
				return "可提";
			}
		},
		/**
		 * 不可变现积分
		 */
		UN_EXTC_SCORE{
			@Override
			public String toString() {
				return "不可提";
			}
		},
		/**
		 * 券类
		 */
		COUPON{
			@Override
			public String toString() {
				return "券类";
			}
		}
		
	}
	//流水号
	private String flowId; 
	//借贷标志,借减贷增
	private int flag;
	//数量
	private BigDecimal amount;
	//开始日期
	private Date startDate;
	//结束日期
	private Date endDate;
	//状态
	private State state;
	//科目
	private Course course;
	//本平台交易单号
	private String orderId;
	//外平台交易单号,可为空
	private String extOrderId;
	//来自用户
	private Account formAccount;
	//到何用户
	private Account toAccount;
	//所属用户
	private Account belongAccount;
	//类型
	private Type type;
	//备注
	private String remarks;
	@Id
	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Enumerated(EnumType.ORDINAL)
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	@Enumerated(EnumType.ORDINAL)
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getExtOrderId() {
		return extOrderId;
	}

	public void setExtOrderId(String extOrderId) {
		this.extOrderId = extOrderId;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name="formAccount")
	public Account getFormAccount() {
		return formAccount;
	}

	public void setFormAccount(Account formAccount) {
		this.formAccount = formAccount;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name="toAccount")
	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}
	@Enumerated(EnumType.ORDINAL)
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="belongAccount")
	public Account getBelongAccount() {
		return belongAccount;
	}

	public void setBelongAccount(Account belongAccount) {
		this.belongAccount = belongAccount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flowId == null) ? 0 : flowId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CapitalFlow other = (CapitalFlow) obj;
		if (flowId == null) {
			if (other.flowId != null)
				return false;
		} else if (!flowId.equals(other.flowId))
			return false;
		return true;
	}
	@Override
	public int compareTo(CapitalFlow o) {
		return this.flowId.compareTo(o.flowId);
	}
}
