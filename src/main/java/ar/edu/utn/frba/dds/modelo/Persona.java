package ar.edu.utn.frba.dds.modelo;

import ar.edu.utn.frba.dds.notificadores.Notificador;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * javadoc.
 */
@Entity
public class Persona implements IntersadoEnCercania{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private String mail;
  private String password;
  @ManyToOne
  private Notificador notificador;
  @ManyToOne
  private Localizacion localizacion;
  @Transient
  private List<Servicio> serviciosDeInteres;
  @Transient
  private List<Entidad> entidadesDeInteres;
  @ManyToMany(fetch = FetchType.LAZY, targetEntity= IncidenteDeComunidad.class)
  @JoinTable(name="Persona_IncidentesAInformar")
  private List<IncidenteDeComunidad> incidentesAInformar;

  @Enumerated(EnumType.ORDINAL)
  private Rol rol = Rol.USUARIO;

  public Rol getRol() {
    return rol;
  }

  //La persona tiene horarios de alarmas preferidos que puede configurar  para cuadno quiera recibir las notificaciones
  @ElementCollection
  private List<LocalTime> horariosDeAlarma;

  public Persona() {
  }

  public Persona(String nombre, String apellido, LocalDate fechaDeNacimiento, String mail, String password) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.mail = mail;
    this.password = String.valueOf(password.hashCode());
    this.serviciosDeInteres = new ArrayList<>();
    this.entidadesDeInteres = new ArrayList<>();
    this.incidentesAInformar = new ArrayList<>();

  }

  public Persona(String nombre, String apellido, LocalDate fechaDeNacimiento, String mail, String password,Rol rol) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.mail = mail;
    this.password = String.valueOf(password.hashCode());
    this.rol = rol;
    this.serviciosDeInteres = new ArrayList<>();
    this.entidadesDeInteres = new ArrayList<>();
    this.incidentesAInformar = new ArrayList<>();

  }

  public Persona(String nombre, String apellido, LocalDate fechaDeNacimiento, String mail, String password, Localizacion localizacion) {
    this(nombre,apellido,fechaDeNacimiento,mail,password);
    this.localizacion = localizacion;
  }

  //---SERVICIOS DE INTERES---


  /*public List<Servicio> serviciosDeInteresConIncidentesRegistrados() {
    List<Servicio> servicios = serviciosDeInteres.stream()
        .filter(servicio -> !servicio.estaDisponible()).collect(Collectors.toList());
    servicios.addAll(entidadesDeInteres.stream()
        .flatMap(entidad -> entidad.serviciosQuePresentanIncidentes().stream()).toList());
    return servicios;
  }*/

  public List<Servicio> serviciosDeInteres() {
    return this.serviciosDeInteres;
  }

  public List<Entidad> entidadesDeInteres() {
    return entidadesDeInteres;
  }

  public Localizacion getLocalizacion() {
    return localizacion;
  }

  public void registrarNuevoServicioDeInteres(Servicio servicio) {
    serviciosDeInteres.add(servicio);
  }
  public void registrarNuevaEntidadDeInteres(Entidad entidad) {
    entidadesDeInteres.add(entidad);
  }


  //---NOTIFICACIONES---
  public void setNotificador(Notificador notificador) {
    this.notificador = notificador;
  }

  public void notificarIncidente(Incidente incidente){
    this.notificador.notificarIncidente(incidente, this);
  }

  //---NOTIFICACIONES ASINCRÓNICAS---
  public void configurarHorarioDeNotificaciones(LocalTime hora){

    horariosDeAlarma.add(hora);

    //RepoAlarmas repoAlarmas = RepoAlarmas.instancia();
    //Alarma nuevaAlarma = repoAlarmas.buscarAlarama(hora);
    //nuevaAlarma.suscribirUsuario(this);
  }

  public void configurarHorarioDeNotificaciones(String hora) {
    LocalTime horaLocalTime = LocalTime.parse(hora);
    configurarHorarioDeNotificaciones(horaLocalTime);
  }

  boolean alarmaEntre(LocalTime principio, LocalTime fin){


    return horariosDeAlarma.stream()
        .anyMatch( horarioAlarma ->                                     //Si hay algún horario de los horarios de alarma
        horarioAlarma.isAfter(principio) && horarioAlarma.isBefore(fin) //Enre los horarios pasados
    );
  }

  public List<IncidenteDeComunidad> getIncidentesAInformar() {
    return incidentesAInformar;
  }

  public void serAlarmado() {
    incidentesAInformar.forEach(i-> {
      if(i.estaVigente()){
        notificarIncidente(i.getIncidente());
      }
    });
    incidentesAInformar.clear();
  }

  public void comunicarIncidente(IncidenteDeComunidad incidenteDeComunidad) {
    incidentesAInformar.add(incidenteDeComunidad);
  }

  //--SETTERS/GETTERS

  public Long getId() {
    return Id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public LocalDate getFechaDeNacimiento() {
    return fechaDeNacimiento;
  }

  public String getMail() {
    return mail;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  @Override//De la interfaz InteresadoEnCercania
  public void anteCercania(Servicio servicioCercano) {//Cuando está cerca de un servicio en mal funcionamiento, la entidad externa llama a esta función

    //Y se notifica al usuario mediante su método de notificación preferido
    notificador.notificarServicioCercano(servicioCercano,this);
  }

    public String getPassword() {
    return this.password;
    }

  public void notificarRemocion(Comunidad comunidad) {

    if(notificador!=null){
      notificador.notificarRemocionDeComunidad(comunidad);
    }
  }
}
