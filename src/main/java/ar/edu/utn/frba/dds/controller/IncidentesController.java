package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.modelo.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

public class IncidentesController extends SessionValidator implements WithSimplePersistenceUnit{

  RepoIncidentes repoIncidentes;
  RepoServicios repoServicios;

  public IncidentesController(RepoIncidentes repoIncidentes, RepoServicios repoServicios) {
    this.repoIncidentes = repoIncidentes;
    this.repoServicios = repoServicios;
  }


  public ModelAndView getFormularioIncidente(Request request, Response response) {

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("seccion","Incidentes");
    Persona usuario = validarSesionActiva(request,modelo);

    if(usuario==null) { //Si el usuario no está logueado se va a la página de error
      return new ModelAndView(null,"error-sesion.html.hbs");
    }


    modelo.put("servicios", repoServicios.todos());

    return new ModelAndView(modelo, "creacion-incidente.html.hbs");
  }

  public ModelAndView crearIncidente(Request request, Response response) {

    Long servicio_id = Long.parseLong(request.queryParams("servicio"));
    Servicio servi = repoServicios.buscar(servicio_id);

    //LocalDateTime fecha = LocalDateTime.now();
    String observaciones = request.queryParams("observaciones");

    Persona usuario = validarSesionActiva(request, new HashMap<>());
    //Incidente incidente = new Incidente(fecha,null,observaciones);

    withTransaction(() -> { //Desde la transacción el usuario registra el incidente de comunidad e incidente en los repos
      servi.informarIncidenteAsincronico(usuario,observaciones);
    });

    response.redirect("/incidentes");
    return null;
  }

  public ModelAndView obtenerTodosLosIncidentes(Request request, Response response) {

    //---- TRAMITES ADMINISTRATIVOS :P ----
    //Instanciación del map modelo
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("seccion","Incidentes"); //Definición de la sección, esto aparece en el título
    Persona usuario = validarSesionActiva(request, modelo);//Esta función devuelve el objeto Persona del usuario si es necesario, y define el nombre qu eva a mostrar

    if(usuario==null) { //Si el usuario no está logueado se va a la página de error
      return new ModelAndView(null,"error-sesion.html.hbs");
    }
    //---- LÓGICA DE INCIDENTES

    List<Incidente> incidentesRegistrados = repoIncidentes.todos();
    modelo.put("incidentes", incidentesRegistrados);

    return new ModelAndView(modelo, "incidentes.html.hbs");
  }

  public ModelAndView obtenerIncidentesPorServicio(Request request, Response response) {

    Map<String, Object> modelo = new HashMap<>();
    Servicio servicio = new Servicio(
            TipoDeServicio.valueOf(TipoDeServicio.class,request.queryParams("servicio")),
            Boolean.FALSE ,
            null);//TODO: ver como convertir a los tipos necesarios
    // request.queryParams("id_establacimiento")
    List<Incidente> incidentesPorServicio = repoIncidentes.buscarPorServicio(servicio);
    modelo.put("incidentes", incidentesPorServicio);
    return new ModelAndView(modelo, "incidentes.html.hbs");
  }

  public Object getIncidenteDetalle(Request request, Response response, TemplateEngine engine) {

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("seccion","Incidentes"); //Definición de la sección, esto aparece en el título
    Persona usuario = validarSesionActiva(request, modelo);


    String id = request.params(":id");
    System.out.println(id);
    System.out.println(Long.parseLong(id));

    Incidente incidente = repoIncidentes.buscar(Long.parseLong(id));

    modelo.put("incidente",incidente);

    return incidente!=null?
        engine.render(new ModelAndView(modelo, "incidente-detalle.html.hbs"))
        : null;
  }

  public Void cerrarIncidente(Request request, Response response) {

    String id = request.params(":id");
    IncidenteDeComunidad incidente = RepositorioCominudades.instancia().buscarIncidente(Long.parseLong(id));

    withTransaction(()->{
      incidente.cerrarIncidente();
      entityManager().persist(incidente);
    });


    System.out.println("Cerré el incidente "+String.valueOf(incidente.getId())+":"+incidente.getIncidente().getServicio().getNombre());



    response.redirect("/home");
    return null;
  }
}
