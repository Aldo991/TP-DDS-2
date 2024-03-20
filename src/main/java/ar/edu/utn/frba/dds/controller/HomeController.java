package ar.edu.utn.frba.dds.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.utn.frba.dds.CriterioRanking.CriterioRanking;
import ar.edu.utn.frba.dds.CriterioRanking.EntidadesConMayorCantidadDeIncidentes;
import ar.edu.utn.frba.dds.CriterioRanking.Ranking;
import ar.edu.utn.frba.dds.modelo.GeneradorDeRankings;
import ar.edu.utn.frba.dds.modelo.Incidente;
import ar.edu.utn.frba.dds.modelo.Persona;
import ar.edu.utn.frba.dds.modelo.RepoIncidentes;
import spark.ModelAndView;
import spark.Request;
import spark.RequestResponseFactory;
import spark.Response;

public class HomeController extends SessionValidator{
  public ModelAndView getInicio(Request request, Response response) {

    return new ModelAndView(null, "index.html.hbs");

  }

  public ModelAndView getHome(Request request, Response response) {

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("seccion","Home");
    validarSesionActiva(request,modelo);

    //----Obtención de los últimos incidentes Reportados
    List<Incidente> ultimosIncidentes = RepoIncidentes.instancia().últimosNincidentes(5);
    modelo.put("incidentes",ultimosIncidentes);

    //----Generación del Ranking
    //(Esto probablemente se debería cachear, pero parece más una cuestón de BI)
    try {
      Ranking ranking = GeneradorDeRankings.instancia().generarRankingPorCriterio(
          new EntidadesConMayorCantidadDeIncidentes(),  //Entidades por cantidad
          LocalDateTime.now().minus(1, ChronoUnit.DAYS), //Deste hoy menos un día
          LocalDateTime.now());                                       //Hasta hoy

          modelo.put("ranking",ranking.getResultado());
    } catch (Exception e){
      System.out.println(e.getMessage());
    }




    return new ModelAndView(modelo, "index.html.hbs");
  }

  public ModelAndView getFormularioIniciarSession(Request request, Response response) {

    return new ModelAndView(null, "login.html.hbs");

  }

  public ModelAndView mostrarLogin(Request request, Response response) {

    return new ModelAndView(null,"login.html.hbs");
  }
}
