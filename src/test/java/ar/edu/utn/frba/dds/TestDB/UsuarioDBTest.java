package ar.edu.utn.frba.dds.TestDB;

import ar.edu.utn.frba.dds.modelo.Rol;
import ar.edu.utn.frba.dds.modelo.Usuario;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioDBTest implements SimplePersistenceTest {

  @Test
  public void sePuedePersistirUnUsuarioYRecuperarlo() {
    Usuario charly = new Usuario("charlyMaxx","Pa$$word", Rol.USUARIO);

    entityManager().persist(charly);

    entityManager().flush();
    entityManager().clear();

    Usuario charlyReturns = entityManager().find(Usuario.class,charly.getId());

    assertEquals(charly.getUsername(),charlyReturns.getUsername());

  }

}