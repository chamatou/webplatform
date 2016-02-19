package cn.chamatou.commons.data.pki;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
/**
 * RSA加密/解密
 *
 */
public class RSACipher{
	/**
	 * RSA最大加密明文大小
	 */
	public static final int MAX_ENCRYPT_BLOCK = 117;
	/**
	 * RSA最大解密密文大小
	 */
	public static final int MAX_DECRYPT_BLOCK = 128;
	// 私钥加密
	private Cipher priEvt;
	// 公钥解密
	private Cipher pubDevt;
	// 公钥加密
	private Cipher pubEvt;
	// 私钥解密
	private Cipher priDevt;

	public RSACipher(PrivateKey privateKey, PublicKey publicKey){
		if(!privateKey.getAlgorithm().equalsIgnoreCase("RSA")||!publicKey.getAlgorithm().equalsIgnoreCase("RSA")){
			throw new IllegalArgumentException("Must need algorithm with RSA");
		}
		priEvt = getCipher(privateKey, true);
		pubDevt = getCipher(publicKey, false);
		pubEvt = getCipher(publicKey, true);
		priDevt = getCipher(privateKey, false);
	}
	/**
	 * 公钥解密
	 * @param encryptedData
	 * @return
	 */
	public byte[] priDencrypt(byte[] encryptedData)throws Exception{
		return dencrypt(priDevt, encryptedData);
	}
	/**
	 * 公钥加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] priEncrypt(byte[] data)throws Exception{
		return encrypt(priEvt, data);
	}
	/**
	 * 公钥解密
	 * 
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 */
	public byte[] pubDencrypt(byte[] encryptedData) throws Exception {
		return dencrypt(pubDevt, encryptedData);
	}
	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            加密原文
	 * @return
	 */
	public byte[] pubEncrypt(byte[] data) throws Exception {
		return encrypt(pubEvt, data);
	}
	//解密
	private byte[] dencrypt(Cipher cipher,byte[] encryptedData)throws Exception{
		int length = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (length - offSet > 0) {
			if (length - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet,
						MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, length - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
	// 加密
	private byte[] encrypt(Cipher cipher, byte[] data) throws Exception {
		int dateLength = data.length;
		int offSet = 0;
		byte[] cache;
		int index = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 对数据分段加密
		while (dateLength - offSet > 0) {
			if (dateLength - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, dateLength - offSet);
			}
			out.write(cache, 0, cache.length);
			index++;
			offSet = index * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	private Cipher getCipher(Key key, boolean isEncypt) {
		Cipher c = null;
		try {
			c = Cipher.getInstance(key.getAlgorithm());
			c.init(isEncypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return c;
	}

}
