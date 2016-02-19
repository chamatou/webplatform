import java.io.PrintWriter;

import cn.chamatou.commons.log.Logger;


public class Test {
	@org.junit.Test
	public void t1(){
		Logger logger=Logger.getDefaultLogger(Test.class);
		logger.info("info");
	}
	
}
