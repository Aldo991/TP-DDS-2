package ar.edu.utn.frba.dds.modelo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class IncidenteDeComunidad {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  private Incidente incidente;
  private LocalDateTime fechaCierre;
  private boolean vigente;


  public IncidenteDeComunidad() {
  }

  public IncidenteDeComunidad(Incidente incidente) {
    this.incidente = incidente;
    this.vigente = true;
  }

  public void cerrarIncidente(){
    this.vigente = false;
    this.fechaCierre = LocalDateTime.now();

  }

  public boolean estaVigente(){
    return vigente;
  }

  public Incidente getIncidente() {
    return incidente;
  }

  public double tiempoDeResolucion() {
    if(this.fechaCierre==null){
      return DAYS.between(LocalDateTime.now(), this.incidente.getFechaApertura());
    }else {
      return DAYS.between(this.fechaCierre, this.incidente.getFechaApertura());
    }
  }

  public Long getId() {
    return id;
  }
}
