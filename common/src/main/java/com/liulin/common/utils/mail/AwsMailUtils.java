package com.liulin.common.utils.mail;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.liulin.common.config.LiulinConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class AwsMailUtils {

    private static final Logger log = LoggerFactory.getLogger(AwsMailUtils.class);

    private final static Regions clientRegion = Regions.AP_SOUTHEAST_2;




    public static void sesSend() throws IOException {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(LiulinConfig.getAwsKeyId(), LiulinConfig.getAwsSecretId());
        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                            .withRegion(clientRegion).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses("ll439637373@163.com"))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData("111111"))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData("123123")))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData("test")))
                    .withSource("sender@broxy.com.au");
                    // Comment or remove the next line if you are not using a
                    // configuration set
//                    .withConfigurationSetName(CONFIGSET);
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }

    public static Boolean smtpSend(String sendAddress,String toAddress,String senderName,String subject,String content,String attachmentUrl,String fileName) throws Exception {

        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(sendAddress,senderName));
        msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toAddress));
        msg.setSubject(subject);
//        msg.setContent(content,"text/html");
        //添加附件部分
        //邮件内容部分1---文本内容
        MimeBodyPart body0 = new MimeBodyPart(); //邮件中的文字部分
        body0.setContent(content,"text/html;charset=utf-8");
        //邮件内容部分2---附件1
        MimeBodyPart body1 = new MimeBodyPart(); //附件1
        if(attachmentUrl.contains("http")){
            body1.setDataHandler( new DataHandler( new URL(attachmentUrl)) );//./代表项目根目录下
        }else{
            body1.setDataHandler( new DataHandler( new FileDataSource(attachmentUrl)) );//./代表项目根目录下
        }

        body1.setFileName( MimeUtility.encodeText(fileName) );
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(body0);
        mm.addBodyPart(body1);
        msg.setContent(mm);
        // Add a configuration set header. Comment or delete the
        // next line if you are not using a configuration set
//        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try
        {
            log.info("Sending mail From:{},to:{}",sendAddress,toAddress);
            String userName = LiulinConfig.getAwsMailUserName();
            String password = LiulinConfig.getAwsMailPassword();
            // Connect to Amazon SES using the SMTP username and password you specified above.
//            transport.connect("email-smtp."+Regions.AP_SOUTHEAST_2.getName()+".amazonaws.com", LiulinConfig.getAwsMailUserName(), LiulinConfig.getAwsMailPassword());
            transport.connect("email-smtp.ap-southeast-2.amazonaws.com", userName, password);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            log.info("Email sent!");
            return true;
        }
        catch (Exception ex) {
            log.info("Email send failure");
            log.info("Error message: " + ex.getMessage());
            return false;
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }

}
