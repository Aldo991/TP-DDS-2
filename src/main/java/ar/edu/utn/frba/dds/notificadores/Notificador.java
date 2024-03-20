package ar.edu.utn.frba.dds.notificadores;

import ar.edu.utn.frba.dds.modelo.Comunidad;
import ar.edu.utn.frba.dds.modelo.Incidente;
import ar.edu.utn.frba.dds.modelo.Persona;
import ar.edu.utn.frba.dds.modelo.Servicio;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_notificacion")
public abstract class Notificador {
  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public abstract void notificarIncidente(Incidente incidente, Persona persona);

  public abstract void notificarServicioCercano(Servicio servicio, Persona persona);

  public abstract void notificarRemocionDeComunidad(Comunidad comunidad);
  //Le informamos que fue removido de la comunidad
}
