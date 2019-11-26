package com.ceshi.study.util;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: SendEmailUtils
 * @Author: shenyafei
 * @Date: 2019/11/26
 * @Desc
 **/
public class SendEmailUtils {

    static {
        //解决附件名称乱码
        System.setProperty("mail.mime.splitlongparameters","false");
        System.setProperty("mail.mime.charset","UTF-8");
    }

    /**
     *@Description: 只适合腾讯qq或腾讯企业邮箱发送邮件
     *@Param:  receiveAddressList 收件人数组
     *@Param:  copyAddressList 抄送人数组
     *@Param:  subject 邮件标题
     *@Param:   msg  正文
     *@Param:   filePath  附件地址
     *@Param:   fileName 附件名称
     *@return:  boolean true:成功 false:失败
     *@Date 2019/11/26 18:01
     *@Author: shenyafei
     */
    public static boolean sendMail(List<String> receiveAddressList, List<String> copyAddressList, String subject, String msg, String filePath, String fileName) {
        if (CollectionUtils.isEmpty(receiveAddressList)) {
            return false;
        }
        // 发件人电子邮箱
        final String from = "shenyafei@labifenqi.com";
        // 发件人电子邮箱密码
        final String pass = "";
        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.exmail.qq.com";
        try {
            // 获取系统属性
            Properties properties = System.getProperties();
            // 设置邮件服务器
            properties.setProperty("mail.smtp.host", host);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", 465);
            properties.put("mail.smtp.ssl.enable", true);
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.socketFactory",sf);
            //获取默认session对象 //qq邮箱服务器账户、第三方登录授权码
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, pass); // 发件人邮件用户名、密码
                }
            });
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            InternetAddress[] hhAddress = new InternetAddress[receiveAddressList.size()];
            for (int i = 0; i < receiveAddressList.size(); i++) {
                if(!StringUtils.isEmpty(receiveAddressList.get(i))){
                    hhAddress[i] = new InternetAddress(receiveAddressList.get(i));
                }
            }
            message.addRecipients(Message.RecipientType.TO, hhAddress);
            if(!CollectionUtils.isEmpty(copyAddressList)){
                InternetAddress[] ccAddress = new InternetAddress[copyAddressList.size()];
                for (int i = 0; i < copyAddressList.size(); i++) {
                    if(!StringUtils.isEmpty(copyAddressList.get(i))){
                        ccAddress[i] = new InternetAddress(copyAddressList.get(i));
                    }
                }
                // Set To: 头部抄送人
                message.addRecipients(Message.RecipientType.CC, ccAddress);
            }
            // Set Subject: 主题文字
            message.setSubject(subject,"UTF-8");
            // 创建消息部分
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            // 消息
            messageBodyPart.setText(msg);
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);
            // 附件部分
            if (!StringUtils.isEmpty(filePath) && !StringUtils.isEmpty(fileName)) {
                messageBodyPart = new MimeBodyPart();
                // 设置要发送附件的文件路径
                DataHandler dataHandler3 = new DataHandler(new FileDataSource(filePath));
                messageBodyPart.setDataHandler(dataHandler3);
                // 处理附件名称中文（附带文件路径）乱码问题
                System.out.println("===名称前：" + fileName);
                String filenames= MimeUtility.encodeText(fileName);
                filenames=filenames.replace("\\r","").replace("\\n","");
                System.out.println("===名称后："+filenames);
                messageBodyPart.setFileName(filenames);
                multipart.addBodyPart(messageBodyPart);
            }
            // 发送完整消息
            message.setContent(multipart);
            message.saveChanges();
            // 发送消息
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            System.out.println("邮件发送异常："+e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args){
        List<String> receiveAddressList = new ArrayList<>();
        receiveAddressList.add("522408323@qq.com");
        List<String> copyAddressList = new ArrayList<>();
        String subject = "测试主题";
        String msg = "测试内容";
        String filePath = "";
        String fileName = "";
        System.out.println("======="+sendMail(receiveAddressList, copyAddressList, subject, msg, filePath, fileName));
    }

}
