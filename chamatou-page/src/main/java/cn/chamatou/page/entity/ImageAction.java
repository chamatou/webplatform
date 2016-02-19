package cn.chamatou.page.entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
/**
 * 图片动作
 */
@Entity(name=ImageAction.TABLE)
public class ImageAction{
	public static final String TABLE="page_image_action";
	/**
	 * 调用本地浏览器打开页面
	 */
	public static final int ACTION_URL=0;
	/**
	 * 调用外部浏览器打开页面
	 */
	public static final int ACTION_BROWSER=1;
	/**
	 * 本地页面跳转
	 */
	public static final int ACTION_LOCAL=2;
	
	public static final int PLATFORM_ANDROID=0;
	
	public static final int PLATFORM_IOS=1;
	
	
	private ActionNames name;

	private int actionType;
	
	private boolean mutilePart;
	
	private int platform;
	
	private Set<PageImage> pageImages;
	
	private Set<ActionParameter> paramters;
	/**
	 * 图片名称
	 * @return
	 */
	@Id
	@Enumerated(EnumType.ORDINAL)
	public ActionNames getName() {
		return name;
	}
	/**
	 * 图片名称
	 * @param name
	 */
	public void setName(ActionNames name) {
		this.name = name;
	}
	/**
	 * 响应类型
	 */
	public int getActionType() {
		return actionType;
	}
	/**
	 * 响应类型
	 * @param actionType
	 */
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	/**
	 * 是否多图
	 * @return
	 */
	public boolean isMutilePart() {
		return mutilePart;
	}
	/**
	 * 是否多图
	 * @param mutilePart
	 */
	public void setMutilePart(boolean mutilePart) {
		this.mutilePart = mutilePart;
	}
	/**
	 * 平台名称
	 * @return
	 */
	public int getPlatform() {
		return platform;
	}
	/**
	 * 平台名称
	 * @return
	 */
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="imageAction")
	public Set<PageImage> getPageImages() {
		return pageImages;
	}
	public void setPageImages(Set<PageImage> pageImages) {
		this.pageImages = pageImages;
	}
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="imageAction")
	public Set<ActionParameter> getParamters() {
		return paramters;
	}
	public void setParamters(Set<ActionParameter> paramters) {
		this.paramters = paramters;
	}
	
	
}
