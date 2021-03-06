import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.chamatou.commons.json.FastJsonStringer;
import cn.chamatou.commons.json.TestJsonBean;
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
		String s="{'users':[{'name':'kaiyi1'},{'age':'33'}],'mer':[{'name':'kaiyi2'},{'age':'33'}]}";
		JSONObject jo=new JSONObject(s);
		JSONArray ja=(JSONArray) jo.get("mer");
		Iterator<Object> memberIter=ja.iterator();
		JSONObject matchObject=null;
		while(memberIter.hasNext()){
			matchObject=(JSONObject) memberIter.next();
			break;
		}
		System.out.println(matchObject.getString("name"));
	}
	@org.junit.Test
	public void sjt(){
		TestJsonBean  lp=new TestJsonBean();
		lp.setName("name_1");
		lp.setAge(33);
		lp.setDate(new Date());
		System.out.println(lp.getName());
		System.out.println(FastJsonStringer.object2String(lp, new String[]{"age","name"},null,null));
		//JSONObject jo=new JSONObject("{'testJsonBean':[{'name':'name_1','age':'33'}],'ab':[{'name':'name_1','age':'33'}]}");
		//System.out.println("abc".substring(0, "abc".length()-1));
		System.out.println(FastJsonStringer.linkMultiJsons("{'testJsonBean':[{'name':'name_1','age':'33'}]}","{'JS':[{'name':'name_1','age':'33'}]}"));
		JSONObject j=new JSONObject("{'testJsonBean':[{'name':'name_1','age':'33'}],'JS':[{'name':'name_1','age':'33'}]}");
	}
	@org.junit.Test
	public void paser(){
		JSONObject jo=new JSONObject("{'testJsonBean':[{'name':'awms','age':'33'}]}");
		Map<String,Object> mp=new HashMap<>();
		Iterator<String> keys=jo.keys();
		for(;keys.hasNext();){
			String key=keys.next();
			Object obj=jo.get(key);
			if(obj instanceof JSONArray){
				mp.put(key, parse((JSONArray) obj));
			}else{
				mp.put(key, obj);
			}
		}
		Set<String> kk=mp.keySet();
		for(String k:kk){
			System.out.println(k+":"+mp.get(k).getClass().getName());
		}
	}
	public Map<String, Object> parse(JSONArray jr){
		Map<String, Object> map=new HashMap<>();
		Iterator<Object> it=jr.iterator();
		for(;it.hasNext();){
			JSONObject jo=(JSONObject) it.next();
			Iterator<String> keys=jo.keys();
			for(;keys.hasNext();){
				String key=keys.next();
				Object obj=jo.get(key);
				if(obj instanceof JSONArray){
					map.put(key, parse((JSONArray) obj));
				}else{
					map.put(key, obj);
				}
			}
		}
		return map;
	}
}
