package cn.chamatou.commons.data.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * 字符编码类,默认采用utf-8
 *
 */
public class CoderUtil {
	public static final Charset charset=Charset.forName("utf-8");
	
	private static final Charset getCharset(String cs){
		Charset c=null;
		try{
			c=Charset.forName(cs);
		}catch(UnsupportedCharsetException e){
			c=charset;
		}
		return c;
	}
	public static final String urlDecode(String encode){
		try {
			return URLDecoder.decode(encode, charset.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encode;
	}
	
	public static final String urlEncode(String url){
		try {
			return URLEncoder.encode(url,charset.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	/**
	 * 将字符串转换为二进制表现形式
	 * @param str
	 * @param cs
	 * @param toLowerCase
	 * @return
	 */
	public static final String hex(String str,String cs,boolean toLowerCase){
		byte[] bytes=str.getBytes(getCharset(cs));
		if(toLowerCase){
			return Hex.encodeHexString(bytes).toLowerCase();
		}
		return Hex.encodeHexString(bytes).toUpperCase();
	}
	/**
	 * 解码Base64字符串
	 * @param base64Str
	 * @return
	 */
	public static final String base64Decode(String base64Str){
		return base64Decode(base64Str,charset.displayName());
	}
	/**
	 * 解码Base64字符串
	 * @param base64Str
	 * @param cs
	 * @return
	 */
	public static final String base64Decode(String base64Str,String cs){
		byte[] bytes=Base64.decodeBase64(base64Str);
		return new String(bytes,getCharset(cs));
	}
	public static final String base64Encode(byte[] bytes){
		return Base64.encodeBase64String(bytes);
	}
	
	/**
	 * 将字符串转换为Base64编码
	 * @param str
	 * @return
	 */
	public static final String base64Encode(String str){
		return base64Encode(str,charset.displayName());
	}
	/**
	 * 将字符串转换为Base64编码
	 * @param str
	 * @param cs 字符集类型
	 * @return
	 */
	public static final String base64Encode(String str,String cs){
		return Base64.encodeBase64String(str.getBytes(getCharset(cs))).trim();
	}
	public static final String MD5(byte[] bytes){
		return DigestUtils.md5Hex(bytes);
	}
	
	public static final String MD5(String str){
		return DigestUtils.md5Hex(str.getBytes(charset));
	}
}
