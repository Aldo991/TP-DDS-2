package ar.edu.utn.frba.dds.notificadores;


import ar.edu.utn.frba.dds.modelo.Comunidad;
import ar.edu.utn.frba.dds.modelo.Incidente;
import ar.edu.utn.frba.dds.modelo.Persona;
import ar.edu.utn.frba.dds.modelo.Servicio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Mail")
public class NotificadorMail extends Notificador{

  @Override
  public void notificarIncidente(Incidente incidente, Persona persona) {

  }
  @Override
  public void notificarServicioCercano(Servicio servicio, Persona persona) {

  }

  @Override
  public void notificarRemocionDeComunidad(Comunidad comunidad) {

  }
}
