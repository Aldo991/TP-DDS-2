package ar.edu.utn.frba.dds.modelo;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import net.bytebuddy.asm.Advice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RepoServicios extends Repositorio<Servicio> {

  private static RepoServicios instancia;

  //--SINGLETON--
  public static RepoServicios instancia(){
     if(instancia==null){
       instancia = new RepoServicios();
     }

     return instancia;
  }

  //public boolean contieneA(Servicio servicio){return servicios.contains(servicio);}

  public List<Servicio> serviciosDeLaEntidad(Entidad entidad){

    List<Servicio> resultado = entityManager().createQuery("SELECT s from Servicio s join s.establecimiento es join es.entidades en where en.id=:entidadId")
          .setParameter("entidadId",entidad.getId())
          .getResultList();

    return resultado;
  }

  public List<Entidad> entidades (){
    return entityManager().createQuery("FROM Entidad").getResultList();
  }

  public List<Establecimiento> establecimientos(){
    return entityManager().createQuery("FROM Establecimiento").getResultList();
  }

  public List<Servicio> serviciosDelEstablecimiento(Establecimiento establecimiento){
    List<Servicio> resultado = entityManager().createQuery("SELECT s from Servicio s join s.establecimiento e where e.id=:establecimientoId")
        .setParameter("establecimientoId",establecimiento.getId())
        .getResultList();

    return resultado;
  }

  public List<Servicio> serviciosQue(Predicate<Servicio> condicion){
    List<Servicio> todo = todos();
    return todo.stream().filter(condicion).collect(Collectors.toList());
  }

}
