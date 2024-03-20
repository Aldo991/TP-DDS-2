package ar.edu.utn.frba.dds.TestDB;

import ar.edu.utn.frba.dds.geoRef.clasesMolde.Centroide;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Municipio;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Provincia;
import ar.edu.utn.frba.dds.modelo.*;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntidadDBTest implements SimplePersistenceTest {

  @Test
  public void guardarEntidadConDosUbicaciones() {
    Entidad entidad = new Entidad();

    Localizacion localizacion1 = new Provincia();
    localizacion1.setNombre("cordoba");
    localizacion1.setCentroide(new Centroide(125,458));
    Localizacion localizacion2 = new Municipio();
    localizacion2.setNombre("hurlingham");
    localizacion2.setCentroide(new Centroide(125,458));

    entidad.agregarLocalizacion(localizacion1);
    entidad.agregarLocalizacion(localizacion2);

    entityManager().persist(localizacion1);
    entityManager().persist(localizacion2);
    entityManager().persist(entidad);

    assertNotNull(entityManager().find(Provincia.class,localizacion1.getId_Db()));
    assertEquals(entidad.getLocalizaciones().get(0), entityManager().find(Provincia.class,localizacion1.getId_Db()));
    assertEquals(entidad.getLocalizaciones().get(1), entityManager().find(Municipio.class,localizacion2.getId_Db()));
  }

  @Test
  public void guardarLinea() {
    Linea lineaA = new Linea(TipoTransporte.SUBTERRANEO,"Linea A");

    Establecimiento establecimiento1 = new Establecimiento(TipoEstablecimiento.ESTACION, "CABA");
    Establecimiento establecimiento2 = new Establecimiento(TipoEstablecimiento.ESTACION, "CABA");
    Localizacion localizacion = new Provincia();
    localizacion.setNombre("Buenos Aires");
    localizacion.setCentroide(new Centroide(125,458));

    lineaA.setOrigen(establecimiento1);
    lineaA.setDestino(establecimiento2);

    entityManager().persist(establecimiento1);
    entityManager().persist(establecimiento2);
    entityManager().persist(localizacion);
    entityManager().persist(lineaA);
  }


}