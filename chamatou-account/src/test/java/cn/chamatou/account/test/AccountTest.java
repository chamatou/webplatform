package cn.chamatou.account.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.chamatou.account.entity.Account;
import cn.chamatou.account.exception.AccountException;
import cn.chamatou.account.service.AccountService;

public class AccountTest {
	static ApplicationContext ctx;
	@Before
	public void before(){
		if(ctx==null){
			ctx=new ClassPathXmlApplicationContext("spring-context.xml");
		}
	}
	@Test
	public void testRepl(){
		int a=1;
		int b=2;
		int c=a|b;
		System.out.println(1|2|2|2|2);
		int d=c^b;
		System.out.println(d);
	}
	
	@Test
	public void testInsert() throws AccountException{
		AccountService as=(AccountService) ctx.getBean("accountService");
		as.regist("13350092891","11111111","126.7.7.7");
		
	}
	@Test
	public void addRoles() throws AccountException{
		AccountService as=(AccountService) ctx.getBean("accountService");
		as.addAccountRoles("13350092891", Account.ROLE_BIZ);
	}
	@Test
	public void removeRoles() throws AccountException{
		AccountService as=(AccountService) ctx.getBean("accountService");
		as.addAccountRoles("13350092891", Account.ROLE_STORE);
	}
}
