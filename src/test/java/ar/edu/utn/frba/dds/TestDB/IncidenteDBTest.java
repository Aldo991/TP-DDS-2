package ar.edu.utn.frba.dds.TestDB;

import ar.edu.utn.frba.dds.modelo.Incidente;
import ar.edu.utn.frba.dds.modelo.Servicio;
import ar.edu.utn.frba.dds.modelo.TipoDeServicio;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class IncidenteDBTest implements SimplePersistenceTest {

  @Test
  public void guardarIncidente() {
    Incidente incidente = new Incidente(LocalDateTime.now(),null);
    incidente.setObservaciones("Rompio el baño");

    entityManager().persist(incidente);
  }

  @Test
  public void guardarDosIncidentesParaUnMismoServicio() {
    Servicio servicio = new Servicio("Escalera principal",TipoDeServicio.ESCALERA, true, null);

    Incidente incidente1 = new Incidente(LocalDateTime.now(),servicio);
    Incidente incidente2 = new Incidente(LocalDateTime.now(),servicio);

    entityManager().persist(servicio);
    entityManager().persist(incidente1);
    entityManager().persist(incidente2);

    Assertions.assertEquals(incidente1.getServicio().getId(),(entityManager().find(Incidente.class, incidente1.getId())).getServicio().getId());
    Assertions.assertEquals(incidente2.getServicio().getId(),(entityManager().find(Incidente.class, incidente2.getId())).getServicio().getId());
  }
}
