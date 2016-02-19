package cn.chamatou.commons.data.image;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
/**
 * 图片工具
 *
 */
public  class DefaultImageTool extends ImageTool {
	/* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#clip(java.io.InputStream, int, int, int, int)
	 */
	@Override
	public  BufferedImage clip(InputStream in, int bx, int by,
			int ex, int ey) throws IOException {
		BufferedImage srcImg = ImageIO.read(in);
		return clip(srcImg, bx, by, ex, ey);
	}
	/**
	 * 图片剪裁
	 * @param imgFile 图片文件 
	 * @param bx 开始x坐标
	 * @param by 开始y坐标
	 * @param ex 结束x坐标
	 * @param ey 结束y坐标
	 * @return
	 * @throws IOException
	 */
	public  BufferedImage clip(File imgFile, int bx, int by,
			int ex, int ey) throws IOException {
		BufferedImage srcImg = ImageIO.read(imgFile);
		return clip(srcImg, bx, by, ex, ey);
	}
	/**
	 * 图片剪裁
	 * @param srcImg 图片文件
	 * @param bx 开始x坐标
	 * @param by 开始y坐标
	 * @param ex 结束x坐标
	 * @param ey 结束y坐标
	 * @return
	 */
	public  BufferedImage clip(BufferedImage srcImg, int bx, int by,
			int ex, int ey) {
		BufferedImage distImg = new BufferedImage(ex - bx, ey - by,
				srcImg.getType());
		Graphics2D g2d = (Graphics2D) distImg.getGraphics();
		g2d.drawImage(srcImg, 0, 0, distImg.getWidth(), distImg.getHeight(),
				bx, by, ex, ey, null);
		g2d.dispose();
		return distImg;
	}
	/* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#getReaderMIMETypes()
	 */
	@Override
	public  String[] getReaderMIMETypes() {
		return ImageIO.getReaderMIMETypes();
	}
	/* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#getReaderFormatNames()
	 */
	@Override
	public  String[] getReaderFormatNames() {
		return ImageIO.getReaderFormatNames();
	}
	/* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#canReadForMIMEType(java.lang.String)
	 */
	@Override
	public  boolean canReadForMIMEType(String mimeType) {
		return imageCanRead(getReaderMIMETypes(), mimeType);
	}
	/* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#canReadForSuffix(java.lang.String)
	 */
	@Override
	public  boolean canReadForSuffix(String suffix) {
		return imageCanRead(getReaderFormatNames(), suffix);
	}
	
	private  boolean imageCanRead(String[] types, String inType) {
		boolean canRead = false;
		for (String type : types) {
			if (type.equalsIgnoreCase(inType)) {
				canRead = true;
			}
		}
		return canRead;
	}
	/* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#scaleZoomIn(java.io.InputStream, int)
	 */
	@Override
	public  BufferedImage scaleZoomIn(InputStream in, int scale)
			throws IOException {
		BufferedImage src = ImageIO.read(in);
		return scale(src, scale, false);
	}
	/* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#scaleZoomOut(java.io.InputStream, int)
	 */
	@Override
	public  BufferedImage scaleZoomOut(InputStream in, int scale)
			throws IOException {
		BufferedImage src = ImageIO.read(in);
		return scale(src, scale, true);
	}
	@Override
	public  BufferedImage scale(BufferedImage src, int scale,
			boolean inOrOut) throws IOException {
		int width = src.getWidth(); // 得到源图宽
		int height = src.getHeight(); // 得到源图长
		if (inOrOut) {// 放大
			width = width * scale;
			height = height * scale;
		} else {// 缩小
			width = width / scale;
			height = height / scale;
		}
		Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		return tag;
	}

	/* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#scale(java.awt.image.BufferedImage, int, int, boolean)
	 */
	@Override
	public  BufferedImage scale(BufferedImage src, int height, int width,
			boolean backWhite) {
		double ratio = 0.0; // 缩放比例

		Image itemp = src.getScaledInstance(width, height,
				BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((src.getHeight() > height) || (src.getWidth() > width)) {
			if (src.getHeight() > src.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / src.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / src.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(
					AffineTransform.getScaleInstance(ratio, ratio), null);
			itemp = op.filter(src, null);
		}
		if (backWhite) {// 补白
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			if (width == itemp.getWidth(null))
				g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
						itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
			else
				g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
						itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
			g.dispose();
			itemp = image;
		}
		return (BufferedImage) itemp;
	}
	  /* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#convert(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public  void convert(String srcImageFile, String formatName, String destImageFile) {
        try {
            File f = new File(srcImageFile);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#gray(java.lang.String, java.lang.String)
	 */
    @Override
	public  void gray(String srcImageFile, String destImageFile) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(destImageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#waterText(java.awt.image.BufferedImage, java.lang.String, java.lang.String, int, java.awt.Color, int, int, int, float)
	 */
    @Override
	public BufferedImage waterText(BufferedImage srcImageFile,String pressText,String fontName,
            int fontStyle, Color color, int fontSize,int x,
            int y, float alpha) {
    	int width=srcImageFile.getWidth(null);
    	int height=srcImageFile.getHeight(null);
    	BufferedImage image = new BufferedImage(width,height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d=image.createGraphics();
        g2d.drawImage(srcImageFile,0,0,width,height,null);
        g2d.setColor(color);
        g2d.setFont(new Font(fontName,fontStyle,fontSize));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        // 在指定坐标绘制水印文字
        g2d.drawString(pressText, (width - (getLength(pressText) * fontSize))
                / 2 + x, (height - fontSize) / 2 + y);
        g2d.dispose();
        return image;
    }
    /* (non-Javadoc)
	 * @see org.sourceforge.commons.image.utils.ImageUtils#waterImage(java.awt.image.BufferedImage, java.awt.image.BufferedImage, int, int, float)
	 */
    @Override
	public BufferedImage waterImage(BufferedImage src,BufferedImage waterImage,
            int x, int y, float alpha) {
    	int width=src.getWidth(null);
    	int height=src.getHeight(null);
    	BufferedImage image = new BufferedImage(width,height,
                BufferedImage.TYPE_INT_RGB);
    	Graphics2D g = image.createGraphics();
        g.drawImage(src, 0, 0, width, height, null);
        int waterWidth=waterImage.getWidth(null);
        int waterHeight=waterImage.getHeight(null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        g.drawImage(waterImage, (width - waterWidth) / 2,
                (height - waterHeight) / 2, waterWidth, waterHeight, null);
        g.dispose();
        return image;
    }
    /**
     * 计算text的长度（一个中文算两个字符）
     * @param text
     * @return
     */
    private  int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }
}
