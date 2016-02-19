package cn.chamatou.commons.httpclient;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.TrustManagerFactory;
public class TrustManagerFactoryUtil {
	/**
	 * 获取Keystore记录的信任证书
	 * @return
	 * @throws KeyStoreException 
	 */
	public static final TrustManagerFactory getKeystoreFactory(KeyStore keyStore) throws KeyStoreException{
		TrustManagerFactory factory=InsecureTrustManagerFactory.INSTANCE;
		try {
			factory = TrustManagerFactory.getInstance("PKIX");
			factory.init(keyStore);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return factory;
	}
}
