package ar.edu.utn.frba.dds.CriterioRanking;

import ar.edu.utn.frba.dds.modelo.ElementoRanking;
import ar.edu.utn.frba.dds.modelo.GeneradorDeReportes;
import ar.edu.utn.frba.dds.modelo.RepositorioCominudades;

import java.time.LocalDateTime;
import java.util.List;

public class Ranking {
  public RepositorioCominudades comunidades = RepositorioCominudades.instancia();
//  public class ContenidoRanking{
//    Entidad entidad;
//    Servicio servicio;
//  }

  CriterioRanking criterio;
  List<ElementoRanking> resultado;
  LocalDateTime fechaDeGeneracion;
  LocalDateTime fechaDesde;
  LocalDateTime fechaHasta;
  GeneradorDeReportes generadorDeReportes;

  public Ranking(CriterioRanking criterio, List<ElementoRanking> resultado,
                 LocalDateTime fechaDeGeneracion, LocalDateTime fechaDesde,
                 LocalDateTime fechaHasta, GeneradorDeReportes generadorDeReportes) {
    this.criterio = criterio;
    this.resultado = resultado;
    this.fechaDeGeneracion = fechaDeGeneracion;
    this.fechaDesde = fechaDesde;
    this.fechaHasta = fechaHasta;
    this.generadorDeReportes = generadorDeReportes;
  }

  public List<ElementoRanking> getResultado() {
    return resultado;
  }

  public LocalDateTime getFechaDeGeneracion() {
    return fechaDeGeneracion;
  }

  public LocalDateTime getFechaDesde() {
    return fechaDesde;
  }

  public LocalDateTime getFechaHasta() {
    return fechaHasta;
  }

//  public ReporteCSV generarReporte() {
//    generadorDeReportes.generarArchivoReporte(this.resultado);
//  }
}
