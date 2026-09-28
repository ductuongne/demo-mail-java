package murach.util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class MailUtilGmail {

    private static final String SMTP_USERNAME = "tuongnoob432@gmail.com";
    private static final String SMTP_PASSWORD = "ftgy inhx bujr yvgw";

    public static void sendMail(String to, String from,
                                String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

        if (SMTP_USERNAME == null || SMTP_PASSWORD == null) {
            throw new MessagingException(
                    "Chưa cấu hình MAIL_USERNAME và MAIL_APP_PASSWORD cho Gmail SMTP.");
        }

        // 1 - get a mail session
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", 465);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        Session session = Session.getInstance(props);
        session.setDebug(true);

        // 2 - create a message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }
        // 3 - address the message
        Address fromAddress = new InternetAddress(SMTP_USERNAME);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 4 - send the message
        Transport transport = session.getTransport();
        transport.connect(SMTP_USERNAME, SMTP_PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
