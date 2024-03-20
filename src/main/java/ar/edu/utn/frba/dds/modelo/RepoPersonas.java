package ar.edu.utn.frba.dds.modelo;

import java.util.ArrayList;
import java.util.List;

public class RepoPersonas extends Repositorio<Persona>{

  public static RepoPersonas instancia;

  //--SINGLETON--
  public static RepoPersonas instancia(){
    if(instancia==null){
      instancia = new RepoPersonas();
    }
    return instancia;
  }

  public Persona validarUsuarioYContrasenia(String mail, String contrasenia) {
    return entityManager()
            .createQuery("from Persona where mail = :mail and password = :contrasenia", Persona.class)
            .setParameter("mail", mail)
            .setParameter("contrasenia",String.valueOf(contrasenia.hashCode()))//TODO hashear la contraseña
            .getResultList()
            .get(0);
  }

  public List<Persona> puroAdmins() {

    return entityManager()
        .createQuery("from Persona where rol = :rol", Persona.class)
        .setParameter("rol", Rol.ADMINISTRADOR)
        .getResultList();
  }
}
