package cn.chamatou.commons.net.transf;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.chamatou.commons.net.netty.NettyDataWriter;

public class ResponseWarp implements Serializable{
	private static final long serialVersionUID = 4607127651811911662L;
	private ResponseWarp(){}
	private int code;
	private Map<String, String> header;
	private String content;
	private Table table;
	private String operNumber;
	private NettyDataWriter writer;
	
	public static final int SUCCESS=0;
	public static final int FAIL=1;
	
	
	public void setCode(int code) {
		this.code = code;
	}
	public NettyDataWriter getWriter() {
		return writer;
	}
	public void setWriter(NettyDataWriter writer) {
		this.writer = writer;
	}

	public static class Table{
		private static final int NO_COLUMN_NUMBER=-1;
		//行号
		private int rowNumber;
		//头名称
		private String[] columns;
		//值
		private String[][] values;
		//创建表格
		/**
		 * 
		 * @param rowNumber 表格行数
		 * @param columns 表格列名称
		 */
		public Table(int rowNumber,String[] columns){
			this.rowNumber=rowNumber;
			this.columns=columns;
			values=new String[rowNumber][columns.length];
			for(int row=0;row<rowNumber;row++){
				for(int column=0;column<columns.length;column++){
					values[row][column]="";//初始化表格
				}
			}
		}
		/**
		 * 获取所有表格数据
		 * @return
		 */
		public String[][] getValues(){
			return values;
		}
		/**
		 * 获取表格行数
		 * @return
		 */
		public int getRowNumber(){
			return rowNumber;
		}
		/**
		 * 获取表格列数
		 * @return
		 */
		public int getColumnNumber(){
			return columns.length;
		}
		/**
		 * 获取表格头名称
		 * @return
		 */
		public String[] getColumnHeader(){
			return this.columns;
		}
		/**
		 * 添加表格数据
		 * @param row 行号
		 * @param columnName 列名称
		 * @param value 值
		 */
		public void addValue(int row,String columnName,String value){
			int columnTag=getColumnTag(columnName);
			check(row, columnTag);
			values[row][columnTag]=value; 
		}
		public void addValue(int row,int columnNumber,String value){
			check(row, columnNumber);
			values[row][columnNumber]=value; 
		}
		/**
		 * 获取表格数据
		 * @param row 行号
		 * @param columnName 列名称
		 * @return
		 */
		public String getColumnValue(int row,String columnName){
			int columnTag=getColumnTag(columnName);
			check(row, columnTag);
			return values[row][columnTag];
		}
		/**
		 * 获取表格数据
		 * @param row 行号
		 * @param columnNumber 列号
		 * @return
		 */
		public String getColumnValue(int row,int columnNumber){
			check(row, columnNumber);
			return values[row][columnNumber];
		}
		private void check(int row,int columnTag){
			if(columnTag==NO_COLUMN_NUMBER){
				throw new IllegalArgumentException("Argument columnName is not found");
			}else if(row<0||row>rowNumber-1){
				throw new IllegalArgumentException("Argument row un match");
			}
		}
		private int getColumnTag(String columnName){
			for(int i=0;i<columns.length;i++){
				if(columns[i].equals(columnName)){
					return i;
				}
			}
			return NO_COLUMN_NUMBER;
		}
		
	}
	
	
	public void setTable(Table table) {
		this.table = table;
	}
	public static final ResponseBuilder createBuild(){
		return new ResponseBuilder();
	}
	
	public static class ResponseBuilder{
		private ResponseWarp resp;
		private ResponseBuilder(){
			resp=new ResponseWarp();
			resp.code=SUCCESS;
			resp.content="";
			resp.header=new HashMap<String, String>();
		}
		public void setCode(int code){
			resp.code=code;
		}
		
		public void addHeader(String name,String value){
			if(name!=null&&!name.equals("")&&value!=null&&!value.equals("")){
				resp.header.put(name, value);
			}
		}
		public void setOperNumber(String number){
			resp.operNumber=number;
		}
		public void setContent(String content){
			if(content!=null&&!content.equals("")){
				resp.content=content;
			}
		}
		public void setTable(Table table){
			if(table!=null){
				resp.table=table;
			}
		}
		public ResponseWarp build(){
			return resp;
		}
	}
	public int getCode() {
		return code;
	}
	public Map<String, String> getHeader() {
		return header;
	}
	public String getContent() {
		return content;
	}
	
	public Table getTable() {
		return table;
	}
	public boolean hasTable(){
		return table!=null;
	}
	
	public boolean hasContent(){
		return content!=null&&!content.equals("");
	}
	public boolean hasHeader(){
		return header!=null&&!header.isEmpty();
	}
	
	public static ResponseWarp paser(TransforProtocol.Response resp){
		ResponseBuilder builder=ResponseWarp.createBuild();
		builder.setCode(resp.getCode());
		builder.setOperNumber(resp.getOperNumber());
		builder.setContent(resp.getContent());
		if(resp.getHeadersCount()>0){
			List<TransforProtocol.Entry> entrys=resp.getHeadersList();
			for(TransforProtocol.Entry entry:entrys){
				builder.addHeader(entry.getName(), entry.getValue());
			}
		}
		if(resp.getColumnCount()>0){
			String[] columnHeader=new String[resp.getColumnHeaderCount()];
			for(int i=0;i<columnHeader.length;i++){
				columnHeader[i]=resp.getColumnHeader(i);
			}
			
			int columnCount=columnHeader.length;
			int row=resp.getColumnCount()/columnHeader.length;
			Table table=new Table(row, columnHeader);
			int index=0;
			for(int r=0;r<row;r++){
				for(int c=0;c<columnCount;c++){
					table.addValue(r, c, resp.getColumn(index++).getValue());
				}
			}
			builder.setTable(table);
		}
		/*if(resp.getColumnHeaderCount()>0){
			String[] columnHeader=new String[resp.getColumnHeaderCount()];
			for(int i=0;i<columnHeader.length;i++){
				columnHeader[i]=resp.getColumnHeader(i);
			}
			int columnCount=resp.getColumnCount();
			int row=columnCount/columnHeader.length;
			Response.Table table=new Response.Table(row, columnHeader);

		}*/
		return builder.build();
	}
	
	public String getOperNumber() {
		return operNumber;
	}
	public void setOperNumber(String operNumber) {
		this.operNumber = operNumber;
	}
	public TransforProtocol.Response toProtocol(){
		TransforProtocol.Response.Builder build=TransforProtocol.Response.newBuilder();
		build.setCode(code);
		build.setContent(content);
		build.setOperNumber(operNumber);
		if(hasHeader()){
			Set<String> keys=header.keySet();
			for(String key:keys){
				TransforProtocol.Entry.Builder entry=TransforProtocol.Entry.newBuilder();
				entry.setName(key);
				entry.setValue(header.get(key));
				build.addHeaders(entry.build());
			}
		}
		if(hasTable()){
			String[] columnHeaders=table.getColumnHeader();
			for(String ch:columnHeaders){
				build.addColumnHeader(ch);
			}
			String[][] values=table.values;
			for(int row=0;row<values.length;row++){
				for(int column=0;column<values[row].length;column++){
					TransforProtocol.Column.Builder cb=TransforProtocol.Column.newBuilder();
					cb.setRow(row);
					cb.setColumn(column);
					cb.setValue(values[row][column]);
					build.addColumn(cb.build());
				}
			}
		}
		return build.build();
	}
}
