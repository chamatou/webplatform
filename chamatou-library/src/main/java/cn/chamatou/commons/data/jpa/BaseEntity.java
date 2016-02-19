package cn.chamatou.commons.data.jpa;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import cn.chamatou.commons.data.pki.RSACipher;
import cn.chamatou.commons.data.utils.KeyUtils;
/**
 *  基础类,记录数据写入时间
 */
@MappedSuperclass
public  class BaseEntity{
	private static final String ERROR_DATA="error data";
	private static RSACipher rsaCipher;
	private String time=ERROR_DATA;
	private static SimpleDateFormat sdf;
	static{
		try {
			sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			rsaCipher=new RSACipher(KeyUtils.buildRSAPrivateKey("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMRq2nNoterxUpcrfS0hOF21JwWN0EkqRYWykD2p9+WosAQLWYxAS1qjWGlwY1Fo/XYCwZ+9Dv+wXWZfVLN3YlQVigFeY5Mf6FUeM4ZoB0BIIAU383EPHa1C8uDsSaMkekuBJlZA7Ke7w0Y3YGGlGZTdZZ3YOKCl+OJ84Kj9WJWTAgMBAAECgYBCYnLuqqdBfsqROSuXgzDPyxgjMmkBMX5Jz64ciUO1pSvIUiWz108glTCeglOvAjMYHZpfaMCW/n6yyOmRtnnOpY40yNUWl3r8X8/DxO9sVyAQIIVtBy/lk0BcpbGmPHeAjozZhpMngbUwduQ17tdW8LSoU/JsgbAVqCb3Qe3XIQJBAO8Dj+uRjI6CVLPyMEw+73NIKcTm0KlYAmqUUqzScgoKjQpCz1NbX8e6siflamBL3yZ0GQE7bjJjqvw9h22FYisCQQDSYFKIFnnn5m5ReWU3vO2pRDrE+Xglo90++mYkfJ/qc9i+5jUhVmfpWKWsWtl2Ef6SrjIakakH4fdyn00CBi45AkBRI3xwE/ELT+NBUy5iM9tF33GOjmy3kFEBrWiDks1z6zEGErgfLB3Px+lC8fayyg7vFuqGKI64PG2HQ7v9yhIRAkBov/1YNZjNlzcm6kMz1aaguZBps63XUBhB23wwfr1BiB1MMGQUDWoADluvziypVZxkfMgF4rE/c4w6ToeRknJBAkA7MMHc8ZGfETn8np4VUcDkvRK+r8/Y3u2caBhJ2yXqP5g94t1cShE+yrFnhYDHSY+uH0KHsE54Bc8/GN7rSNab"),KeyUtils.buildRSAPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEatpzaLXq8VKXK30tIThdtScFjdBJKkWFspA9qfflqLAEC1mMQEtao1hpcGNRaP12AsGfvQ7/sF1mX1Szd2JUFYoBXmOTH+hVHjOGaAdASCAFN/NxDx2tQvLg7EmjJHpLgSZWQOynu8NGN2BhpRmU3WWd2DigpfjifOCo/ViVkwIDAQAB"));
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	protected byte[] erc;
	@Lob
	public byte[] getErc() {
		return erc;
	}
	public void setErc(byte[] erc) {
		this.erc = erc;
	}
	/**
	 * 是否为校验过的数据
	 * @return
	 */
	@Transient
	public boolean dataSuccess(){
		try {
			rsaCipher.pubDencrypt(erc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public void encryptErc(Date date) throws Exception{
		String time=String.valueOf(date.getTime());
		setErc(rsaCipher.priEncrypt(time.getBytes()));
	}
	@Transient
	public String getUpdateOrRecordTime(){
		if(erc!=null&&time.equals(ERROR_DATA)){
			try {
				byte[] b=rsaCipher.pubDencrypt(erc);
				String s=new String(b);
				time=sdf.format(sdf.parse(s));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return time;
	}
}
