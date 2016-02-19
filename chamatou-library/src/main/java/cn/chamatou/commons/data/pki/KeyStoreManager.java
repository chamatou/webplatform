package cn.chamatou.commons.data.pki;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

/**
 * java秘钥管理器
 */
public class KeyStoreManager{
	private KeyStore keystore;
	
	private String firstAlias;
	/**
	 * 默认java提供的keystore文件
	 * The SUN Provider
	 
	private static final String JKS="JKS";*/
	/**
	 * 默认java提供的p12或pfx文件
	 * 提供者SunJSSE
	 */
	private static final String PKCS12="PKCS12";
	/**
	 * 默认java提供的keystore文件
	 * 提供者SunJCE
	 */
	private static final String JCEKS="JCEKS";
	/**
	 * windows个人证书库
	 * 提供者SunMSCAPI 
	 */
	public static final String WINDOWS_MY="Windows-MY";
	/**
	 * windows根证书库
	 * 提供者SunMSCAPI
	 */
	public static final String WINDOWS_ROOT="Windows-ROOT";
	/**
	 * 提供者，访问JCEKS类型时使用
	 */
	private static final String PROVIDER_SUNJCE="SunJCE";
	/**
	 * 提供者，访问WINDOWS_ROOT，PROVIDER_SUNJCE时使用
	 */
	private static final String PROVIDER_MSC="SunMSCAPI";
	/**
	 * 默认sun提供者
	 */
	private static final String PROVIDER_JSSE="SunJSSE";
	
	/**
	 * 从p12文件中构建KeyStoreManager
	 * @param file
	 * @param password
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static KeyStoreManager buildForP12(File file,String password) throws CertificateException, IOException{
		FileInputStream is=null;
		try{
			is=new FileInputStream(file);
			return buildForP12(is,password);
		}finally{
			if(is!=null){
				is.close();
			}
		}
	}
	/**
	 * 从p12文件中构建KeyStoreManager
	 * @param stream
	 * @param password
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static KeyStoreManager buildForP12(InputStream stream,String password) throws CertificateException, IOException{
		return buildFileKS(PKCS12, PROVIDER_JSSE, stream, password);
	}
	/**
	 * 从文件获取jks
	 * @param file
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws CertificateException
	 */
	public static KeyStoreManager buildForJCE(File file,String password) throws IOException, CertificateException{
		FileInputStream is=null;
		try{
			is=new FileInputStream(file);
			return buildForJCE(is,password);
		}finally{
			if(is!=null){
				is.close();
			}
		}
	}
	/**
	 * 获取sun提供的keyStore
	 * @param stream
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws CertificateException
	 */
	public static KeyStoreManager buildForJCE(InputStream stream,String password) throws IOException, CertificateException{
		return buildFileKS(JCEKS, PROVIDER_SUNJCE, stream, password);
	}
	/**
	 * 获取windows证书库
	 * @param type 取值WINDOWS_ROOT或WINDOWS_ME
	 * @return
	 * @throws Exception
	 */
	public static KeyStoreManager buildForMSC(String type) throws Exception{
		KeyStoreManager ksm=new KeyStoreManager();
		ksm.keystore=KeyStore.getInstance(type, PROVIDER_MSC);
		ksm.keystore.load(null, null);
		Enumeration<String> aliases=ksm.keystore.aliases();
		if(aliases.hasMoreElements()){
			ksm.firstAlias=aliases.nextElement();
		}
		return ksm;
	}
	
	
	private static KeyStoreManager buildFileKS(String type,String provider,InputStream stream,String password)throws IOException, CertificateException{
		KeyStoreManager ks=new KeyStoreManager();
		try {
			ks.keystore=KeyStore.getInstance(type,provider);
			ks.keystore.load(stream, password.toCharArray());
			Enumeration<String> aliases=ks.keystore.aliases();
			if(aliases.hasMoreElements()){
				ks.firstAlias=aliases.nextElement();
			}
			return ks;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}
	private KeyStoreManager(){
		
	}
	/**
	 * 获取keystoe中的所有别名
	 * @return
	 */
	public Enumeration<String> getAlias(){
		try {
			return keystore.aliases();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过域名获取证书
	 * @param alias 别名，设置null默认采用第一个,如果没有返回null
	 * @return
	 */
	public Certificate getCertificate(String alias){
		String a=alias==null?firstAlias:alias;
		try {
			return keystore.getCertificate(a);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取指定别名的私钥
	 * @param alias 别名，设置null默认采用第一个,如果没有返回null
	 * @param password
	 * @return
	 */
	public PrivateKey getPrivateKey(String alias,String password){
		String a=alias==null?firstAlias:alias;
		try {
			return (PrivateKey)keystore.getKey(a, password.toCharArray());
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取指定别名的公钥
	 * @param alias 别名，设置null默认采用第一个,如果没有返回null
	 * @return
	 */
	public PublicKey getPublicKey(String alias){
		try{
			Certificate c=getCertificate(alias);
			return c.getPublicKey();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
} 
