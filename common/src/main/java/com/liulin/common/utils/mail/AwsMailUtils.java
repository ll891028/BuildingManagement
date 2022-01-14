package com.liulin.common.utils.mail;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.liulin.common.config.LiulinConfig;
import com.liulin.common.utils.file.AwsFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class AwsMailUtils {

    private static final Logger log = LoggerFactory.getLogger(AwsFileUtils.class);

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

    public static Boolean smtpSend(String sendAddress,String toAddress,String senderName,String subject,String content) throws Exception {

        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 25);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(sendAddress,senderName));
        msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toAddress));
        msg.setSubject(subject);
        msg.setContent(content,"text/html");


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
