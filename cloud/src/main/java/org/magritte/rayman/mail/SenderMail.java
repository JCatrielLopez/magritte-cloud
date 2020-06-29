package org.magritte.rayman.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Setter
@Getter
public class SenderMail {
    // Recipient's email ID needs to be mentioned.
    private Vector<String> to;

    // Sender's email ID needs to be mentioned
    private String from = "informacionrayman@gmail.com";
    private String password = "ingenieria123";

    // Assuming you are sending email from through gmails smtp
    private String host = "smtp.gmail.com";

    private Properties properties;

    private Session session;

    public SenderMail() {
        // Get system properties
        properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);
    }

    public void addRecepient(String s){
        to.add(s);
    }

    public void sendMail(String messageToSend) {
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set Subject: header field
            message.setSubject("Aviso de Emergency Alert");

            // Now set the actual message
            message.setText(messageToSend);

            for (String s : to){
                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(s));
            }

            // Send message
            Transport.send(message);

            //Limpio vector de receptores
            to.removeAllElements();

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
