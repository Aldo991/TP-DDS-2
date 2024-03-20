package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.modelo.*;
import ar.edu.utn.frba.dds.notificadores.NotificadorConsola;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class IncidenteTest {

  Persona gustavo;
  Persona martin;
  Comunidad unaComunidad;
  Entidad unaEntidad;
  Establecimiento unEstablecimiento;
  Servicio escaleraMecanica;
  Servicio ascensor;
  NotificadorConsola notificadorElegido;

//  private RepoIncidentes repoIncidentes;

  @BeforeEach
  void incidenteInit() {
//    repoIncidentes = RepoIncidentes.instancia();

    gustavo = new Persona("Gustavo", "Caceres",null,"gustavo@gmail.com","");
    martin = new Persona("Martin", "Gutierrez",null,"martin@gmail.com","");
    unaComunidad = new Comunidad();
    escaleraMecanica = new Servicio(TipoDeServicio.ESCALERA, true,unEstablecimiento);
    ascensor = new Servicio(TipoDeServicio.ASCENSOR, true,unEstablecimiento);
    unaEntidad = new Entidad("Entidad");
    unEstablecimiento = new Establecimiento(TipoEstablecimiento.ESTACION,"ubicacion");
    notificadorElegido = new NotificadorConsola();

    unaComunidad.agregarMiembro(gustavo);
    unaComunidad.agregarMiembro(martin);
    unEstablecimiento.agregarAEntidad(unaEntidad);
    martin.registrarNuevoServicioDeInteres(ascensor);
    martin.setNotificador(notificadorElegido);
    gustavo.registrarNuevoServicioDeInteres(ascensor);
    gustavo.setNotificador(notificadorElegido);
    escaleraMecanica.suscribirComunidad(unaComunidad);
    ascensor.suscribirUsuario(martin);
    ascensor.suscribirUsuario(gustavo);
  }

  @Test
  void unaPersonaRegistraUnIncidenteEnUnServicio() {
    escaleraMecanica.informarIncidenteAsincronico(gustavo,"La escalera está en reparación");
    Assertions.assertEquals(1, unaComunidad.getIncidentes().size());
    Assertions.assertEquals(1, martin.getIncidentesAInformar().size());
  }

  @Test
  void unaEntidadRegistraUnIncidenteSincronicoEnUnServicio() {
    ascensor.informarIncidenteSincronico("El ascensor se encuentra fuera de servicio");
  }

  @Test
  void seRegistraUnIncidenteEnUnServicioYunaPersonaLoCierra() {
    escaleraMecanica.informarIncidenteAsincronico(martin, "La escalera está en reparación");
    unaComunidad.getIncidentes().get(0).cerrarIncidente();
    Assertions.assertFalse(unaComunidad.getIncidentes().get(0).estaVigente());
  }

}
