package ar.edu.utn.frba.dds.modelo;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class  Incidente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime fechaApertura;
  private String observaciones;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "servicio_id",referencedColumnName = "id")
  private Servicio servicio;


  public Incidente() {
  }

  public Incidente(LocalDateTime fechaApertura, Servicio servicio) {
    this.fechaApertura = fechaApertura;
    this.servicio = servicio;
  }

  public Incidente(LocalDateTime fechaApertura, Servicio servicio, String observaciones) {
    this.fechaApertura = fechaApertura;
    this.servicio = servicio;
    this.observaciones = observaciones;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getFechaApertura() {
    return this.fechaApertura;
  }

  public Incidente setObservaciones(String observaciones) {
    this.observaciones = observaciones;
    return this;
  }

  public Servicio getServicio() {
    return servicio;
  }

  public String getObservaciones() {
    return observaciones;
  }
}
