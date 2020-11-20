package com.ceshi.study.test;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: SendMailUtils
 * @Author: shenyafei
 * @Date: 2020/11/11
 * @Desc
 **/
@Slf4j
public class SendMailUtils {


    static {
        System.setProperty("mail.mime.splitlongparameters","false");
        System.setProperty("mail.mime.charset","UTF-8");
    }

    /**
     * 发送带附件的邮件
     *
     * @param receiveAddressList  收件人数组
     * @param copyAddressList  抄送人数组
     * @param subject  邮件主题
     * @param msg      邮件内容
     * @param attachmentPath 附件地址 比如：C:/哈哈.xlsx
     * @param attachmentName 附件名称 比如：哈哈.xlsx
     * @return
     */
    public static boolean sendMail2(List<String> receiveAddressList, List<String> copyAddressList, String subject, String msg, String attachmentPath, String attachmentName){
        if (CollectionUtils.isEmpty(receiveAddressList)) {
            return false;
        }
        // 发件人电子邮箱
        final String from = "";//TODO 账号需自备
        // 发件人电子邮箱密码
        final String pass = "";//TODO 账号密码需自备
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
                // Set To: 头部头字段
                message.addRecipients(Message.RecipientType.CC, ccAddress);
            }
            // Set Subject: 主题文字
            message.setSubject(subject,"UTF-8");
            // 创建消息部分
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            // 消息
            messageBodyPart.setContent(msg,"text/html; charset=utf-8");
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);
            // 附件部分
            messageBodyPart = new MimeBodyPart();
            // 设置要发送附件的文件路径
          /*  DataHandler dataHandler3 = new DataHandler(new FileDataSource(attachmentPath));
            messageBodyPart.setDataHandler(dataHandler3);*/
          if (!StringUtils.isEmpty(attachmentPath)) {
              DataHandler dataHandler3 = new DataHandler(new URLDataSource(new URL(attachmentPath)));
              messageBodyPart.setDataHandler(dataHandler3);
              // 处理附件名称中文（附带文件路径）乱码问题
              log.info("===名称前：" + attachmentName);
              String filenames= MimeUtility.encodeText(attachmentName);
              filenames=filenames.replace("\\r","").replace("\\n","");
              log.info("===名称后："+filenames);
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
            log.error("邮件发送异常：",e);
        }
        return false;
    }

    /***
     * 发送一个html格式文本内容的邮件
     */
    public static void sendHtmlEmail(List<String> receiveAddressList, List<String> copyAddressList){
        StringBuffer strsTcontent = new StringBuffer();
        strsTcontent.append("<table border='1' cellspacing='0' cellpadding='0' >" +
                "<tr>" +
                "<th colspan=4 style='background-color:#87CEFF;padding:2px;' >" +
                "抽奖名单" +
                "</th>" +
                "</tr>");
        strsTcontent.append(
                "<tr style='background-color:#FFDAB9;padding:2px;' >" +
                        "<th align='center' style='padding:2px;' >手机号</th>" +
                        "<th align='center' style='padding:2px;' >姓名</th>"+
                        "<th align='center' style='padding:2px;' >中奖金额（元）</th>"+
                        "<th align='center' style='padding:2px;' >抽奖时间</th>"+
                        "</tr>");
        //background-color:#f6fff3;
        strsTcontent.append(
                "<tr style='' >" +
                        "<td style='padding:2px;'>" +
                        ""+"13011110000" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"张三" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"1111" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"2020-11-11 12:40:53" +
                        "</tr>");
        strsTcontent.append(
                "<tr style='background-color:#f6fff3;' >" +
                        "<td style='padding:2px;'>" +
                        ""+"13011110000" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"张三" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"1111" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"2020-11-11 12:40:53" +
                        "</tr>");
        strsTcontent.append(
                "<tr style='' >" +
                        "<td style='padding:2px;'>" +
                        ""+"13011110000" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"张三" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"1111" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"2020-11-11 12:40:53" +
                        "</tr>");
        strsTcontent.append(
                "<tr style='background-color:#f6fff3;' >" +
                        "<td style='padding:2px;'>" +
                        ""+"13011110000" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"张三" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"1111" +
                        "</td>" +
                        "<td style='padding:2px;'>" +
                        ""+"2020-11-11 12:40:53" +
                        "</tr>");
        strsTcontent.append("</table>");
        sendMail2(receiveAddressList,copyAddressList,"测试邮件",strsTcontent.toString(),"","");
    }

    /****
     * 邮件发送针对springboot框架有依赖的jar包
     * <dependency>
     * 			<groupId>org.springframework.boot</groupId>
     * 			<artifactId>spring-boot-starter-mail</artifactId>
     * </dependency>
     * 配置文件yml中添加下面配置：
     * spring:
     *   mail:
     *     host: smtp.exmail.qq.com
     *     username: 邮箱账号
     *     password: 密码
     *     port: 465
     *     properties:
     *       mail:
     *         smtp:
     *           auth: true
     *           socketFactory:
     *             port: 465
     *             class: javax.net.ssl.SSLSocketFactory
     *             fallback: false
     *           ssl:
     *             enable: true
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        sendMail2(Arrays.asList(""),new ArrayList<>(),"张三-201010101-AMH健康报告","张三的文本","https://cos.shuimuglobal.com/static/AmhReport/pdf-10.pdf","aaa.pdf");
        //sendHtmlEmail(Arrays.asList(""),new ArrayList<>());
    }

}
