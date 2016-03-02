package cn.chamatou.account.test;

import org.junit.Test;

import cn.chamatou.commons.lottery.LotteryPool;

public class LotteryTest {
	@Test
	public void testLuck(){
		LotteryPool pool=new LotteryPool();
		pool.addLottery(5, "现金");
		pool.addLottery(50, "代金券5元");
		pool.addLottery(30, "代金券10元");
		pool.addLottery(15, "未中奖");
		int l1=0;
		int l2=0;
		int l3=0;
		int l4=0;
		for(int i=0;i<100;i++){
			String luck=pool.veryLuck().toString();
			if(luck.equals("现金")){
				l1++;
			}else if(luck.equals("代金券5元")){
				l2++;
			}else if(luck.equals("代金券10元")){
				l3++;
			}else if(luck.equals("未中奖")){
				l4++;
			}
			System.out.println(luck);
		}
		System.out.println("现金:"+l1+",代5："+l2+"，代10:"+l3+",未："+l4);
	}
}
