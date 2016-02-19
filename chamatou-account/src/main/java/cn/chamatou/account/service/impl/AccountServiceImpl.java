package cn.chamatou.account.service.impl;
import java.io.IOException;
import java.util.Date;
import javax.annotation.Resource;
import javax.persistence.LockModeType;
import org.springframework.stereotype.Service;
import cn.chamatou.account.entity.Account;
import cn.chamatou.account.exception.AccountException;
import cn.chamatou.account.service.AccountService;
import cn.chamatou.account.service.InjectDao;
import cn.chamatou.commons.data.filesystem.FileStoreManager;
import cn.chamatou.commons.data.pki.AESCipher;
import cn.chamatou.commons.data.utils.VariableVerifyUtils;
@Service("accountService")
public class AccountServiceImpl extends InjectDao<Account> implements AccountService{

	private static final long serialVersionUID = 2625281238803080544L;
	private AESCipher aesChipher;
	@Resource
	private FileStoreManager fileStoreManager;
	public AccountServiceImpl(){
		aesChipher=new AESCipher("gTm0OqJZ1qI7y1tlvb49QQ==");
	}
	@Override
	public boolean exist(String name) {
		return findById(name)!=null;
	}
	
	@Override
	public Account regist(String name, String password,String ipaddr) throws AccountException {
		return regist(name, password, ipaddr,null);
	}
	@Override
	public Account regist(String name, String password, String ipaddr,
			Account recommender) throws AccountException {
		if(exist(name)){
			throw new AccountException("Account exist.");
		}
		Account account=new Account();
		account.setAccount(name);
		account.setPassword(aesChipher.encode(password));
		account.setRegistTime(new Date());
		account.setRecommender(recommender);
		account.setIpaddr(ipaddr);
		byte[] qrCode=account.generatorQRCode();
		if(qrCode!=null){
			try {
				String code=fileStoreManager.store(name,"qrcode.png", qrCode);
				account.setQcode(code);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(VariableVerifyUtils.emailValidate(name)){
			account.setEmail(name);
		}else if(VariableVerifyUtils.mobileValidate(name)){
			account.setPhone(name);
		}
		persist(account);
		
		return account;
	}
	@Override
	public Account login(String name, String password) throws AccountException {
		return null;
	}

	@Override
	public void addAccountRoles(String name, int roles)throws AccountException  {
		if(roles==Account.ROLE_BIZ||roles==Account.ROLE_STORE||roles==Account.ROLE_TEACHER){
			Account account=findById(name);
			if(account==null)throw new AccountException("Account not found.");
			em.lock(account, LockModeType.PESSIMISTIC_WRITE);
			account.setAccountRole(account.getAccountRole()|roles);
		}else{
			throw new AccountException("Argument roles is error.");
		}
	}
	@Override
	public void removeAccountRoles(String name, int roles)throws AccountException{
		if(roles==Account.ROLE_BIZ||roles==Account.ROLE_STORE||roles==Account.ROLE_TEACHER){
			Account account=findById(name);
			if(account==null)throw new AccountException("Account not found.");
			em.lock(account, LockModeType.PESSIMISTIC_WRITE);
			account.setAccountRole(account.getAccountRole()^roles);
		}else{
			throw new AccountException("Argument roles is error.");
		}
	}
	@Override
	public String cipher(String text, int mode) {
		if(mode==CIPHER_DECODE){
			return aesChipher.decode(text);
		}
		return aesChipher.encode(text);
	}
}
