package ar.edu.utn.frba.dds.modelo;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
  private static RepoPersonas repoPersonas;

  private static Duration intervaloDeTiempo;


  public static void main(String[] args) {
      repoPersonas = RepoPersonas.instancia();

      List<Persona> personas = repoPersonas.todos();

      personas = personas.stream().filter(p -> p.alarmaEntre(LocalTime.now(),LocalTime.now().plus(intervaloDeTiempo))).collect(Collectors.toList());

      personas.forEach(p -> p.serAlarmado());
    }
}
