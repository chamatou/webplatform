package cn.chamatou.commons.data.utils;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

/**
 * 秘钥工具类
 *
 */
public class KeyUtils {
	private static final String algorithm="RSA";
	/**
	 * 构建私钥
	 * @param bytes 私钥的二进制编码
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static final  PrivateKey buildRSAPrivateKey(byte[] bytes)throws InvalidKeySpecException, NoSuchAlgorithmException{
		KeyFactory keyFactory;
		keyFactory = KeyFactory.getInstance(algorithm);
		PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(bytes);
		return keyFactory.generatePrivate(pkcs8KeySpec);
	}
	
	/**
	 * 构建私钥
	 * @param pk Base64编码的私钥
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException 
	 */
	public static final  PrivateKey buildRSAPrivateKey(String pk)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		byte[] bytes=Base64.decodeBase64(pk);
		return buildRSAPrivateKey(bytes);
	}
	public static final  PublicKey buildRSAPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException{
		KeyFactory keyFactory;
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePublic(keySpec);
	}
	/**
	 * 构建公钥
	 * @param pubk Base64编码的公钥
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static final  PublicKey buildRSAPublicKey(String pubk) throws NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] bytes=Base64.decodeBase64(pubk);
		return buildRSAPublicKey(bytes);
	}
	/**
	 * 构建RSA秘钥
	 * @return
	 */
	public static final KeyPair generatorRSAKey(Integer size){
		try {
			KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
			kpg.initialize(size==null?1024:size, new SecureRandom());
			return kpg.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成AES对称秘钥
	 * @return
	 */
	public static final byte[] generatorAesKey(Integer size){
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(size==null?128:size, new SecureRandom());
			SecretKey skey = kgen.generateKey();  
			return skey.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
