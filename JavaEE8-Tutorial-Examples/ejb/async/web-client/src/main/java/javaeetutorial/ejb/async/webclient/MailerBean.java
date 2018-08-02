package javaeetutorial.ejb.async.webclient;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named
@Stateless
public class MailerBean {

    @Resource(name = "mail/myExampleSession")
    private Session session;

    @Asynchronous
    public Future<String> sendMessage(String recptAddress) {
        String status;
        try {
            session = getMailSession("3025");
            Message message = constructMimeMessage(session, recptAddress);
            Transport.send(message);
            status = "Sent";
        } catch (MessagingException ex) {
            status = "Encountered an error: " + ex.getMessage();
        }
        return new AsyncResult<>(status);
    }

    
    private Session getMailSession(String smtpPort) {
        Properties properties = new Properties();
        properties.put("mail.smtp.port", smtpPort);
        return Session.getInstance(properties);
    }

    private Message constructMimeMessage(Session session, String recptAddress) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom();
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recptAddress, false));
        msg.setSubject("Dummy Mail Subject");
        msg.setHeader("X-Mailer", "JavaMail");
        msg.setText("This is dummy mail content.");
        msg.setSentDate(new Date());
        return msg;
    }

}
