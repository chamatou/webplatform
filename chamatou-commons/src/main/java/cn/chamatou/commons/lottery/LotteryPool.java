package cn.chamatou.commons.lottery;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 奖池 
 *
 */
public class LotteryPool{
	//奖品
	private Map<LotterySection,Object> prizeMap;
	
	private int poistion;
	
	private Random random;
	
	private LuckFilter filter;
	public LotteryPool(){
		prizeMap=new HashMap<LotterySection, Object>();
		poistion=0;
		random=new Random(System.currentTimeMillis());
	}
	/**
	 * 添加奖品
	 * @param percentage 中奖机率百分比
	 * @param prize 奖品
	 */
	public void addLottery(int percentage,Object prize){
		LotterySection section=new LotterySection(poistion,poistion+percentage);
		poistion+=percentage;
		prizeMap.put(section, prize);
	}
	
	
	public void setFilter(LuckFilter filter) {
		this.filter = filter;
	}
	/**
	 * 执行抽奖,返回Null表示未中奖或被过滤
	 * @return
	 */
	public Object veryLuck(){
		int luckNumber=random.nextInt(poistion);
		Set<LotterySection> keys=prizeMap.keySet();
		for(LotterySection key:keys){
			if(key.contains(luckNumber)){
				Object prize=prizeMap.get(key);
				boolean isLuck=true;
				if(filter!=null){
					isLuck=filter.filter(key.getWinCount(),prize);
				}
				if(isLuck){
					key.addWinCount();
					return prize;
				}else{
					break;
				}
			}
		}
		return null;
	}
	/**
	 * 奖品过滤
	 *
	 */
	public interface LuckFilter{
		/**
		 * 如果发放改奖品,返回true
		 * @param winCount 改奖品出现次数
		 * @param obj
		 * @return
		 */
		boolean filter(long winCount,Object obj);
	}
}
