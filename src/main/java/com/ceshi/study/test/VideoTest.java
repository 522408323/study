package com.ceshi.study.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @ClassName: VideoTest
 * @Author: shenyafei
 * @Date: 2020/9/24
 * @Desc
 **/

public class VideoTest {
    /*public static void main(String[] args) {
        try {
            getTempPath("D:/rrrrr.jpg", "D:/rrr.mp4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public static String getTempPath(String tempPath, String filePath) throws Exception {
//        String tempPath="    ";//保存的目标路径
        File targetFile = new File(tempPath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        File file2 = new File(filePath);
        if (file2.exists()) {
            System.out.println("文件存在，路径正确！");
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file2);
            ff.start();
            int ftp = ff.getLengthInFrames();
            int flag=0;
            Frame frame = null;
            while (flag <= ftp) {
                //获取帧
                frame = ff.grabImage();
                //过滤前3帧，避免出现全黑图片
                if ((flag>3)&&(frame != null)) {
                    break;
                }
                flag++;
            }
            ImageIO.write(FrameToBufferedImage(frame), "jpg", targetFile);
            ff.close();
            ff.stop();
        } else {
            System.out.println("文件不存在");
        }
        return null;
    }
    private static RenderedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }

    public static String getBase64(String filePath) throws Exception {
        String base64 = "";
        File file2 = new File(filePath);
        if (file2.exists()) {
            System.out.println("文件存在，路径正确！");
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file2);
            ff.start();
            int ftp = ff.getLengthInFrames();
            int flag=0;
            Frame frame = null;
            while (flag <= ftp) {
                //获取帧
                frame = ff.grabImage();
                //过滤前3帧，避免出现全黑图片
                if ((flag>3)&&(frame != null)) {
                    break;
                }
                flag++;
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(FrameToBufferedImage(frame), "jpg", stream);
            base64 = Base64.encodeBase64String(stream.toByteArray());
            if(!StringUtils.isEmpty(base64)) {
                base64 = "data:image/jpg;base64," + base64;
            }
            ff.close();
            ff.stop();
        } else {
            System.out.println("没有文件");
        }
        return base64;
    }

    public static List<Integer> getAllYYYYMMDate(Date beginDate, Date endDate){
        List<Integer> list = new ArrayList<>();
        Integer begin = Integer.valueOf(getDateTime(beginDate,"yyyyMM"));
        Integer end = Integer.valueOf(getDateTime(endDate,"yyyyMM"));
        while (beginDate.getTime() <= endDate.getTime()) {
            System.out.println("---"+begin+"-----"+getDateTime(beginDate,"yyyy-MM-dd"));
            list.add(begin);
            beginDate = DateUtils.addDays(beginDate,1);
            begin = Integer.valueOf(getDateTime(beginDate,"yyyyMM"));

        }
        return list;
    }

    /**
     * 获取指定日期的指定的格式的字符串格式
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateTime(Date date , String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);

    }

    /***
     * 根据url获取文件流
     * @param strUrl
     * @return
     */
    public static ByteArrayInputStream getInputStreamByUrl(String strUrl){
        HttpURLConnection conn = null;
        ByteArrayOutputStream output = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(),output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if (output != null) {
                    output.flush();
                    output.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                if (conn != null) {
                    conn.disconnect();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getTempPathNew(String tempPath, String filePath) throws Exception {
        File targetFile = new File(tempPath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        InputStream in = getInputStreamByUrl(filePath);
        if (in != null) {
            System.out.println("文件存在，路径正确！");
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(in);
            ff.start();
            int ftp = ff.getLengthInFrames();
            int flag=0;
            Frame frame = null;
            while (flag <= ftp) {
                //获取帧
                frame = ff.grabImage();
                //过滤前3帧，避免出现全黑图片
                if ((flag>3)&&(frame != null)) {
                    break;
                }
                flag++;
            }
            ImageIO.write(FrameToBufferedImage(frame), "jpg", targetFile);
            ff.close();
            ff.stop();
        } else {
            System.out.println("文件不存在");
        }
        if (in != null) {
            in.close();
        }
        return null;
    }

    public static String getBase64New(String filePath){
        String base64 = "";
        FFmpegFrameGrabber ff = null;
        ByteArrayOutputStream stream = null;
        InputStream in = null;
        try {
            System.out.println("视频源路径：{}"+filePath);
            in = getInputStreamByUrl(filePath);
            if (in != null) {
                System.out.println("文件存在，路径正确！111");
                ff = new FFmpegFrameGrabber(in);
                System.out.println("=====000=======");
                ff.start();
                int ftp = ff.getLengthInFrames();
                System.out.println("=====01111=======");
                int flag=0;
                Frame frame = null;
                System.out.println("=====0=======");
                while (flag <= ftp) {
                    //获取帧
                    frame = ff.grabImage();
                    //过滤前3帧，避免出现全黑图片
                    if ((flag>3)&&(frame != null)) {
                        break;
                    }
                    flag++;
                }
                System.out.println("=====1=======");
                stream = new ByteArrayOutputStream();
                ImageIO.write(FrameToBufferedImage(frame), "jpg", stream);
                System.out.println("=====2=======");
                base64 = Base64.encodeBase64String(stream.toByteArray());
                System.out.println("=====3=======");
                if(!StringUtils.isEmpty(base64)) {
                    base64 = "data:image/jpg;base64," + base64;
                }
            } else {
                System.out.println("源路径【{}】的视频不存在"+filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ff != null) {
                    ff.close();
                    ff.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return base64;
    }
    public static void main(String[] args) {
        try {
            //getTempPath("D:\\test.jpg", "D:\\rrr.mp4");
           // System.out.println(getBase64( "D:\\rrr.mp4"));
           /* Date beginDate = DateUtils.addDays(new Date(), -50);
            List<Integer> timeList = getAllYYYYMMDate(beginDate,new Date());
            timeList.stream().forEach(e->{
                System.out.println(e);
            });
            System.out.println("--------------------------------------------");
            timeList = timeList.stream().distinct().collect(Collectors.toList());
            timeList.stream().forEach(e->{
                System.out.println(e);
            });*/

            //getTempPathNew("D:\\test222.jpg", "https://cos.shuimuglobal.com/create_date/2020/10/21/17/fGLqyGUSzHYoTW0JUMB.mp4");
            System.out.println(getBase64New( "https://cos.shuimuglobal.com/create_date/2020/10/21/17/fGLqyGUSzHYoTW0JUMB.mp4"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
