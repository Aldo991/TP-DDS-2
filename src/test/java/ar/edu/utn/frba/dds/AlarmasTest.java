package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.modelo.*;
import ar.edu.utn.frba.dds.notificadores.NotificadorConsola;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AlarmasTest {

    private Entidad entidad = new Entidad("Entidad");
    Establecimiento establecimiento = new Establecimiento(TipoEstablecimiento.ESTACION,"Calle Falsa 123");

    private RepoAlarmas repoAlarmas = RepoAlarmas.instancia();

    private Persona juan = new Persona("Juan","Perez",null,"juanp@gmail.com","");
    private Persona tomas = new Persona("Tomas", "Gomez",null,"tomasg@gmail.com","");

    Servicio banio = new Servicio(TipoDeServicio.BANIO,true,establecimiento);
    Servicio escalera = new Servicio(TipoDeServicio.ESCALERA,true,establecimiento);
    Servicio ascensor = new Servicio(TipoDeServicio.ASCENSOR,true,establecimiento);


    //DEPRECATED
    /*public void unUsuarioPuedeSetearUnaAlarma(){

        banio.setDisponibilidad(false);
        Incidente banioTapado = new Incidente(LocalDateTime.now(),banio);

        escalera.setDisponibilidad(false);
        Incidente escaleraRota = new Incidente(LocalDateTime.now(),escalera);


        IncidenteDeComunidad idc1 = new IncidenteDeComunidad(banioTapado);
        IncidenteDeComunidad idc2 = new IncidenteDeComunidad(escaleraRota);

        juan.getIncidentesAInformar().add(idc1);
        juan.getIncidentesAInformar().add(idc2);

        LocalTime enDosSegundos = LocalTime.now().plusSeconds(2);

        juan.configurarHorarioDeNotificaciones(enDosSegundos);
        juan.setNotificador(new NotificadorConsola());


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Alarma alarmaEnDosSegundos = repoAlarmas.buscarAlarama(enDosSegundos);

        assertThat(repoAlarmas.getAlarmas().size()).isEqualTo(1);

        assertThat(repoAlarmas.getAlarmas()).contains(alarmaEnDosSegundos);

        assertThat(alarmaEnDosSegundos.getUsuariosSuscritos()).contains(juan);

    }*/
}
