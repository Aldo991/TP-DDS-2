package ar.edu.utn.frba.dds.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepoIncidentes extends Repositorio<Incidente>{
  public static RepoIncidentes instancia;
  //public List<Incidente> incidentes = new ArrayList<>();

  //----SINGLETON----
  public static RepoIncidentes instancia(){
    if (instancia == null) {
      instancia = new RepoIncidentes();
    }
    return instancia;
  }

  public List<Incidente> buscarPorServicio(Servicio servicio){
    List<Incidente>  resultado =entityManager().createQuery("select i from Incidente i join i.servicio c where c.id=:servicioId")
          .setParameter("servicioId",servicio.getId())
          .getResultList();

    return resultado;
  }
  public List<Incidente> últimosNincidentes(int n){
    return entityManager().createQuery("FROM Incidente i ORDER BY i.fechaApertura")
        .setMaxResults(n)
        .getResultList();
  }

}
