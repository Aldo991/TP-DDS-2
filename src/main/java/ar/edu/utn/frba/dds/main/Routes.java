package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.controller.*;
import ar.edu.utn.frba.dds.modelo.*;
import ar.edu.utn.frba.dds.spark.template.handlebars.HandlebarsTemplateEngine;
import spark.Spark;
import spark.debug.DebugScreen;

import java.time.LocalDateTime;

public class Routes {

  public static void main(String[] args) {

    //Comando mágico de deploy
    //az webapp deploy --name grupo2WebApp --resource-group grupo-2-rg --type jar --src-path ".\2023-tpa-vi-no-grupo-02\target\tpa-1.0-SNAPSHOT.jar"
    System.out.println("Iniciando servidor");
    DebugScreen.enableDebugScreen();

    Spark.port(8080);
    Spark.staticFileLocation("/public");

    new Bootstrap().run();

    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    HomeController homeController = new HomeController();
    //Repos
    RepoIncidentes repoIncidentes = RepoIncidentes.instancia();
    RepoServicios repoServicios = RepoServicios.instancia();
    RepositorioCominudades repoComunidades = RepositorioCominudades.instancia();
    RepoPersonas repoPersonas = RepoPersonas.instancia();

    //Generador de rankings
    GeneradorDeRankings generadorDeRankings = GeneradorDeRankings.instancia();

    //Instancia de controllers
    IncidentesController incidentesController = new IncidentesController(repoIncidentes, repoServicios);
    RankingsController rankingsController = new RankingsController(generadorDeRankings);
    RevisionesController revisionesController = new RevisionesController(repoComunidades);
    UserController userController = new UserController(repoPersonas);
    SessionController sessionController = new SessionController(repoPersonas);
    ComunidadesController comunidadesController = new ComunidadesController(repoComunidades, repoPersonas);
    //Reemplazar Singleton por Inyección de dependencias


    Spark.get("/", ((request, response) -> {response.redirect("/home");return null;}),engine);
    Spark.get("/home", homeController::getHome, engine);

    //---- INCIDENTES ----
    Spark.get("/incidentes", incidentesController::obtenerTodosLosIncidentes, engine);
    Spark.get("/incidentes/nuevo", incidentesController::getFormularioIncidente, engine);
    Spark.get("/incidentes/:id", (request, response) -> incidentesController.getIncidenteDetalle(request, response, engine));
    //Spark.get("/incidente",incidentesController::obtenerIncidente,engine);

    Spark.post("/incidentes",incidentesController::crearIncidente,engine);
    Spark.post("/Comunidades/incidente/:id",incidentesController::cerrarIncidente);

    //---- SUGERENCIAS ----
    Spark.get("/sugerencias",revisionesController::obtenerRevisiones, engine);

    //---- RANKINGS ----
    Spark.get("/rankings",rankingsController::listarRanking, engine);

    //---- GESTION DE USUARIO ----
    Spark.get("/me", userController::mostrarUsuario, engine);      // /perfil o /mi
    // Y consistente con lo que requiere que esté logueado
    //Spark.get("/usuarios", userController::obtenerTodosLosUsuarios, engine);
    Spark.get("/usuarios/nuevo", userController::getFormularioCrearUsuario,engine);// /registro /signup

    //---- GESTION DE SESION ----
    
    //Spark.get("/iniciarSession", homeController::getFormularioIniciarSession, engine);
    Spark.get("/login", sessionController::accederALogin,engine);
    Spark.post("/login", sessionController::crearSesion,engine);

    Spark.post("/usuario", sessionController::cerrarSesion,engine);

    Spark.post("/usuarios", userController::registrarUsuario);

    //---- GESTION DE COMUNIDADES
    Spark.get("/comunidades", comunidadesController::obtenerTodosLasComunidades,engine);
    Spark.get("/comunidades/nuevo", comunidadesController::getFormularioComunidad, engine);
    Spark.post("/comunidades/nuevo", comunidadesController::crearComunidad);
    Spark.get("/comunidades/:id", comunidadesController::detallesComunidad,engine);
    Spark.post("/comunidades", comunidadesController::eliminarComunidad);//si bien dice post es de delete
    Spark.post("/comunidades/:id", comunidadesController::salirmeDeComunidad);
    Spark.post("/comunidades/:id/miembro/:usuario_id", comunidadesController::echarMiembro); //tambien delete


    System.out.println("Servidor iniciado!");
  }
}
