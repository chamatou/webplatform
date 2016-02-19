package cn.chamatou.commons.data.jpa;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
/**
 * 分页类,获取从第一条记录开始,当前属于第一页,每页需显示多少记录,总共有多少记录等信息
 *
 */
public final class LimitPage<T> implements Serializable{
	private static final long serialVersionUID = 5449387567883930692L;
	private int total;//总记录数
	private int maxResult;//每页显示多少记录
	private int currentPage;//当前页
	private int first;//开始记录	
	private int totalPage;//总共多少页
	private HashMap<String, String> params;//查询参数
	private String url;//查询URL
	private List<T> resultSet; //结果集
	private int start=0;//分页数据开始
	/**
	 * 添加单条记录
	 * @param t
	 */
	public void addSignleResult(T t){
		if(resultSet==null){
			resultSet=new ArrayList<T>();
		}
		resultSet.add(t);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 返回记录数
	 * @return
	 */
	public List<T> getResultSet() {
		return resultSet;
	}
	/**
	 * 设置记录
	 * @param resultList
	 */
	public void setResultSet(List<T> resultSet) {
		this.resultSet = resultSet;
	}
	/**
	 * 创建分页数据
	 * @param total 总记录数
	 * @param maxResult 每页最大显示记录数
	 */
	public LimitPage(int total,int maxResult){
		this(0, total, maxResult);
	}
	/**
	 * 创建分页数据
	 * @param start 开始记录位置
	 * @param total 总记录数
	 * @param maxResult 每页最大显示记录数
	 */
	public LimitPage(int startPosition,int total,int maxResult){
		params=new HashMap<String, String>();
		this.start=startPosition;
		this.total=total;
		this.maxResult=maxResult;
		first=this.start;
		if(total<=0||maxResult<=0)
		{
			totalPage=0;currentPage=0;
		}
		else
		{
			if(total%maxResult==0)
			{
				totalPage=total/maxResult;
			}
			else
			{
				totalPage=total/maxResult+1;			
			}
			currentPage=first/maxResult+1;
		}
	}
	/**
	 * 改变当前第一条记录,向前递增
	 * @param first
	 */
	public void setFirst(int f){
		while(this.getFirst()<f){
			this.next();
		}
	}
	/**
	 * 向前一页
	 * @return 向前翻页后的ScrollPage
	 */
	/*如果first-maxResult<total则表示是第一页,不做处理*/
	public void provious(){
		if((first-maxResult)>=this.start){
			first-=maxResult;
			currentPage--;
		}
	}
	/**
	 * 向后一页
	 * @return 向前翻页后的ScrollPage
	 */
	/*如果first+maxResult>total则表示是最后一页,不做处理*/
	public void next(){
		if((first+maxResult)<=total){
			first+=maxResult;
			currentPage++;
		}
	};
	/**
	 * 获取当前开始记录
	 * @return 
	 */
	public int getFirst(){
		return first;
	};
	/**
	 * 
	 * @return 返回当前记录总数
	 */
	public int getTotal(){
		return total;
	};
	/**
	 * 
	 * @return 获取每页应当显示记录数
	 */
	public int getMaxResult(){
		return maxResult;
	};
	/**
	 * 
	 * @return 返回当前处于第几页
	 */
	public int getCurrentPage(){
		return this.currentPage;
	};
	/**
	 * 获取总共多少页
	 * @return
	 */
	public int getTotalPage(){
		return totalPage;
	};
	
	/**
	 * 设置当前页  并计算起始查询值
	 * @param current 当前页
	 */
	public void setCurrentPage(int current){
		if(current>=1&&current<=totalPage){
			currentPage=current;
			first=(maxResult*currentPage)-(maxResult);
		}
	}
	/**
	 * 获取前一页的第一条记录开始数
	 * @return
	 */
	public int getProviousPage(){
		if(first<maxResult)return this.start;
		return first-maxResult;
	}
	/**
	 * 获取最后一页的第一条记录开始数
	 * @return
	 */
	public int getLastPage(){
		return maxResult*totalPage-maxResult;
	}
	/**
	 * 获取下一页的第一条记录开始数
	 * @return
	 */
	public int getNextPage(){
		if(first+maxResult>total)return first;
		return first+maxResult;
	}
	/**
	 * 获取第一页的第一条记录开始数
	 * @return
	 */
	public int getFirstPage(){
		return this.start;
	}


	public String getParameter() {
		StringBuilder builder=new StringBuilder();
		if(params.size()>0){
			builder.append("?");
			String encoding="utf-8";
			Set<String> keys=params.keySet();
			int index=0;
			for(String key:keys){
				try {
					builder.append(key).append("=").append(URLEncoder.encode(params.get(key), encoding));
					if(index<params.size()-1){
						builder.append("&");
					}
					++index;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return builder.toString();
	}

	public void putParameter(String name,String value) {
		this.params.put(name, value);
	}
}
