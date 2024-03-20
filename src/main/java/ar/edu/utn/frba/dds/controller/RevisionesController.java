package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.modelo.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevisionesController extends SessionValidator{
    RepositorioCominudades repoComunidades;

    public RevisionesController(RepositorioCominudades repoComunidades) {
        this.repoComunidades = repoComunidades;
    }


    public ModelAndView obtenerRevisiones(Request request, Response response) {

        Map<String, Object> modelo = new HashMap<>();
        modelo.put("seccion","Revisiones");
        Persona personaLoggeada = validarSesionActiva(request,modelo);

        if(personaLoggeada==null) {
            response.redirect("error-sesion.html.hbs");
        }

        //Para obtener las sugerencias de revision que recibe el usuario
        List<Comunidad> comunidadesUsuario = repoComunidades.comunidadesDePersona(personaLoggeada);
        modelo.put("comunidades", comunidadesUsuario);
        List<IncidenteDeComunidad> incidentesUsuario = comunidadesUsuario.stream().flatMap(comunidad -> comunidad.getIncidentes().stream()).toList();
        //List<Incidente> revisionesPendientes = repoIncidentes.getIncidentes();

        modelo.put("revisiones", incidentesUsuario);
        return new ModelAndView(modelo, "revisiones.html.hbs");
    }
}
