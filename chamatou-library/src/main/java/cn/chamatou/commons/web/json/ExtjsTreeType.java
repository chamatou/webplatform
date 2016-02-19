package cn.chamatou.commons.web.json;
/**
 * extjs树对象类型
 *  {name: 'parentId',   type: idType,    defaultValue: null},
	{name: 'index',      type: 'int',     defaultValue: null, persist: false},
	{name: 'depth',      type: 'int',     defaultValue: 0, persist: false},
	{name: 'expanded',   type: 'bool',    defaultValue: false, persist: false},
	{name: 'expandable', type: 'bool',    defaultValue: true, persist: false},
	{name: 'checked',    type: 'auto',    defaultValue: null, persist: false},
	{name: 'leaf',       type: 'bool',    defaultValue: false},
	{name: 'cls',        type: 'string',  defaultValue: null, persist: false},
	{name: 'iconCls',    type: 'string',  defaultValue: null, persist: false},
	{name: 'icon',       type: 'string',  defaultValue: null, persist: false},
	{name: 'root',       type: 'boolean', defaultValue: false, persist: false},
	{name: 'isLast',     type: 'boolean', defaultValue: false, persist: false},
	{name: 'isFirst',    type: 'boolean', defaultValue: false, persist: false},
	{name: 'allowDrop',  type: 'boolean', defaultValue: true, persist: false},
	{name: 'allowDrag',  type: 'boolean', defaultValue: true, persist: false},
	{name: 'loaded',     type: 'boolean', defaultValue: false, persist: false},
	{name: 'loading',    type: 'boolean', defaultValue: false, persist: false},
	{name: 'href',       type: 'string',  defaultValue: null, persist: false},
	{name: 'hrefTarget', type: 'string',  defaultValue: null, persist: false},
	{name: 'qtip',       type: 'string',  defaultValue: null, persist: false},
	{name: 'qtitle',     type: 'string',  defaultValue: null, persist: false},
	{name: 'children',   type: 'auto',   defaultValue: null, persist: false}
 *
 */
public class ExtjsTreeType {
	/**
	 * 节点ID
	 */
	public static final String ID="id";
	/**
	 * 节点名称
	 */
	public static final String NAME="name";
	/**节点内容*/
	public static final String TEXT="text";
	/**用于指示该节点的父节点ID,integer or string*/
	public static final String PARENTID="parentId";
	/**
	 * integer or string
	 * 用于存储节点顺序,插入或删除节点时，其所有同级节点插入或移除点之后的指标更新,
	 * 如果需要,应用程序可以使用此字段保存节点顺序然而，如果服务器使用不同的方法存储顺序，
	 * 可能更适当的索引字段保留为非持久性。
	 * 在使用WebStorage代理时，如果需要存储顺序，此字段必须是持久的。
	 * 如果正在使用客户端排序，还是推荐索引字段保留为非持久性，从排序的索引更新所有节点排序后，
	 * 这将导致他们坚持在下次同步或保存如果坚持属性为true*/
	public static final String INDEX="index";
	/**
	 * int
	 * 用于存储树中的节点层次结构的深度。重写这一领域打开持久性，如果服务器需要存储深度领域。
	 * 在使用WebStorage代理建议时，不会覆盖深度的持久性字段，因为它不需要妥善存储树结构，
	 * 而且只会占用额外的空间
	 */
	public static final String DEPTH="depth";
	/**
	 * boolean
	 * 用于存储节点的展开/折叠状态。此字段通常不需要重写
	 * */
	public static final String EXPANDED="expanded";
	/**
	 * boolean
	 * 表明节点是否可展开,一般不需要重写
	 */
	public static final String EXPANDABLE="expandable";
	/**
	 * boolean
	 * 用于开启树的CheckBox功能
	 */
	public static final String CHECKED="checked";
	/**
	 * boolean
	 * 指明是否为子节点
	 */
	public static final String LEAF="leaf";
	/**
	 * string
	 * 当渲染在一个TreePanel中时指明应用的CSS
	 */
	public static final String CLS="cls";
	/**
	 * string
	 * 设置节点图标,从CSS文件中获取
	 */
	public static final String ICON_CLS="iconCls";
	/**
	 * string
	 * 设置节点图标
	 */
	public static final String ICON="icon";
	/**用于指示此节点的根节点,此字段不能被重写*/
	public static final String ROOT="root";
	/**
	 * boolean
	 * 指定节点在同级别中是最后一个
	 */
	public static final String IS_LAST="isLast";
	/**
	 * boolean
	 * 指定节点在同级别中是第一个
	 */
	public static final String IS_FIRST="isFirst";
	public static final String ALLOW_DROP="allowDrop";
	public static final String ALLOW_DRAG="allowDrag";
	/**
	 * 指明节点已加载
	 */
	public static final String LOADED="loaded";
	/**在内部用于指示代理是加载过程中节点的子节点*/
	public static final String LOADING="loading";
	/**节点链接*/
	public static final String HREF="href";
	/**节点链接目标*/
	public static final String HREF_TARGET="hrefTarget";
	/**添加节点文本提示*/
	public static final String QTIP="qtip";
	/**用于指定工具提示的标题*/
	public static final String QTITLE="qtitle";
	/**在同一个请求加载一个节点及其子节点都*/
	public static final String CHILDREN="children";
	private String name;
	private Object value;
	private boolean isString;
	public ExtjsTreeType(){}
	/**
	 * @param name 类型名称
	 * @param value 类型值
	 */
	public ExtjsTreeType(String name, Object value) {
		this(name, value, true);
	}
	/**
	 * 
	 * @param name 类型名称
	 * @param value 类型值
	 * @param isString 是否为字符串
	 */
	public ExtjsTreeType(String name, Object value,boolean isString) {
		this.name = name;
		this.value = value;
		this.isString=isString;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtjsTreeType other = (ExtjsTreeType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	/**
	 * 返回字符'name':'value'
	 */
	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder("'");
		builder.append(name).append("':");
		if(isString){
			builder.append("'");
		}
		builder.append(value.toString());
		if(isString){
			builder.append("'");
		}
		return builder.toString();
	}
}
