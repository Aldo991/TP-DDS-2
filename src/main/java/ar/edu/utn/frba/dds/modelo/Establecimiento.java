package ar.edu.utn.frba.dds.modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * javadoc.
 */
@Entity
public class Establecimiento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.ORDINAL)
  private TipoEstablecimiento tipoEstablecimiento;
  private String ubicacion;
  @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
  private List<Entidad> entidades;

  public String nombre;

  public Establecimiento() {
  }

  /**
   * javadoc.
   */
  public Establecimiento(TipoEstablecimiento tipoEstablecimiento, String ubicacion) {
    this.tipoEstablecimiento = tipoEstablecimiento;
    this.ubicacion = ubicacion;
    this.entidades = new ArrayList<>();
    this.nombre = "Establecimiento-sin-Nombre";
  }

  public Establecimiento(String nombre,TipoEstablecimiento tipoEstablecimiento, String ubicacion) {
    this.tipoEstablecimiento = tipoEstablecimiento;
    this.ubicacion = ubicacion;
    this.entidades = new ArrayList<>();
    this.nombre = nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public Long getId() {
    return id;
  }

  public TipoEstablecimiento getTipoEstablecimiento() {
    return tipoEstablecimiento;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public List<Entidad> getEntidades() {
    return entidades;
  }

  //public List<Servicio> getServicios() {rturn servicios;}

  public void agregarAEntidad(Entidad entidad){
    entidades.add(entidad);
  }

  //Se usa esDeLaEntidad en vezz de getEntiad porque una estación puede pertenecer a muchas lineas
  public boolean esDeLaEntidad(Entidad entidad) {
    return entidades.contains(entidad);
  }

  /*public List<Servicio> getServiciosConProblemas() {
    return servicios.stream().filter(servicio -> !servicio.estaDisponible()).toList();
  }*/
}
