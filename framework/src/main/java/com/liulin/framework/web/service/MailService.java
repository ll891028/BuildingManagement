package com.liulin.framework.web.service;

import com.liulin.common.utils.StringUtils;
import com.liulin.framework.web.domain.MailDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @Author: LinLiu
 * @Date: 2021/8/19 3:05 下午
 */
@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String mailFrom;

    public void sendSimpleMail(){
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("thanks");
        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom(mailFrom);
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

    public void sendMail(MailDomain mailDomain){
        // 构建一个邮件对象
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 设置邮件主题
            helper.setSubject(mailDomain.getSubject());
            // 设置邮件发送者，这个跟application.yml中设置的要一致
            helper.setFrom(mailFrom);
            // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
            // message.setTo("10*****16@qq.com","12****32*qq.com");
            if(StringUtils.isNotEmpty(mailDomain.getReceiver())) {
                helper.setTo(mailDomain.getReceiver().split(","));
            }
//        // 设置邮件抄送人，可以有多个抄送人
//        message.setCc("12****32*qq.com");
//        // 设置隐秘抄送人，可以有多个
            if(StringUtils.isNotEmpty(mailDomain.getBcc())){
                helper.setBcc(mailDomain.getBcc().split(","));
            }
//            helper.setBcc( new String[]{"liulintx@whty.com.cn"});
            // 设置邮件发送日期
            helper.setSentDate(new Date());
            // 设置邮件的正文
            helper.setText(mailDomain.getContent(),true);
            // 发送邮件
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
