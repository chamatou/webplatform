package cn.chamatou.commons.data.generator;

import java.util.Random;
import cn.chamatou.commons.data.IdentifierGenerat;

public class RandomIdentifierGenerator implements IdentifierGenerat{
	private Random r;
	public RandomIdentifierGenerator(){
		r=new Random(System.currentTimeMillis());
	}
	@Override
	public String nextIdentifier() {
		int n1=r.nextInt(99);
		int n2=r.nextInt(99);
		String pre=n1<10?"0"+String.valueOf(n1):String.valueOf(n1);
		String suffix=n2<10?"0"+String.valueOf(n2):String.valueOf(n2);
		return pre+String.valueOf(System.nanoTime())+suffix;
	}

}
