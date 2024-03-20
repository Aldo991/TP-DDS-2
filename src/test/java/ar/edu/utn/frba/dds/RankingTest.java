package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.CriterioRanking.CriterioRanking;
import ar.edu.utn.frba.dds.CriterioRanking.EntidadesConMayorCantidadDeIncidentes;
import ar.edu.utn.frba.dds.CriterioRanking.EntidadesConMayorTimempoDeCierreDeIncidentes;
import ar.edu.utn.frba.dds.CriterioRanking.Ranking;
import ar.edu.utn.frba.dds.modelo.*;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class RankingTest implements SimplePersistenceTest {

  GeneradorDeRankings generadorDeRankings = GeneradorDeRankings.instancia();

  Entidad entidad1;
  Entidad entidad2;
  Entidad entidad3;

  Establecimiento establecimiento1;
  Establecimiento establecimiento2;
  Establecimiento establecimiento3;

  Servicio servicio1;
  Servicio servicio2;
  Servicio servicio3;

  Incidente incidente1;
  Incidente incidente2;
  Incidente incidente3;

  Comunidad comunidad1;
  Comunidad comunidad2;

  CriterioRanking criterioCantidadIncidentes = new EntidadesConMayorCantidadDeIncidentes();
  CriterioRanking criterioTiempoDeResolucion = new EntidadesConMayorTimempoDeCierreDeIncidentes();

  final RepoIncidentes incidentes = RepoIncidentes.instancia();
  final RepositorioCominudades comunidades = RepositorioCominudades.instancia();
  final GeneradorDeReportes generadorDeReportes = GeneradorDeReportes.instancia();

  @BeforeEach
  public void init() {
    entidad1 = new Entidad("Entidad 1");
    entidad2 = new Entidad("Entidad 2");
    entidad3 = new Entidad("Entidad 3");

    establecimiento1 = new Establecimiento(TipoEstablecimiento.ESTACION, "ubi");
    establecimiento2 = new Establecimiento(TipoEstablecimiento.ESTACION, "ubi");
    establecimiento3 = new Establecimiento(TipoEstablecimiento.ESTACION, "ubi");

    servicio1 = new Servicio(TipoDeServicio.ESCALERA, Boolean.TRUE, establecimiento1);
    servicio2 = new Servicio(TipoDeServicio.ESCALERA, Boolean.TRUE, establecimiento2);
    servicio3 = new Servicio(TipoDeServicio.ESCALERA, Boolean.TRUE, establecimiento3);

    establecimiento1.agregarAEntidad(entidad1);
    establecimiento2.agregarAEntidad(entidad2);
    establecimiento3.agregarAEntidad(entidad3);

    incidente1 = new Incidente(LocalDateTime.of(2023, 5, 11, 0, 0), servicio2);
    incidente2 = new Incidente(LocalDateTime.of(2023, 2, 16, 0, 0), servicio1);
    incidente3 = new Incidente(LocalDateTime.of(2023, 8, 25, 0, 0), servicio1);

    comunidad1 = new Comunidad();
    comunidad2 = new Comunidad();

    comunidades.registrar(comunidad1);
    comunidades.registrar(comunidad2);

    comunidad1.comunicarIncidente(incidente1);
    comunidad2.comunicarIncidente(incidente2);
    comunidad2.comunicarIncidente(incidente3);

    //esto se debe poder hacer desde la comunidad
    incidentes.registrar(incidente1);
    incidentes.registrar(incidente2);
    incidentes.registrar(incidente3);
  }

  @Test
  void criterioCantidadDeIncidentesDevuelveIncidentesCuandoHayIncidentesARankear() throws IOException {
    LocalDateTime desde = LocalDateTime.of(2023,2,11,0,0);
    LocalDateTime hasta = LocalDateTime.of(2023,12,11,0,0);
    Ranking ranking = generadorDeRankings.generarRankingPorCriterio(criterioCantidadIncidentes, desde, hasta);
    generadorDeReportes.generarReporte(ranking, "reporteRanking");
    Assertions.assertEquals(2,ranking.getResultado().size());
  }

  @Test
  void criterioTiempoDeResolucionDevuelveIncidentesCuandoHayIncidentesARankear() {
    comunidad1.comunicarIncidente(incidente1);
    comunidad2.comunicarIncidente(incidente2);
    comunidad2.comunicarIncidente(incidente3);

    //esto se debe poder hacer desde la comunidad
    incidentes.registrar(incidente1);
    incidentes.registrar(incidente2);
    incidentes.registrar(incidente3);

    LocalDateTime desde = LocalDateTime.of(2023,2,11,0,0);
    LocalDateTime hasta = LocalDateTime.of(2023,12,11,0,0);
    comunidades.getIncidentes().forEach(IncidenteDeComunidad::cerrarIncidente);
    Ranking ranking = generadorDeRankings.generarRankingPorCriterio(criterioTiempoDeResolucion, desde, hasta);
    Assertions.assertEquals(2,ranking.getResultado().size());
  }

  @Test
  void criterioCantidadDeIncidentesLanzaExcepcionCuandoNoHayIncidentes() {
    LocalDateTime desde = LocalDateTime.of(2022,2,11,0,0);
    LocalDateTime hasta = LocalDateTime.of(2022,12,11,0,0);
    Assertions.assertThrows(RuntimeException.class,
        () -> generadorDeRankings.generarRankingPorCriterio(criterioCantidadIncidentes, desde, hasta));
  }

  @Test
  void criterioTiempoDeResolucionLanzaExcepcionCuandoNoHayIncidentes() {
    LocalDateTime desde = LocalDateTime.of(2022,2,11,0,0);
    LocalDateTime hasta = LocalDateTime.of(2022,12,11,0,0);
    Assertions.assertThrows(RuntimeException.class,
        () -> generadorDeRankings.generarRankingPorCriterio(criterioTiempoDeResolucion, desde, hasta));
  }
}
