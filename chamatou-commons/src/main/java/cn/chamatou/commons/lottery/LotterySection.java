package cn.chamatou.commons.lottery;
/**
 * 中奖区间 
 */
public class LotterySection {
	private int start;
	private int end;
	private long winCount;
	
	public LotterySection(int start, int end) {
		this.start = start;
		this.end = end;
		winCount=0;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	public boolean contains(int number){
		return number>=start&&number<=end;
	}
	public long getWinCount() {
		return winCount;
	}
	public synchronized void addWinCount(){
		winCount++;
	}
	
}
