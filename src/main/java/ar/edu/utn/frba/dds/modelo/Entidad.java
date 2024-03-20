package ar.edu.utn.frba.dds.modelo;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * javadoc.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminador")
public class Entidad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public String nombreEntidad;

  @ManyToMany
  @JoinColumn(name="localizacion_id")
  public List<Localizacion> localizaciones = new ArrayList<>();

  public List<Localizacion> getLocalizaciones() {
    return localizaciones;
  }
  /*public List<Servicio> serviciosQuePresentanIncidentes() {
    List<Servicio> servicios;
    servicios = establecimientos.stream()
        .flatMap(establecimiento -> establecimiento.getServiciosConProblemas().stream()).toList();
    return servicios;
  }
  */

  public Long getId() {
    return id;
  }

  public Entidad() {
  }
  public Entidad(String nombreEntidad) {
    this.nombreEntidad = nombreEntidad;
  }

  public String getNombreEntidad() {
    return nombreEntidad;
  }

  public void agregarLocalizacion(Localizacion nuevaLocalizacion) {
    this.localizaciones.add(nuevaLocalizacion);
  }

  public void eliminarLocalizacion(Localizacion localizacionAEliminar) {
    this.localizaciones.remove(localizacionAEliminar);
  }
}
