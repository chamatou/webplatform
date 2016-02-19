package cn.chamatou.account.test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;

import cn.chamatou.commons.data.pki.RSACipher;
import cn.chamatou.commons.data.utils.CoderUtil;
import cn.chamatou.commons.data.utils.KeyUtils;
import cn.chamatou.commons.entity.PageAccessLog;
import cn.chamatou.commons.web.json.JsonUtils;

public class RSATest {
	@Test
	public void getRSAKey()throws Exception{
		PrivateKey pk=KeyUtils.buildRSAPrivateKey("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMRq2nNoterxUpcrfS0hOF21JwWN0EkqRYWykD2p9+WosAQLWYxAS1qjWGlwY1Fo/XYCwZ+9Dv+wXWZfVLN3YlQVigFeY5Mf6FUeM4ZoB0BIIAU383EPHa1C8uDsSaMkekuBJlZA7Ke7w0Y3YGGlGZTdZZ3YOKCl+OJ84Kj9WJWTAgMBAAECgYBCYnLuqqdBfsqROSuXgzDPyxgjMmkBMX5Jz64ciUO1pSvIUiWz108glTCeglOvAjMYHZpfaMCW/n6yyOmRtnnOpY40yNUWl3r8X8/DxO9sVyAQIIVtBy/lk0BcpbGmPHeAjozZhpMngbUwduQ17tdW8LSoU/JsgbAVqCb3Qe3XIQJBAO8Dj+uRjI6CVLPyMEw+73NIKcTm0KlYAmqUUqzScgoKjQpCz1NbX8e6siflamBL3yZ0GQE7bjJjqvw9h22FYisCQQDSYFKIFnnn5m5ReWU3vO2pRDrE+Xglo90++mYkfJ/qc9i+5jUhVmfpWKWsWtl2Ef6SrjIakakH4fdyn00CBi45AkBRI3xwE/ELT+NBUy5iM9tF33GOjmy3kFEBrWiDks1z6zEGErgfLB3Px+lC8fayyg7vFuqGKI64PG2HQ7v9yhIRAkBov/1YNZjNlzcm6kMz1aaguZBps63XUBhB23wwfr1BiB1MMGQUDWoADluvziypVZxkfMgF4rE/c4w6ToeRknJBAkA7MMHc8ZGfETn8np4VUcDkvRK+r8/Y3u2caBhJ2yXqP5g94t1cShE+yrFnhYDHSY+uH0KHsE54Bc8/GN7rSNab");
		PublicKey pub=KeyUtils.buildRSAPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEatpzaLXq8VKXK30tIThdtScFjdBJKkWFspA9qfflqLAEC1mMQEtao1hpcGNRaP12AsGfvQ7/sF1mX1Szd2JUFYoBXmOTH+hVHjOGaAdASCAFN/NxDx2tQvLg7EmjJHpLgSZWQOynu8NGN2BhpRmU3WWd2DigpfjifOCo/ViVkwIDAQAB");
		RSACipher rc=new RSACipher(pk, pub);
		String time=String.valueOf(System.currentTimeMillis());
		System.out.println(rc.priEncrypt(time.getBytes()).length);
		System.out.println(rc.pubEncrypt(time.getBytes()).length);
	}
	@Test
	public void buildPk() throws InvalidKeySpecException, NoSuchAlgorithmException{
		KeyUtils.buildRSAPrivateKey("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMRq2nNoterxUpcrfS0hOF21JwWN0EkqRYWykD2p9+WosAQLWYxAS1qjWGlwY1Fo/XYCwZ+9Dv+wXWZfVLN3YlQVigFeY5Mf6FUeM4ZoB0BIIAU383EPHa1C8uDsSaMkekuBJlZA7Ke7w0Y3YGGlGZTdZZ3YOKCl+OJ84Kj9WJWTAgMBAAECgYBCYnLuqqdBfsqROSuXgzDPyxgjMmkBMX5Jz64ciUO1pSvIUiWz108glTCeglOvAjMYHZpfaMCW/n6yyOmRtnnOpY40yNUWl3r8X8/DxO9sVyAQIIVtBy/lk0BcpbGmPHeAjozZhpMngbUwduQ17tdW8LSoU/JsgbAVqCb3Qe3XIQJBAO8Dj+uRjI6CVLPyMEw+73NIKcTm0KlYAmqUUqzScgoKjQpCz1NbX8e6siflamBL3yZ0GQE7bjJjqvw9h22FYisCQQDSYFKIFnnn5m5ReWU3vO2pRDrE+Xglo90++mYkfJ/qc9i+5jUhVmfpWKWsWtl2Ef6SrjIakakH4fdyn00CBi45AkBRI3xwE/ELT+NBUy5iM9tF33GOjmy3kFEBrWiDks1z6zEGErgfLB3Px+lC8fayyg7vFuqGKI64PG2HQ7v9yhIRAkBov/1YNZjNlzcm6kMz1aaguZBps63XUBhB23wwfr1BiB1MMGQUDWoADluvziypVZxkfMgF4rE/c4w6ToeRknJBAkA7MMHc8ZGfETn8np4VUcDkvRK+r8/Y3u2caBhJ2yXqP5g94t1cShE+yrFnhYDHSY+uH0KHsE54Bc8/GN7rSNab");
		KeyUtils.buildRSAPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEatpzaLXq8VKXK30tIThdtScFjdBJKkWFspA9qfflqLAEC1mMQEtao1hpcGNRaP12AsGfvQ7/sF1mX1Szd2JUFYoBXmOTH+hVHjOGaAdASCAFN/NxDx2tQvLg7EmjJHpLgSZWQOynu8NGN2BhpRmU3WWd2DigpfjifOCo/ViVkwIDAQAB");
	}
	@Test
	public void testJson(){
		PageAccessLog pl=new PageAccessLog();
		pl.setFormPage("mepage");
		String js=JsonUtils.classJson(PageAccessLog.class,pl,new String[]{"formPage"});
		System.out.println(js);
	}
}
