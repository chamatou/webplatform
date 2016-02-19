package cn.chamatou.commons.data.utils;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.chamatou.commons.data.image.ImageTool;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 二维码工具类 
 */
public class QRCodeUtils {
	public static BufferedImage encode(String text,int width,int height) throws WriterException, IOException{
		return encode(text, width, height, "png");
	}
	
	public static BufferedImage encode(String text,int width,int height,String format) throws WriterException, IOException{
        return ImageTool.getImage(encodeToBytes(text, width, height, format));
	}
	public static byte[] encodeToBytes(String text,int width,int height)throws WriterException, IOException{
		return encodeToBytes(text, width, height, "png");
	}
	public static byte[] encodeToBytes(String text,int width,int height,String format)throws WriterException, IOException{
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, CoderUtil.charset.toString());  
        
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,  
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, format, out);
        return out.toByteArray();
	}
	public static String decode(BufferedImage image) throws NotFoundException{
        LuminanceSource source = new BufferedImageLuminanceSource(image);  
        Binarizer binarizer = new HybridBinarizer(source);  
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();  
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");  
        Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码  
        return result.getText();
	}
}
