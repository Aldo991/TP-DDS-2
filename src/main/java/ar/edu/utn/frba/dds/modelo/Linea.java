package ar.edu.utn.frba.dds.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * javadoc.
 */
@Entity
@DiscriminatorValue("Linea")
public class Linea extends Entidad {
  //@Enumerated(EnumType.STRING)
  private TipoTransporte tipoTransporte;
  //@OneToOne
  @ManyToOne
  @JoinColumn(name = "origen_id")
  private Establecimiento origen;
  //@OneToOne
  @ManyToOne
  @JoinColumn(name = "destino_id")
  private Establecimiento destino;


  public Linea() {
  }

  public Linea(TipoTransporte tipo, String nombre) {
    super(nombre);
    this.tipoTransporte = tipo;
  }

  public void setOrigen(Establecimiento origen) {
    this.origen = origen;
  }

  public Establecimiento getOrigen() {
    return this.origen;
  }

  public void setDestino(Establecimiento destino) {
    this.destino = destino;
  }

  public Establecimiento getDestino() {
    return this.destino;
  }
}
