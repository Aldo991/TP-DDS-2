package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.modelo.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ComunidadesController extends SessionValidator implements WithSimplePersistenceUnit {
  RepositorioCominudades repoComunidades;
  RepoPersonas repoPersonas;
  public ComunidadesController(RepositorioCominudades repoComunidades, RepoPersonas repoPersonas) {
    this.repoComunidades = repoComunidades;
    this.repoPersonas = repoPersonas;
  }

  public ModelAndView obtenerTodosLasComunidades(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("seccion","Comunidades");

    Persona usuario = validarSesionActiva(request, modelo);
    if(usuario==null) { //Si el usuario no está logueado se va a la página de error
      return new ModelAndView(null,"error-sesion.html.hbs");
    }

    //TODO ver si tiene permiso de administrador

    List<Comunidad> comunidadesUsuario = repoComunidades.comunidadesDePersona(usuario);

    if(usuario.getRol()== Rol.ADMINISTRADOR){
      modelo.put("otrasComunidades",repoComunidades.todos().stream().filter(c ->{return !(comunidadesUsuario.contains(c));}).toList());
    }

    modelo.put("comunidades", comunidadesUsuario);


    return new ModelAndView(modelo, "comunidades.html.hbs");
  }

  public ModelAndView getFormularioComunidad(Request request, Response response) {

    Map<String, Object> modelo = new HashMap<>();

    Persona usuario = validarSesionActiva(request, modelo);
    if(usuario==null) { //Si el usuario no está logueado se va a la página de error
      return new ModelAndView(null,"error-sesion.html.hbs");
    }

    //TODO si tiene permiso

    List<Persona> miembrosRegistrados = repoPersonas.todos();
    modelo.put("miembros", miembrosRegistrados);

    List<Persona> administradoresRegistrados = repoPersonas.puroAdmins();
    modelo.put("administradores", administradoresRegistrados);

    return new ModelAndView(modelo, "creacion-comunidad.html.hbs");
  }

  public Void crearComunidad(Request request, Response response) {

    Persona usuario = validarSesionActiva(request, new HashMap<>());
    if(usuario==null) { //Si el usuario no está logueado se va a la página de error
      //return new ModelAndView(null,"error-sesion.html.hbs");
    }

    //request.session().attribute("user_id", personaLoggeada.getId());

    //TODO si tiene permiso


    Comunidad comunidadNueva = new Comunidad(request.queryParams("nombreComunidad"));
    //comunidadNueva.agregarMiembro(null);
    //comunidadNueva.agregarAdministrador(null);

    String[] nombreMimebros = request.queryParamsValues("elejirMiembros");
    if(nombreMimebros!=null) {
      for (String miembro : nombreMimebros) {
        //String id_miembro = (miembro.split(" "))[2]; //agarramos solo el id
        //System.out.println("idMiembro : " + id_miembro);
        Persona personamiembro = RepoPersonas.instancia().buscar(Long.parseLong(miembro));
        comunidadNueva.agregarMiembro(personamiembro);
      }
    }


    String[] nombreAdministradores = request.queryParamsValues("elejirAdministradores");
    if(nombreAdministradores!=null) {
      for (String admin : nombreAdministradores) {
        //String id_admin = (admin.split(" "))[2]; //agarramos solo el id
        //System.out.println("idAdmin : " + id_admin);
        Persona personaadmin = RepoPersonas.instancia().buscar(Long.parseLong(admin));
        comunidadNueva.agregarAdministrador(personaadmin);
      }
    }

    withTransaction(() -> {
      RepositorioCominudades.instancia().registrar(comunidadNueva);
    });

    response.redirect("/comunidades");
    return null;
  }

  public ModelAndView detallesComunidad(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();

    modelo.put("seccion","Detalles de la comunidad");

    Persona usuario = validarSesionActiva(request, modelo);


    String id = request.params("id").substring(1);
    System.out.println("valor_id: " + id);

    Comunidad comunidadElejida = repoComunidades.buscar(Long.parseLong(id));
    System.out.println("nombre comunidad " + comunidadElejida.getNombre());

    modelo.put("comunidad",comunidadElejida);

    return new ModelAndView(modelo, "comunidad-detalle.html.hbs");
  }

  public Void salirmeDeComunidad(Request request, Response response) {
    String idComunidad = request.params("id");

    System.out.println("la comu es: " + idComunidad);

    Comunidad comunidadAburrida = repoComunidades.buscar(Long.parseLong(idComunidad));


    Persona noMasMiembro = repoPersonas.buscar(request.session().attribute("user_id"));

    //System.out.println("persona a eliminar: "+ noMasMiembro.getNombre() + "de la comu:" + comunidad.getNombre());
    withTransaction(()->{
      comunidadAburrida.eliminarMiembro(noMasMiembro);
      entityManager().persist(comunidadAburrida);
    });

    response.redirect("/comunidades");

    return null;
  }

  public Void eliminarComunidad(Request request, Response response) {

    Persona usuario = validarSesionActiva(request, new HashMap<>());//Esta función devuelve el objeto Persona del usuario si es necesario, y define el nombre qu eva a mostrar

    //request.session().attribute("user_id", personaLoggeada.getId());
    //TODO si tiene permiso

    String nombreComunidad = request.queryParams("nombreComunidad");
/*
    if(nombreComunidad==null) {
      System.out.println("entraste al null muy peligroso usar try catch");
    } else {
      System.out.println(nombreComunidad);
    }
*/
    withTransaction(() -> {
      Comunidad comunidadAEliminar = RepositorioCominudades.instancia().buscarPorNombre(nombreComunidad);
      RepositorioCominudades.instancia().remover(comunidadAEliminar);
    });

    response.redirect("/home");
    return null;
  }

  public Void echarMiembro(Request request, Response response) {

    String idComunidad = request.params("id");
    String idMiembro = request.params("usuario_id");

    System.out.println("la comunidad es: " + idComunidad);
    System.out.println("el usuario es: " + idMiembro);

    Persona noMasMiembro = repoPersonas.buscar(Long.parseLong(idMiembro));
    Comunidad comunidad = repoComunidades.buscar(Long.parseLong(idComunidad));

    System.out.println("persona a eliminar: "+ noMasMiembro.getNombre() + "de la comu:" + comunidad.getNombre());
    withTransaction(()->{
      comunidad.eliminarMiembro(noMasMiembro);
      entityManager().persist(comunidad);
    });


    response.redirect("/comunidades");
    return null;
  }
}
