package cn.chamatou.account.test;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.Test;

import cn.chamatou.commons.data.utils.CoderUtil;
import cn.chamatou.commons.data.utils.StringUtils;
import cn.chamatou.commons.web.json.JQueryData;
import cn.chamatou.commons.web.json.JsonUtils;
import cn.chamatou.page.entity.PageImage;

public class JsonTest {
	@Test
	public void testJson1(){
		PageImage image1=new PageImage();
		image1.setUrl("myurl");
		image1.setId("myid");
		PageImage image2=new PageImage();
		image2.setUrl("myurl");
		image2.setId("myid");
		ArrayList<PageImage> pi=new ArrayList<>();
		pi.add(image2);
		pi.add(image1);
		JQueryData jd=new JQueryData(PageImage.class, pi,new String[]{"id","url"});
		jd.setNeedBase64Value(true);
		jd.setPrefix("image");
		System.out.println(StringUtils.singleQuotationMark(jd.build()));
		JSONObject jo=new JSONObject("{'image':[{'id':'myid','url':'myurl'},{'id':'myid','url':'myurl'}]}");
		System.out.println(jo.get("image"));
		System.out.println(CoderUtil.base64Encode("332211"));
		System.out.println("332211");
	}
	@Test
	public void testJson2(){
		String json="{'pageImage':[{'id':'bXlpZA==','url':'bXl1cmw='}]}";
		PageImage image=JsonUtils.parseToClass(PageImage.class, json, false, true,null);
		System.out.println(image.getUrl()+":"+image.getId());
	}
}
