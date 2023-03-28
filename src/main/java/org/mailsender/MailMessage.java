package org.mailsender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class MailMessage {

    public static Message messageWithAttachments(String recipient, String subject, String content, List<File> attachments) throws MessagingException, IOException {

        Message message = prepareMessageObject(recipient, subject);

        //Text part
        BodyPart messageTextPart = new MimeBodyPart();
        messageTextPart.setText(content);

        Multipart multipart = new MimeMultipart();

        //attachment part
        for (File file:attachments){
            MimeBodyPart messageAttachmentPart = new MimeBodyPart();
            messageAttachmentPart.attachFile(file.getAbsolutePath());
            multipart.addBodyPart(messageAttachmentPart);
        }
        //adding both parts
        multipart.addBodyPart(messageTextPart);

        message.setContent(multipart);
        return message;
    }

    public static Message textMessage(String recipient, String subject, String content) throws MessagingException {
        Message txtMsg = prepareMessageObject(recipient,subject);
        txtMsg.setText(content);
        return txtMsg;
    }

    private static Message prepareMessageObject(String recipient, String subject) throws MessagingException{

        //prepare configuration
        Properties prop = MailConfiguration.getConfiguration();
        MailAuthenticator authenticator = new MailAuthenticator();

        //create session
        Session session = Session.getInstance(prop,authenticator);

        //prepare message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(authenticator.getPasswordAuthentication().getUserName()));
        message.setSubject(subject);
        InternetAddress[] recipientAddresses = InternetAddress.parse(String.join(",", recipient));
        message.setRecipients(Message.RecipientType.TO, recipientAddresses);

        return message;
    }
}