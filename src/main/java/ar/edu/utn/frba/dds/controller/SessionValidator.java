package ar.edu.utn.frba.dds.controller;

import ar.edu.utn.frba.dds.modelo.Persona;
import ar.edu.utn.frba.dds.modelo.RepoPersonas;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class SessionValidator {
    public Persona validarSesionActiva(Request request, Map<String, Object> modelo){
        Long user_id = request.session().attribute("user_id");
        if(user_id!= null){//Si tengo un user_id
            //Long user_id = Long.parseLong(s_cookie); //Lo paso a Long
            Persona usuario = RepoPersonas.instancia().buscar(user_id); //BUsco el usuario con ese user_id
            //Si no hay usuario se devuelve null

            modelo.put("usuario",usuario.getNombre());
            //modelo.put("usuario_id", usuario.getId());
            //System.out.println("Verifico que entró "+usuario.getNombre());
            return usuario;
        }else{
            return null;
        }

    }
}
