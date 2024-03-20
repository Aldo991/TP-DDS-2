package ar.edu.utn.frba.dds.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * javadoc.
 */
@Entity
public class Comunidad{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToMany(fetch = FetchType.LAZY, targetEntity=Persona.class)
  @JoinTable(name = "COMUNIDADES_MIEMBRO",
      joinColumns = {@JoinColumn(name ="COMUNIDAD_ID")},
      inverseJoinColumns = {@JoinColumn(name = "MIEMBRO_ID")})
  private List<Persona> miembros;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  private List<IncidenteDeComunidad> incidentes;
  @ManyToMany
  private List<Persona> administradores;

  private String nombre;

  /**
   * javadoc.
   */
  public Comunidad() {
    this.miembros = new ArrayList<Persona>();
    this.administradores = new ArrayList<Persona>();
    this.incidentes = new ArrayList<IncidenteDeComunidad>();
    this.nombre = "Comunidad-Sin-Nombre";
  }

  public Comunidad(String nombre) {
    this.nombre = nombre;
    this.miembros = new ArrayList<Persona>();
    this.administradores = new ArrayList<Persona>();
    this.incidentes = new ArrayList<IncidenteDeComunidad>();
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getId() {
    return id;
  }


  public List<IncidenteDeComunidad> getIncidentes() {
    return this.incidentes;
  }

  public void agregarMiembro(Persona miembro) {
    this.miembros.add(miembro);
  }

  public void eliminarMiembro(Persona miembro) {
    //miembro.notificarRemocion(this);
    this.miembros.remove(miembro);
  }

  public void agregarAdministrador(Persona miembro) {
    this.administradores.add(miembro);
  }

  public void eliminarAdministrador(Persona miembro) {
    this.administradores.remove(miembro);
  }

  public void setMiembros(List<Persona> miembros) {
    this.miembros = miembros;
  }

  public List<Persona> getMiembros() {
    return miembros;
  }

  public void setAdministradores(List<Persona> administradores) { this.administradores = administradores; }

  public List<Persona> getAdministradores() {
    return administradores;
  }

  public boolean esAdministrador(Persona persona){
    return administradores.contains(persona);
  }



  public boolean contieneIntegrante(Persona persona) { return miembros.contains(persona); }

  public void comunicarIncidente(Incidente incidente) {

    IncidenteDeComunidad incidenteDeComunidad = new IncidenteDeComunidad(incidente);
    incidentes.add(incidenteDeComunidad);

    miembros.forEach(miembro -> miembro.comunicarIncidente(incidenteDeComunidad));
  }
}
