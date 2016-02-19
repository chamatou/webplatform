package cn.chamatou.commons.data.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class ImageTool {
	public static ImageTool getDefault(){
		return new DefaultImageTool();
	}
	public static BufferedImage getImageByFile(File file) throws MalformedURLException{
		Image image=Toolkit.getDefaultToolkit().getImage(file.toURI().toURL());
		return toBufferedImage(image);
	}
	public static BufferedImage getImage(byte[] bits) throws IOException{
		ByteArrayInputStream in=new ByteArrayInputStream(bits);
		return ImageIO.read(in);
	}
	
	public static byte[] writerBufferedImage(BufferedImage image,String formatName){
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		try {
			ImageIO.write(image, formatName, out);
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage toBufferedImage(Image image) {  
        if (image instanceof BufferedImage) {  
            return (BufferedImage) image;  
        }  
        // This code ensures that all the pixels in the image are loaded  
        image = new ImageIcon(image).getImage();  
        BufferedImage bimage = null;  
        GraphicsEnvironment ge = GraphicsEnvironment  
                .getLocalGraphicsEnvironment();  
        try {  
            int transparency = Transparency.OPAQUE;  
            GraphicsDevice gs = ge.getDefaultScreenDevice();  
            GraphicsConfiguration gc = gs.getDefaultConfiguration();  
            bimage = gc.createCompatibleImage(image.getWidth(null),  
                    image.getHeight(null), transparency);  
        } catch (HeadlessException e) {  
            // The system does not have a screen  
        }  
        if (bimage == null) {  
            // Create a buffered image using the default color model  
            int type = BufferedImage.TYPE_INT_RGB;  
            bimage = new BufferedImage(image.getWidth(null),  
                    image.getHeight(null), type);  
        }  
        // Copy image to buffered image  
        Graphics g = bimage.createGraphics();  
        // Paint the image onto the buffered image  
        g.drawImage(image, 0, 0, null);  
        g.dispose();  
        return bimage;  
    }  
	
	
	/**
	 * 图片剪裁
	 * @param in 输入流
	 * @param bx 开始x坐标
	 * @param by 开始y坐标
	 * @param ex 结束x坐标
	 * @param ey 结束y坐标
	 * @return
	 * @throws IOException
	 */
	public abstract BufferedImage clip(InputStream in, int bx, int by, int ex,
			int ey) throws IOException;

	/**
	 * 获取支持的图片mime类型
	 * @return
	 */
	public abstract String[] getReaderMIMETypes();

	/**
	 * 获取支持的图片类型名称
	 * @return
	 */
	public abstract String[] getReaderFormatNames();

	/**
	 * 是否可以处理指定的mime类型图片
	 * @param mimeType
	 * @return
	 */
	public abstract boolean canReadForMIMEType(String mimeType);

	/**
	 * 是否可以处理该后缀的图片文件
	 * @param suffix
	 * @return
	 */
	public abstract boolean canReadForSuffix(String suffix);

	/**
	 * 等比例缩小
	 * @param in
	 * @param scale 缩放比列
	 * @return
	 * @throws IOException
	 */
	public abstract BufferedImage scaleZoomIn(InputStream in, int scale)
			throws IOException;

	/**
	 * 等比例放大
	 * @param in
	 * @param scale 放大比列
	 * @return
	 * @throws IOException
	 */
	public abstract BufferedImage scaleZoomOut(InputStream in, int scale)
			throws IOException;

	/**
	 * 缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcImageFile
	 *            源图像
	 * @param result
	 *            缩放后的图像地址
	 * @param height
	 *            缩放后的高度
	 * @param width
	 *            缩放后的宽度
	 * @param bb
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 */
	public abstract BufferedImage scale(BufferedImage src, int height,
			int width, boolean backWhite);
	
	
	public abstract BufferedImage scale(BufferedImage src, int scale,
		 	boolean inOrOut) throws IOException;
	/**
	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 * @param srcImageFile 源图像地址
	 * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
	 * @param destImageFile 目标图像地址
	 */
	public abstract void convert(String srcImageFile, String formatName,
			String destImageFile);
	
	/**
	 * 彩色转为黑白
	 * @param srcImageFile 源图像地址
	 * @param destImageFile 目标图像地址
	 */
	public abstract void gray(String srcImageFile, String destImageFile);

	/**
	 * 给图片添加文字水印
	 * @param srcImageFile 原图
	 * @param pressText 添加的文字
	 * @param fontName 水印的字体名称
	 * @param fontStyle 水印的字体样式
	 * @param color 水印的字体颜色
	 * @param fontSize 水印的字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 * @return
	 */
	public abstract BufferedImage waterText(BufferedImage srcImageFile,
			String pressText, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha);

	/**
	 * 给图片添加图片水印
	 * @param src 源图像地址
	 * @param waterImage 水印图片
	 * @param x 修正值。 默认在中间
	 * @param y 修正值。 默认在中间
	 * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 * @return
	 */
	public abstract BufferedImage waterImage(BufferedImage src,
			BufferedImage waterImage, int x, int y, float alpha);

}