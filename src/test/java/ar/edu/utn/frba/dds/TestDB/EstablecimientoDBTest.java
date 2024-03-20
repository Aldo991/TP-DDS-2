package ar.edu.utn.frba.dds.TestDB;

import ar.edu.utn.frba.dds.modelo.Establecimiento;
import ar.edu.utn.frba.dds.modelo.TipoEstablecimiento;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

public class EstablecimientoDBTest implements SimplePersistenceTest {

  @Test
  public void guardarEstablecimiento() {
    Establecimiento establecimiento = new Establecimiento(TipoEstablecimiento.ESTACION, "Argentina");

    entityManager().persist(establecimiento);
  }
}
