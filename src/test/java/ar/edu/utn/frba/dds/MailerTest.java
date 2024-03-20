package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.modelo.Mailer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MailerTest {
  @Test
  public void mandarMail() {
    Mailer mailer = new Mailer();

    //Assertions.assertDoesNotThrow(() -> mailer.enviarMail("supsuhdude@gmail.com", "", "jfreyre@frba.utn.edu.ar", "test", "test"));

  }
}
