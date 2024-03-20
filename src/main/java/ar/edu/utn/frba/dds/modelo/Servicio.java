package ar.edu.utn.frba.dds.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.*;

/**
 * javadoc.
 */
@Entity
public class Servicio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.ORDINAL)
  private TipoDeServicio tipoDeServicio;
  private Boolean disponibilidad;
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Establecimiento establecimiento;
  @ManyToMany(fetch = FetchType.LAZY, targetEntity= Comunidad.class)
  @JoinTable(name="Servicio_ComunidadInteresada")
  private List<Comunidad> comunidadesInteresadas;
  @ManyToMany
  @JoinTable(name="Servicio_UsuarioInteresado")
  private List<Persona> usuariosInteresados;

  public String nombre;

  public Servicio() {
  }

  public Servicio(TipoDeServicio tipo, Boolean disponibilidad, Establecimiento establecimiento) {
    this.tipoDeServicio = tipo;
    this.disponibilidad = disponibilidad;
    this.usuariosInteresados = new ArrayList<Persona>();
    this.comunidadesInteresadas = new ArrayList<Comunidad>();
    this.establecimiento = establecimiento;
    this.nombre = "Sin-Nombre";


  }

  public Servicio(String nombre,TipoDeServicio tipo, Boolean disponibilidad, Establecimiento establecimiento) {
    this.tipoDeServicio = tipo;
    this.disponibilidad = disponibilidad;
    this.usuariosInteresados = new ArrayList<Persona>();
    this.comunidadesInteresadas = new ArrayList<Comunidad>();
    this.establecimiento = establecimiento;
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public Long getId() {
    return this.id;
  }

  public TipoDeServicio getTipoDeServicio() {
    return tipoDeServicio;
  }

  public void setDisponibilidad(Boolean disponibilidad) {
    this.disponibilidad = disponibilidad;
  }

  public Boolean estaDisponible() {
    return this.disponibilidad;
  }

  public void informarIncidenteAsincronico(Persona informante, String observaciones) {
    Incidente incidente = new Incidente(LocalDateTime.now(), this);
    incidente.setObservaciones(observaciones);

    RepoIncidentes.instancia().registrar(incidente);
    comunidadesInteresadas.forEach(comunidad -> comunidad.comunicarIncidente(incidente));
  }

  public void informarIncidenteSincronico(String observaciones) {
    Incidente incidente = new Incidente(LocalDateTime.now(), this);
    incidente.setObservaciones(observaciones);

    usuariosInteresados.forEach(user -> user.notificarIncidente(incidente));
  }

  public List<Persona> getUsuariosInteresados() {
    return usuariosInteresados;
  }

  public void suscribirUsuario(Persona usuario) {
    this.usuariosInteresados.add(usuario);
  }

  public void suscribirComunidad(Comunidad comunidad) {
    this.comunidadesInteresadas.add(comunidad);
  }

  public Establecimiento getEstablecimiento() {
    return establecimiento;
  }

  public boolean esDeLaEntidad(Entidad entidad){
    return establecimiento.esDeLaEntidad(entidad);
  }

}
