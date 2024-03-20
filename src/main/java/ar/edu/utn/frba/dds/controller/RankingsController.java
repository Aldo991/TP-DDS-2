package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.CriterioRanking.CriterioRanking;
import ar.edu.utn.frba.dds.CriterioRanking.EntidadesConMayorCantidadDeIncidentes;
import ar.edu.utn.frba.dds.CriterioRanking.EntidadesConMayorTimempoDeCierreDeIncidentes;
import ar.edu.utn.frba.dds.CriterioRanking.Ranking;
import ar.edu.utn.frba.dds.modelo.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingsController extends SessionValidator{

  GeneradorDeRankings generador;
  public RankingsController(GeneradorDeRankings generadorDeRankings) {
    this.generador = generadorDeRankings;
  }


  public ModelAndView listarRanking(Request request, Response response) {

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("seccion","Rankings");
    validarSesionActiva(request,modelo);

    CriterioRanking criterio;
    //Ranking ranking;
    String param = request.queryParams("criterio");

    //Reviso si existe el parámetro criterio
    if(param==null){param="";} //Si param es null, le asigno vacío para que pueda hacer la comparación

    if(param.equalsIgnoreCase("cantidad")){              //Si el criterio es cantidad
      criterio = new EntidadesConMayorCantidadDeIncidentes();       //El criterio es un new EntidadesConMayorCantidadDeIncidentes
    }else if(param.equalsIgnoreCase("tiempo")) {            //Si el criterio tiempo
      criterio = new EntidadesConMayorTimempoDeCierreDeIncidentes();   //El criterio es un new EntidadesConMayorTimempoDeCierreDeIncidentes
    }else{
      return new ModelAndView(modelo, "rankings.html.hbs");
    }

    // A la vista a mostrar hay que pasarle el resultado del ranking que se generó
    // Ver que mostrar en caso de no tenes un criterio elegido

    try {
      Ranking ranking = generador.generarRankingPorCriterio(criterio,
          LocalDateTime.now().minus(1, ChronoUnit.DAYS), //Este año, TODO parametrizar el lapso estimado
          LocalDateTime.now().plus(1, ChronoUnit.DAYS));

      modelo.put("resultadoRanking",ranking.getResultado());
    }catch(Exception e){
      System.out.println(e.getMessage());
    }


    return new ModelAndView(modelo, "rankings.html.hbs");// Debería ir a lapágina que muestra los rankings
  }

}
