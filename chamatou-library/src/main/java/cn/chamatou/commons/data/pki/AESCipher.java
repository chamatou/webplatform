package cn.chamatou.commons.data.pki;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import cn.chamatou.commons.data.utils.CoderUtil;
/**
 * AES加密，默认 AES/ECB/PKCS5PADDING
 *
 */
public class AESCipher {
	private Cipher ec;
	private Cipher dc;
	private static final String alog="AES";
	private static final String transformation="AES/ECB/PKCS5PADDING";
	private Charset charset;
	private static volatile Cipher eCipher;
	private static volatile Cipher dCipher;
	static{
		char[] ck="0d5e0b8f4357ef23".toCharArray();
		try {
			SecretKeySpec key=new SecretKeySpec(Hex.decodeHex(ck), "DES");
			eCipher=Cipher.getInstance("DES");
			eCipher.init(Cipher.ENCRYPT_MODE,key);
			dCipher=Cipher.getInstance("DES");
			dCipher.init(Cipher.DECRYPT_MODE,key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 使用默认秘钥进行加密
	 * @param text
	 * @return
	 */
	public static final String defaultEncrypt(String text){
		try {
			byte[]	echar=eCipher.doFinal(text.getBytes("utf8"));
			return new String(Base64.encodeBase64(echar));
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 使用默认秘钥对字符解密
	 * @param base64char
	 * @return
	 */
	public static String decrypt(String base64char){
		try {
			byte[] dchar=Base64.decodeBase64(base64char.getBytes("utf8"));
			byte[] text= dCipher.doFinal(dchar);
			return new String(text,"utf8");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return null;
	}
	public String decode(String b64Cipher){
		try {
			byte[] cipherByte=Base64.decodeBase64(b64Cipher);
			byte[] result=dc.doFinal(cipherByte);
			result=dc.doFinal(result);
			result=dc.doFinal(result);
			return new String(result,charset);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 加密文本
	 * @param text
	 * @return
	 */
	public String encode(String text){
		try {
			byte[] bytes=text.getBytes(charset);
			bytes=ec.doFinal(bytes);
			bytes=ec.doFinal(bytes);
			return CoderUtil.base64Encode(ec.doFinal(bytes));
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AESCipher(byte[] aesKey){
		SecretKey secretKey=new SecretKeySpec(aesKey,alog);
		try {
			ec=Cipher.getInstance(transformation);
			ec.init(Cipher.ENCRYPT_MODE,secretKey);
			dc=Cipher.getInstance(transformation);
			dc.init(Cipher.DECRYPT_MODE, secretKey);
			charset=Charset.forName("utf-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	public AESCipher(String aesKey){
		this(Base64.decodeBase64(aesKey));
	}
	
}
