package ar.edu.utn.frba.dds.modelo;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class Mailer {
  public void enviarMail(String mailDesde, String password, String mailDestino, String sujeto, String mensaje) throws Exception {

    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");


    Authenticator authenticator = new Authenticator() {
      private PasswordAuthentication getAuthentication() {
        return new PasswordAuthentication(mailDesde, password);
      }
    };

    Session session = Session.getInstance(properties, authenticator);

    Message message = new MimeMessage(session);

    // Configurar mail
    message.setFrom(new InternetAddress(mailDesde));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailDestino));
    message.setSubject(sujeto);
    message.setText(mensaje);

    Transport.send(message);
  }
}
