import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import cn.chamatou.commons.data.utils.CoderUtil;
import cn.chamatou.commons.log.Logger;
import cn.chamatou.commons.web.json.JsonUtils;


public class Test {
	@org.junit.Test
	public void t1(){
		Logger logger=Logger.getDefaultLogger(Test.class);
		logger.info("info");
	}
	
	@org.junit.Test
	public void testJson(){
		Map<String, Integer> maps=new HashMap<String, Integer>();
		maps.put("ab", 3);
		maps.put("de", 4);
		System.out.println(JsonUtils.simpleJson(maps));
		JSONObject jo=new JSONObject("{'ab':'3','de':'4'}");
		System.out.println(jo.get("ab"));
	}
	@org.junit.Test
	public void tj2(){
		String s="{'users':[{'name': 'Lisa'},{'name': 'Lisa'}]}";
		JSONObject jo=new JSONObject(s);
		System.out.println(jo.get("users"));
		System.out.println(CoderUtil.base64Encode("2'\""));
	}
}
