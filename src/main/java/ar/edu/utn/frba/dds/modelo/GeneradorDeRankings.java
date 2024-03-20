package ar.edu.utn.frba.dds.modelo;

import ar.edu.utn.frba.dds.CriterioRanking.CriterioRanking;
import ar.edu.utn.frba.dds.CriterioRanking.Ranking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GeneradorDeRankings {
  public RepositorioCominudades cominudades = RepositorioCominudades.instancia();
  public static GeneradorDeRankings instancia;

  //----SINGLETON----
  public static GeneradorDeRankings instancia(){
    if (instancia == null) {
      instancia = new GeneradorDeRankings();
    }
    return instancia;
  }

  public Ranking generarRankingPorCriterio(CriterioRanking criterio, LocalDateTime fechaDesde,
                                           LocalDateTime fechaHasta) {
    //Lista de incidentes registrados en el período especificado
    //Puede abstraerse la lógica en otro método
    List<IncidenteDeComunidad> incidentesARankear = this.cominudades.getIncidentes()
        .stream().filter(incidenteComunidad -> incidenteComunidad.getIncidente().getFechaApertura().isAfter(fechaDesde)
            && incidenteComunidad.getIncidente().getFechaApertura().isBefore(fechaHasta))
        .collect(Collectors.toList());
    //Lista "resultadoRanking" obtenida a partir de criterio.generarRanking(incidentesARankear)
    if (incidentesARankear.isEmpty()) {
      throw new RuntimeException("No se registraron incidentes en entre el " + fechaDesde.toString() + " y el " + fechaHasta);
    } else {
      List<ElementoRanking> resultadoRanking = criterio.generarRanking(incidentesARankear);
      //Ranking creado con toda la info anterior
      return new Ranking(criterio,resultadoRanking,LocalDateTime.now(),fechaDesde, fechaHasta,
          new GeneradorDeReportes());
    }
  }
}
