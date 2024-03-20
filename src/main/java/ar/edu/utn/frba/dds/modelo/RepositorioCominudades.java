package ar.edu.utn.frba.dds.modelo;

import ar.edu.utn.frba.dds.controller.IncidentesController;

import java.util.ArrayList;
import java.util.List;

public class RepositorioCominudades extends Repositorio<Comunidad> {

  public static RepositorioCominudades instancia;
  public List<Comunidad> comunidades = new ArrayList<>();

  //----SINGLETON----
  public static RepositorioCominudades instancia(){
    if (instancia == null) {
      instancia = new RepositorioCominudades();
    }
    return instancia;
  }

  public List<Comunidad> comunidadesDePersona(Persona persona){
    return entityManager().createQuery("select c from Comunidad c join c.miembros p where p.id=:personaId")
        .setParameter("personaId",persona.getId())
        .getResultList();
  }
  public List<IncidenteDeComunidad> getIncidentes() {
    return entityManager().createQuery("FROM IncidenteDeComunidad").getResultList();
  }

  public IncidenteDeComunidad buscarIncidente(Long id){
    return entityManager().find(IncidenteDeComunidad.class, id);
  }

  public List<IncidenteDeComunidad> incidentesDeUnServicio(Servicio servicio){
    List<IncidenteDeComunidad> resultado = entityManager().createQuery("SELECT ic from IncidenteDeComunidad ic join ic.incidente i join i.servicio s where s.id=:servicioId")
        .setParameter("servicioId",servicio.getId())
        .getResultList();

    return resultado;
  }

  //busca una comunidad por su nombre
  public Comunidad buscarPorNombre(String nombre) {
     return entityManager().createQuery("FROM Comunidad where nombre = :nombre", Comunidad.class)
                                        .setParameter("nombre", nombre)
                                        .getResultList()
                                        .get(0);

  }
}
