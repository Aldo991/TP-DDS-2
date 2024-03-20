package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.criteriospw.CriterioPassword;
import ar.edu.utn.frba.dds.criteriospw.CriterioPasswordCaracteresRepetidos;
import ar.edu.utn.frba.dds.criteriospw.CriterioPasswordFuerte;
import ar.edu.utn.frba.dds.criteriospw.CriterioPasswordLongitud;
import ar.edu.utn.frba.dds.modelo.Persona;
import ar.edu.utn.frba.dds.modelo.RepoPersonas;
import ar.edu.utn.frba.dds.modelo.ValidadorPassword;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.LocalDate;
import java.util.List;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class UserController extends SessionValidator implements WithSimplePersistenceUnit {

  RepoPersonas repoPersonas;

  public UserController(RepoPersonas repoPersonas) {
    this.repoPersonas = repoPersonas;
  }

  public ModelAndView mostrarUsuario(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("seccion", "Usuario");
    Persona usuario = validarSesionActiva(request, modelo);

    if(usuario==null) { //Si el usuario no está logueado se va a la página de error
      return new ModelAndView(null,"error-sesion.html.hbs");
    }

    modelo.put("user",usuario);

    return new ModelAndView(modelo,"info-usuario.html.hbs");
  }

  public Void registrarUsuario(Request request, Response response) {

    //----Validar la contraseña
    String password = request.queryParams("password");

    ValidadorPassword validador = new ValidadorPassword();
    CriterioPassword[] criterios =
        {new CriterioPasswordFuerte(),
        new CriterioPasswordLongitud(6,50),
        new CriterioPasswordCaracteresRepetidos()};

    try {//Prueba si la contraseña es válida
      validador.validatePassword(password,criterios);
    } catch (Exception e) {      //Si no lo es:
      String mensaje = e.getMessage();
      mensaje = mensaje.replace("ar.edu.utn.frba.dds.criteriospw.FalloCriterioPassword: ","");
      request.session().attribute("signup_fail",mensaje);  //Setea la cookie al mensaje de error
      response.redirect("/usuarios/nuevo");                     //Y redirecciona a registrarse
      return null;
    }

    Persona persona = new Persona(request.queryParams("nombre"),
                              request.queryParams("apellido"),
                              null, //LocalDate.parse(request.queryParams("fechaDeNacimiento")),
                              request.queryParams("mail"),
                              password);
    withTransaction(() -> {
      repoPersonas.registrar(persona);
    });
    response.redirect("/home");
    return null;
  }

  public ModelAndView getFormularioEditarUsuario(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    return new ModelAndView(modelo, "sign-up.html.hbs");
  }
  //TODO: Implementar editarUsuario
  public Void editarUsuario(Request request, Response response) {
//    if(validarSesionActiva(request, response)) {
//      response.redirect("error-sesion.html.hbs");
//      return null;
//    }
    //con la sesion iniciada y desde la vista user.html.hbs el usuario puede tocar un boton que dice
    // "eliminar usuario" y ejecutar este método
    // ver como sacar la info sobre que usuario debemos eliminar

    //obtener la persona (el ID lo sacamos de la sesion?)
    Persona personaAEditar = repoPersonas.buscar(Long.parseLong(request.queryParams("id")));

    withTransaction(() -> {
      // persistir la nueva info de la persona
      //ver si es necesario crear un metodo en el repo para poder editar info de una persona

//          repoPersonas.editarPersona(persona,request.queryParams("nombre"),
//          request.queryParams("apellido"),
//          Date.valueOf(request.queryParams("fechaDeNacimiento")), request.queryParams("mail"),
//          request.queryParams("password"));
    });

    response.redirect("/me");
    return null;
  }

  //TODO: Implementar eliminarUsuario
  public Void eliminarUsuario(Request request, Response response) {
//    if(validarSesionActiva(request, response)) {
//      response.redirect("error-sesion.html.hbs");
//    }
    //con la sesion iniciada y desde la vista user.html.hbs el usuario puede tocar un boton que dice
    // "eliminar usuario" y ejecutar este método
    // ver como sacar la info sobre que usuario debemos eliminar
    return null;
  }

  public ModelAndView obtenerTodosLosUsuarios(Request request, Response response) {
    /*ademas deberiamos fijarno si tiene el rol para hacerlo*/
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("seccion", "Usuarios"); //Definición de la sección, esto aparece en el título
    validarSesionActiva(request,modelo);

    List<Persona> personasRegistradas = repoPersonas.todos();

    modelo.put("personas", personasRegistradas);
    return new ModelAndView(modelo, "usuarios.html.hbs");
  }

  public ModelAndView getFormularioCrearUsuario(Request request, Response response) {

    String signup_fail = request.session().attribute("signup_fail");
    HashMap modelo = new HashMap();

    if(signup_fail!=null){
      modelo.put("signup_fail",signup_fail);
    }

    return new ModelAndView(modelo, "sign-up.html.hbs");

  }


}
