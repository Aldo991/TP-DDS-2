package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.modelo.Persona;
import ar.edu.utn.frba.dds.modelo.RepoPersonas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionController {

  private RepoPersonas repoPersonas;

  public SessionController(RepoPersonas repoPersonas) {
    this.repoPersonas = repoPersonas;
  }

  public ModelAndView accederALogin(Request request, Response response) {

        Map<String, Object> modelo = new HashMap<>();
        Boolean login_fail = request.session().attribute("login_fail");
        if(login_fail!= null && login_fail){
          modelo.put("login_fail",true);
        }

        return new ModelAndView(modelo, "login.html.hbs");
    }

    public ModelAndView crearSesion(Request request, Response response) {
        try {
            Persona personaLoggeada = repoPersonas.validarUsuarioYContrasenia(
                    request.queryParams("username"),
                    request.queryParams("password"));

            request.session().attribute("user_id", personaLoggeada.getId());
          request.session().attribute("login_fail",false);
            response.redirect("/home");
            System.out.println("Se logueó "+personaLoggeada.getNombre()+" "+personaLoggeada.getApellido()+" - id:"+String.valueOf(personaLoggeada.getId()));

            return null;
        } catch (Exception e) {
            request.session().attribute("login_fail",true);
            System.out.println(e.getMessage());
            response.redirect("/login");
            return null;

            //Ver cookies para mostrar error de logue en el login
        }
    }

    public ModelAndView cerrarSesion(Request request, Response response) {
        request.session().removeAttribute("user_id");
        response.redirect("/home");
        return null;
    }
}
