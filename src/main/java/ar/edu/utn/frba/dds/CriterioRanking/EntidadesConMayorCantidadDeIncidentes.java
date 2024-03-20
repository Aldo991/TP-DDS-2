package ar.edu.utn.frba.dds.CriterioRanking;

import ar.edu.utn.frba.dds.modelo.ElementoRanking;
import ar.edu.utn.frba.dds.modelo.Entidad;
import ar.edu.utn.frba.dds.modelo.IncidenteDeComunidad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EntidadesConMayorCantidadDeIncidentes implements CriterioRanking {

  @Override
  public List<ElementoRanking> generarRanking(List<IncidenteDeComunidad> incidentesARankear) {
    List<ElementoRanking> resultadoRanking = new ArrayList<>();
    for (IncidenteDeComunidad i :
        incidentesARankear) {
      for (Entidad entidad :
          i.getIncidente().getServicio().getEstablecimiento().getEntidades()) {
        ElementoRanking elementoAModificar = resultadoRanking.stream()
            .filter(elementoRanking -> elementoRanking.esDeLaEntidad(entidad)).findFirst()
            .orElse(null);
        if (elementoAModificar == null) {
          ElementoRanking nuevoElemento = new ElementoRanking(entidad, 1.0);
          resultadoRanking.add(nuevoElemento);
        } else {
          elementoAModificar.modificar(elementoAModificar.getValor() + 1);
        }
      }
    }
    resultadoRanking = resultadoRanking.stream()
        .sorted(Comparator.comparingDouble(ElementoRanking::getValor))
        .collect(Collectors.toList());
    Collections.reverse(resultadoRanking);

    double valorMaximo = resultadoRanking.get(0).getValor();
    resultadoRanking.forEach(r ->{
      r.setPorcentaje( (int) (r.getValor() / valorMaximo * 100) );
    });

    return resultadoRanking;
  }
}
