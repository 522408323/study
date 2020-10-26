package com.ceshi.study.util;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

/**
 * @ClassName TestPosterUtil
 * @Description
 * @Author linzhixiong
 * @Date 2020/7/7 11:01
 **/
public class TestPosterUtil {


    public static void test_cut_image(String imgPath, String subPath) throws IOException {
        //String imgPath = "C:\\Users\\GJ\\Desktop\\1.jpg";
        //String subPath = "C:\\Users\\GJ\\Desktop\\2.jpg";

        File f = new File(imgPath);
        File t = new File(subPath);
        if (t.exists()) {
            t.delete();
        }

        //图片输入流
        ImageInputStream iis = ImageIO.createImageInputStream(f);

        //图片读取器
        Iterator<ImageReader> it = ImageIO.getImageReaders(iis);

        if (it.hasNext()) {
            ImageReader r = it.next();

            //设置输入流
            r.setInput(iis, true);
            System.out.println("格式=" + r.getFormatName());
            System.out.println("宽=" + r.getWidth(0));
            System.out.println("高=" + r.getHeight(0));

            //读取参数
            ImageReadParam param = r.getDefaultReadParam();

            //创建要截取的矩形范围
            Rectangle rect = new Rectangle(0, 0, 255, 100);

            //设置截取范围参数
            param.setSourceRegion(rect);

            //读取截图数据
            BufferedImage bi = r.read(0, param);
            //bi = transparentImage(bi,100);
           /* Graphics2D g2d = bi.createGraphics();
            bi = g2d.getDeviceConfiguration().createCompatibleImage(255, 100, Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = bi.createGraphics();
            g2d.dispose();*/
            // 保存图片
            ImageIO.write(bi, "png", t);
        }
    }

    /**
     * 图片压缩
     * @param filePath 原文件路径
     * @param w 压缩后宽度
     * @param h 压缩后高度
     * @return
     */
    public static void imgCompress(String filePath, String distPath, int w, int h) {
        try {
            File file = new File(filePath);// 读入文件
            BufferedImage img = ImageIO.read(file);
            int width = img.getWidth(null);    // 得到源图宽
            int height = img.getHeight(null);  // 得到源图长

            if (width < w) {
                w = width;
            }
            if (height < h) {
                h = height;
            }
            // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
            BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );

            image.getGraphics().drawImage(image, 0, 0, w, h, null); // 绘制缩小后的图

            File destFile = new File(distPath);
            FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
            //可以正常实现bmp、png、gif转jpg
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image); // JPEG编码
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置源图片为背景透明，并设置透明度
     * @param srcImage 源图片
     * @param alpha 透明度
     * @throws IOException
     */
    public static BufferedImage transparentImage(BufferedImage srcImage, int alpha) throws IOException {
        int imgHeight = srcImage.getHeight();//取得图片的长和宽
        int imgWidth = srcImage.getWidth();
        int c = srcImage.getRGB(3, 3);
        //防止越位
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 10) {
            alpha = 10;
        }
        BufferedImage bi = new BufferedImage(imgWidth, imgHeight,
                BufferedImage.TYPE_4BYTE_ABGR);//新建一个类型支持透明的BufferedImage
        for(int i = 0; i < imgWidth; ++i)//把原图片的内容复制到新的图片，同时把背景设为透明
        {
            for(int j = 0; j < imgHeight; ++j)
            {
                //把背景设为透明
                if(srcImage.getRGB(i, j) == c){
                    bi.setRGB(i, j, c & 0x00ffffff);
                }
                //设置透明度
                else{
                    int rgb = bi.getRGB(i, j);
                    rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);
                    bi.setRGB(i, j, rgb);
                }
            }
        }
        return bi;
    }
    /**
     * 裁剪PNG图片工具类
     *
     * @param fromFile
     *            源文件
     * @param toFile
     *            裁剪后的文件
     * @param outputWidth
     *            裁剪宽度
     * @param outputHeight
     *            裁剪高度
     * @param proportion
     *            是否是等比缩放
     */
    public static void resizePng(File fromFile, File toFile, int outputWidth, int outputHeight,
                                 boolean proportion) {
        try {
            BufferedImage bi2 = ImageIO.read(fromFile);
            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            if (proportion) {
                // 为等比缩放计算输出的图片宽度及高度
                double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth + 0.1;
                double rate2 = ((double) bi2.getHeight(null)) / (double) outputHeight + 0.1;
                // 根据缩放比率大的进行缩放控制
                double rate = rate1 < rate2 ? rate1 : rate2;
                newWidth = (int) (((double) bi2.getWidth(null)) / rate);
                newHeight = (int) (((double) bi2.getHeight(null)) / rate);
            } else {
                newWidth = outputWidth; // 输出的图片宽度
                newHeight = outputHeight; // 输出的图片高度
            }
            BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = to.createGraphics();
            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight,
                    Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = to.createGraphics();
            @SuppressWarnings("static-access")
            Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();
            ImageIO.write(to, "png", toFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //test_cut_image("C:\\Users\\lixip\\Pictures\\1312.jpg", "C:\\Users\\lixip\\Pictures\\1313.jpg");
        test_cut_image("C:\\Users\\lixip\\Pictures\\111.png", "C:\\Users\\lixip\\Pictures\\222.png");
        //imgCompress("C:\\Users\\lixip\\Pictures\\dd.jpg","C:\\Users\\lixip\\Pictures\\yy.jpg",300,500);
        //resizePng(new File("C:\\Users\\lixip\\Pictures\\111.png"), new File("C:\\Users\\lixip\\Pictures\\222.png"), 255, 100, false);
    }
}
