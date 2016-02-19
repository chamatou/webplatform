package cn.chamatou.account.service;

import cn.chamatou.account.entity.Account;
import cn.chamatou.account.exception.AccountException;
import cn.chamatou.commons.data.jpa.IDao;

public interface AccountService extends IDao<Account>{
	public static final int CIPHER_ENCODE=1;
	public static final int CIPHER_DECODE=0;
	//数据加解密,
	String cipher(String text,int mode);
	/**
	 * 账号是否存在
	 * @param name
	 * @return
	 */
	boolean exist(String name);
	/**
	 * 注册新账号
	 * @param name
	 * @param password
	 * @param ipaddr 用户注册地IP
	 * @return 返回注册成功账号
	 */
	Account regist(String name,String password,String ipaddr)throws AccountException;
	/**
	 * 注册新账号
	 * @param name
	 * @param password
	 * @param ipaddr
	 * @param recommender 注册推荐人
	 * @return
	 * @throws AccountException
	 */
	Account regist(String name,String password,String ipaddr,Account recommender)throws AccountException;
	/**
	 * 账号登陆
	 * @param name
	 * @param password
	 * @return 返回登陆成功账号
	 */
	Account login(String name,String password)throws AccountException;
	/**
	 * 提升账号权限
	 * @param roles
	 * @return
	 */
	void addAccountRoles(String name,int roles)throws AccountException;
	/**
	 * 移除用户权限
	 * @param name
	 * @param roles
	 * @return
	 */
	void removeAccountRoles(String name,int roles)throws AccountException;
	
	
}
