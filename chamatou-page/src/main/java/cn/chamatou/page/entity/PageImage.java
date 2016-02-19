package cn.chamatou.page.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import cn.chamatou.commons.data.jpa.RandomIdentifierEntity;
/**
 * 页图片
 *
 */
@Entity(name=PageImage.TABLE_NAME)
public class PageImage extends RandomIdentifierEntity implements Comparable<PageImage>{
	public static final String TABLE_NAME="page_image";
	private static final long serialVersionUID = 830824020111906045L;
	
	private byte[] encode;
	
	private String prefix;
	
	private String url;
	
	private ImageAction imageAction;
	
	private int sequenceNumber;
	
	@Lob
	@Basic(fetch=FetchType.EAGER)
	public byte[] getEncode() {
		return encode;
	}

	public void setEncode(byte[] encode) {
		this.encode = encode;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="imageAction")
	public ImageAction getImageAction() {
		return imageAction;
	}

	public void setImageAction(ImageAction imageAction) {
		this.imageAction = imageAction;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	//升序排列
	@Override
	public int compareTo(PageImage o) {
		return this.sequenceNumber==o.sequenceNumber?0:(this.sequenceNumber>o.sequenceNumber?1:-1);
	}
}
