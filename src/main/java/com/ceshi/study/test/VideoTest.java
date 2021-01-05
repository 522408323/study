package com.ceshi.study.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import ws.schild.jave.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.Encoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
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


    public static File compressionVideo(File source,String newPathName) {
        if(source == null){
            return null;
        }
        File target = new File(newPathName);
        try {
            MultimediaObject object = new MultimediaObject(source);
            AudioInfo audioInfo = object.getInfo().getAudio();
            // 根据视频大小来判断是否需要进行压缩,
            int maxSize = 100;
            double mb = Math.ceil(source.length()/ 1048576);
            int second = (int)object.getInfo().getDuration()/1000;
            BigDecimal bd = new BigDecimal(String.format("%.4f", mb/second));
            System.out.println("开始压缩视频了--> 视频每秒平均 "+ bd +" MB ");
            // 视频 > 100MB, 或者每秒 > 0.5 MB 才做压缩， 不需要的话可以把判断去掉
            boolean temp = mb > maxSize || bd.compareTo(new BigDecimal(0.5)) > 0;
            if(temp){
                long time = System.currentTimeMillis();
                //TODO 视频属性设置
                int maxBitRate = 128000;
                int maxSamplingRate = 44100;
                int bitRate = 800000;
                int maxFrameRate = 20;
                int maxWidth = 1280;

                AudioAttributes audio = new AudioAttributes();
                // 设置通用编码格式
                audio.setCodec("aac");
                // 设置最大值：比特率越高，清晰度/音质越好
                // 设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 128000 = 182kb)
                if(audioInfo.getBitRate() > maxBitRate){
                    audio.setBitRate(new Integer(maxBitRate));
                }

                // 设置重新编码的音频流中使用的声道数（1 =单声道，2 = 双声道（立体声））。如果未设置任何声道值，则编码器将选择默认值 0。
                audio.setChannels(audioInfo.getChannels());
                // 采样率越高声音的还原度越好，文件越大
                // 设置音频采样率，单位：赫兹 hz
                // 设置编码时候的音量值，未设置为0,如果256，则音量值不会改变
                // audio.setVolume(256);
                if(audioInfo.getSamplingRate() > maxSamplingRate){
                    audio.setSamplingRate(maxSamplingRate);
                }

                //TODO 视频编码属性配置
                VideoInfo videoInfo = object.getInfo().getVideo();
                VideoAttributes video = new VideoAttributes();
                video.setCodec("h264");
                //设置音频比特率,单位:b (比特率越高，清晰度/音质越好，当然文件也就越大 800000 = 800kb)
                if(videoInfo.getBitRate() > bitRate){
                    video.setBitRate(bitRate);
                }

                // 视频帧率：15 f / s  帧率越低，效果越差
                // 设置视频帧率（帧率越低，视频会出现断层，越高让人感觉越连续），视频帧率（Frame rate）是用于测量显示帧数的量度。所谓的测量单位为每秒显示帧数(Frames per Second，简：FPS）或“赫兹”（Hz）。
                if(videoInfo.getFrameRate() > maxFrameRate){
                    video.setFrameRate(maxFrameRate);
                }

                // 限制视频宽高
                int width = videoInfo.getSize().getWidth();
                int height = videoInfo.getSize().getHeight();
                if(width > maxWidth){
                    float rat = (float) width / maxWidth;
                    video.setSize(new VideoSize(maxWidth,(int)(height/rat)));
                }

                EncodingAttributes attr = new EncodingAttributes();
                attr.setFormat("mp4");
                attr.setAudioAttributes(audio);
                attr.setVideoAttributes(video);

                // 速度最快的压缩方式， 压缩速度 从快到慢： ultrafast, superfast, veryfast, faster, fast, medium,  slow, slower, veryslow and placebo.
//                attr.setPreset(PresetUtil.VERYFAST);
//                attr.setCrf(27);
//                // 设置线程数
//                attr.setEncodingThreads(Runtime.getRuntime().availableProcessors()/2);

                ws.schild.jave.Encoder encoder = new ws.schild.jave.Encoder();
                encoder.encode(new MultimediaObject(source), target, attr);
                System.out.println("压缩总耗时：" + (System.currentTimeMillis() - time)/1000);
                return target;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(target.length() > 0){
                source.delete();
            }
        }
        return source;
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
            //System.out.println(getBase64New( "https://cos.shuimuglobal.com/create_date/2020/10/21/17/fGLqyGUSzHYoTW0JUMB.mp4"));
            compressionVideo(new File("D://www.mp4"),"D://www2.mp4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
