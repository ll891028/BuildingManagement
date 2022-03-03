package com.liulin.framework.web.service;

import com.liulin.common.utils.StringUtils;
import com.liulin.common.utils.mail.AwsMailUtils;
import com.liulin.framework.web.domain.MailDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

/**
 * @Author: LinLiu
 * @Date: 2021/8/19 3:05 下午
 */
@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String mailFrom;

    @Value(value = "${spring.mail.password}")
    private String password;

    // 初始化连接邮件服务器的会话信息
    private static Properties props = null;

    static {
        props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "mail.broxy.com.au");
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.debug","true");

    }

    public void sendSimpleMail(){
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("thanks");
        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom("535944322@qq.com");
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("10*****16@qq.com","12****32*qq.com");
        message.setTo("ll439637373@163.com");
//        // 设置邮件抄送人，可以有多个抄送人
//        message.setCc("12****32*qq.com");
//        // 设置隐秘抄送人，可以有多个
//        message.setBcc("7******9@qq.com");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText("这是测试邮件的正文");
        // 发送邮件
        javaMailSender.send(message);
    }

    public boolean sendMail(MailDomain mailDomain){
//        // 构建一个邮件对象
//        MimeMessage message = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            // 设置邮件主题
//            helper.setSubject(mailDomain.getSubject());
//            // 设置邮件发送者，这个跟application.yml中设置的要一致
//            helper.setFrom(mailFrom);
//            // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
//            // message.setTo("10*****16@qq.com","12****32*qq.com");
//            if(StringUtils.isNotEmpty(mailDomain.getReceiver())) {
//                helper.setTo(mailDomain.getReceiver().split(","));
//            }
////        // 设置邮件抄送人，可以有多个抄送人
////        message.setCc("12****32*qq.com");
////        // 设置隐秘抄送人，可以有多个
//            if(StringUtils.isNotEmpty(mailDomain.getBcc())){
//                helper.setBcc(mailDomain.getBcc().split(","));
//            }
////            helper.setBcc( new String[]{"liulintx@whty.com.cn"});
//            // 设置邮件发送日期
//            helper.setSentDate(new Date());
//            // 设置邮件的正文
//            helper.setText(mailDomain.getContent(),true);
//            // 发送邮件
//            javaMailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
        return sendSMTPMailByAws(mailDomain);

    }

    public void sendSMTPMail(MailDomain mailDomain){
        /*
         *用户名和密码
         */
//        String SendUser="sender@broxy.com.au";
//        String SendPassword="123456";
        String ReceiveUser=mailDomain.getReceiver();

        /*
         *对用户名和密码进行Base64编码
         */
        String UserBase64= Base64.getEncoder().encodeToString(mailFrom.getBytes());
        String PasswordBase64=Base64.getEncoder().encodeToString(password.getBytes());

        try {
            /*
             *远程连接smtp.163.com服务器的25号端口
             *并定义输入流和输出流(输入流读取服务器返回的信息、输出流向服务器发送相应的信息)
             */
            Socket socket=new Socket("smtp.163.com", 25);
            InputStream inputStream=socket.getInputStream();//读取服务器返回信息的流
            InputStreamReader isr=new InputStreamReader(inputStream);//字节解码为字符
            BufferedReader br=new BufferedReader(isr);//字符缓冲

            OutputStream outputStream=socket.getOutputStream();//向服务器发送相应信息
            PrintWriter pw=new PrintWriter(outputStream, true);//true代表自带flush
            log.info(br.readLine());

            /*
             *向服务器发送信息以及返回其相应结果
             */

            //helo
            pw.println("helo sender");
            log.info(br.readLine());

            //auth login
            pw.println("auth login");
            log.info(br.readLine());
            pw.println(UserBase64);
            log.info(br.readLine());
            pw.println(PasswordBase64);
            log.info(br.readLine());

            //Set "mail from" and  "rect to"
            pw.println("mail from:<"+mailDomain.getFrom()+">");
            log.info(br.readLine());
            pw.println("rcpt to:<"+ReceiveUser+">");
            log.info(br.readLine());
            if(StringUtils.isNotEmpty(mailDomain.getBcc())){
                String[] bccs = mailDomain.getBcc().split(",");
                 for (String bcc : bccs) {
                    pw.println("rcpt to:<"+bcc+">");
                    log.info(br.readLine());
                }
            }
            //Set "data"
            pw.println("data");
            log.info(br.readLine());

            //正文主体(包括标题,发送方,接收方,内容,点)
            pw.println("subject:"+mailDomain.getSubject());
            pw.println("from:"+mailDomain.getFrom());
            pw.println("to:"+ReceiveUser);
            pw.println("bcc:"+mailDomain.getBcc());
//            pw.println("cc:"+mailDomain.getBcc());
            pw.println("Content-Type: text/html;charset=\"utf-8\"");//设置编码格式可发送中文内容
            pw.println();
            pw.println(mailDomain.getContent());
            pw.println(".");
            pw.print("");
            log.info(br.readLine());

            /*
             *发送完毕,中断与服务器连接
             */
            pw.println("rset");
            log.info(br.readLine());
            pw.println("quit");
            log.info(br.readLine());



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean sendSMTPMailByAws(MailDomain mailDomain) {
        try {
            if(StringUtils.isNotEmpty(mailDomain.getReceiver())){
                AwsMailUtils.smtpSend(mailDomain.getFrom(),mailDomain.getReceiver(),mailDomain.getFrom(),mailDomain.getSubject(),mailDomain.getContent(),mailDomain.getAttachmentUrl(),mailDomain.getFileName());
            }

            if(StringUtils.isNotEmpty(mailDomain.getBcc())){
                String[] bccs = mailDomain.getBcc().split(",");
                for (String bcc : bccs) {
                    AwsMailUtils.smtpSend(mailDomain.getFrom(),bcc,mailDomain.getFrom(),mailDomain.getSubject(),mailDomain.getContent(),mailDomain.getAttachmentUrl(),mailDomain.getFileName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void sendSMTPMailTest(){
        MailDomain mailDomain = new MailDomain();
        mailDomain.setFrom("Broxy.au@gmail.com");
        mailDomain.setReceiver("439637373@qq.com");
        mailDomain.setContent("test content");
        mailDomain.setSubject("test subject");
//        mailDomain.setAttachementUrl("https://gtech-bucket-sy.s3.ap-southeast-2.amazonaws.com/workOrderPdf/SP93087/2022/02/10/WO-202202100003.pdf");
        mailDomain.setAttachmentUrl("C:\\image\\profile\\test.pdf");
        mailDomain.setFileName("test.pdf");
//        mailDomain.setBcc("439637373@qq.com");
        sendSMTPMailByAws(mailDomain);
    }


    /**
     * 发送简单的html邮件
     */
    public void sendHtmlEmail(MailDomain mailDomain) throws Exception {
        // 创建Session实例对象
        Session session = Session.getInstance(props, new MyAuthenticator(mailFrom,password));

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置邮件主题
        message.setSubject("html邮件主题");
        // 设置发送人
        message.setFrom(new InternetAddress(mailDomain.getFrom()));
        // 设置发送时间
        message.setSentDate(new Date());
        //增加BCC功能
        if(StringUtils.isNotEmpty(mailDomain.getBcc())) {
            String[] bccs = mailDomain.getBcc().split(",");
            InternetAddress[] bccAddress = new InternetAddress[bccs.length];
            for(int k = 0;k<bccs.length;k++){
                String emailAddress = bccs[k];
                new InternetAddress(emailAddress);
                bccAddress[k]=new InternetAddress(emailAddress);
            }
            message.addRecipients(MimeMessage.RecipientType.BCC, bccAddress);
        }

        // 设置收件人
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mailDomain.getReceiver()));
        // 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk
        message.setContent(mailDomain.getContent(),"text/html;charset=utf-8");

        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 发送邮件
        Transport.send(message);
    }

    /**
     * 向邮件服务器提交认证信息
     */
    static class MyAuthenticator extends Authenticator {

        @Value(value = "${spring.mail.username}")
        private String username;

        @Value(value = "${spring.mail.password}")
        private String password;

        public MyAuthenticator() {
            super();
        }

        public MyAuthenticator(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(username, password);
        }
    }


}
