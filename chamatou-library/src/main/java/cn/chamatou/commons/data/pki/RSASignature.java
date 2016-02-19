package cn.chamatou.commons.data.pki;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.binary.Base64;

import cn.chamatou.commons.data.utils.KeyUtils;
/**
 * RSA数据签名
 *
 */
public class RSASignature {
	private  PrivateKey privateKey;
	private  PublicKey publicKey;
	private static final String signAlgorith="SHA1WithRSA";
	private Signature sign;
	private Signature verify;
	private String charset;
	/**
	 * 
	 * @param pk 签名私钥
	 * @param pubk 签名公钥
	 * @param charset 字符集
	 */
	public RSASignature(String pk, String pubk,String charset) {
		try {
			this.charset=charset;
			if(pk!=null){
				privateKey=KeyUtils.buildRSAPrivateKey(pk);		
				sign=Signature.getInstance(signAlgorith);
				sign.initSign(privateKey);
			}
			if(pubk!=null){
				publicKey=KeyUtils.buildRSAPublicKey(pubk);	
				verify=Signature.getInstance(signAlgorith);
				verify.initVerify(publicKey);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 用自有秘钥进行P1签名
	 * @param text 签名原文
	 * @return 签名失败返回原文
	 */
	public  String signByP1(String text){
		byte[] bytes=null;
		try {
			bytes = text.getBytes(charset);
			sign.update(bytes);
			byte[] result=sign.sign();
			return Base64.encodeBase64String(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return text;
	}
	/**
	 * 验证签名
	 * @param text 原文
	 * @param b64Sign 签名值得base64编码
	 * @return
	 */
	public  boolean verifyP1(String text,String b64Sign){
		try {
			byte[] bytes=text.getBytes(charset);
			byte[] sign=Base64.decodeBase64(b64Sign);
			verify.update(bytes);
			return verify.verify(sign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
}