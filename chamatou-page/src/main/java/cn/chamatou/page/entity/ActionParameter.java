package cn.chamatou.page.entity;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

import cn.chamatou.commons.data.jpa.RandomIdentifierEntity;

/**
 * 动作图片传递参数 
 */
@Entity(name=ActionParameter.TABLE_NAME)
public class ActionParameter extends RandomIdentifierEntity{
	public static final String TABLE_NAME="page_action_parameter";
	private static final long serialVersionUID = -5468517081715652975L;
	
	private String perfix;
	
	private Map<String, String> values;
	
	private ImageAction imageAction;
	/**
	 * 传递参数前缀类型
	 * @return
	 */
	public String getPerfix() {
		return perfix;
	}

	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}
	@ElementCollection(fetch = FetchType.EAGER)  
	@CollectionTable(name = "page_action_param_values")
	@MapKeyColumn(name = "param_key")
	@JoinColumn(name="action_parameter_id")
    @Column(name = "param_value")
	public Map<String, String> getValues() {
		return values;
	}
	public void setValues(Map<String, String> values) {
		this.values = values;
	}
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="imageAction")
	public ImageAction getImageAction() {
		return imageAction;
	}

	public void setImageAction(ImageAction imageAction) {
		this.imageAction = imageAction;
	}
	
}
