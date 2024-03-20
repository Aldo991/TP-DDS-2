package ar.edu.utn.frba.dds.CriterioRanking;

import ar.edu.utn.frba.dds.modelo.ElementoRanking;
import ar.edu.utn.frba.dds.modelo.Entidad;
import ar.edu.utn.frba.dds.modelo.IncidenteDeComunidad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EntidadesConMayorTimempoDeCierreDeIncidentes implements CriterioRanking {

//  @Override
//  public HashMap<Entidad, Double> generarRanking(List<Incidente> incidentesARankear) {
//    HashMap<Entidad, Double> resultadoRanking = new HashMap<>();
//    for (Incidente i :
//        incidentesARankear) {
//      for (Entidad entidad :
//          i.servicio.getEstablecimiento().getEntidades()) {
//        Double tiempoDeResolucion = resultadoRanking.get(entidad);
//        if (tiempoDeResolucion == null) {
//          resultadoRanking.put(entidad, 1.0); //i.getTiempoDeResolucion() no puedo obtener los incidentes de comunidad
//        } else if(tiempoDeResolucion < 1.0) { //i.getTiempoDeResolucion() no puedo obtener los incidentes de comunidad
//          //resultadoRanking.replace(entidad, i.getTiempoDeResolucion());
//        }
//      }
//    }
//    //Falta ordenar el ranking
//    return resultadoRanking;
//  }
  @Override
  public List<ElementoRanking> generarRanking(List<IncidenteDeComunidad> incidentesARankear) {
    List<ElementoRanking> resultadoRanking = new ArrayList<>();
    for (IncidenteDeComunidad i : incidentesARankear) {
      for (Entidad entidad : i.getIncidente().getServicio().getEstablecimiento().getEntidades()) {
        ElementoRanking elementoAModificar = resultadoRanking.stream()
            .filter(elementoRanking -> elementoRanking.esDeLaEntidad(entidad)).findFirst()
            .orElse(null);
        if (elementoAModificar == null) {
          // En caso de no existir un elemento con esa entidad registrada se crea
          ElementoRanking nuevoElemento = new ElementoRanking(entidad, i.tiempoDeResolucion());
          resultadoRanking.add(nuevoElemento);
        } else if (elementoAModificar.getValor() < i.tiempoDeResolucion()) {
          elementoAModificar.modificar(i.tiempoDeResolucion());
        }
      }
    }
    //Ordenar el ranking segun el valor de los elementos
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