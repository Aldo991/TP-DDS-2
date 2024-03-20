package ar.edu.utn.frba.dds.CriterioRanking;

import ar.edu.utn.frba.dds.modelo.ElementoRanking;
import ar.edu.utn.frba.dds.modelo.IncidenteDeComunidad;

import java.util.List;

public interface CriterioRanking {
//  HashMap<Entidad, Double> generarRanking(List<Incidente> incidentesARankear);
  List<ElementoRanking> generarRanking(List<IncidenteDeComunidad> incidentesARankear);
}
