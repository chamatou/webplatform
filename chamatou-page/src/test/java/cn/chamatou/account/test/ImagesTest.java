package cn.chamatou.account.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import cn.chamatou.commons.data.utils.CoderUtil;
import cn.chamatou.commons.web.servlet.HtmlConvertUtils;
import cn.chamatou.page.entity.PageImage;

public class ImagesTest {
	@Test
	public void seq(){
		List<PageImage> pil=new ArrayList<PageImage>();
		PageImage pi1=new PageImage();
		pi1.setSequenceNumber(2);
		pil.add(pi1);
		PageImage pi2=new PageImage();
		pi2.setSequenceNumber(1);
		pil.add(pi2);
		Collections.sort(pil);
		for(PageImage pi:pil){
			System.out.println(pi.getSequenceNumber());
		}
		String h="<a href='http://www.w3.org/TR/html4/sgml/entities.html'>";
		String e=CoderUtil.urlEncode(h);
		System.out.println(e);
		System.out.println(CoderUtil.urlDecode(e));
		System.out.println(HtmlConvertUtils.htmlEscape(h));
		System.out.println(HtmlConvertUtils.htmlUnescape("&lt;a href=&#39;http://www.w3.org/TR/html4/sgml/entities.html&#39;&gt;"));
	}
}
