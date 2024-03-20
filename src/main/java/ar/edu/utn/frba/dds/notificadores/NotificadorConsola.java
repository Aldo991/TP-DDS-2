package ar.edu.utn.frba.dds.notificadores;

import ar.edu.utn.frba.dds.modelo.Comunidad;
import ar.edu.utn.frba.dds.modelo.Incidente;
import ar.edu.utn.frba.dds.modelo.Persona;
import ar.edu.utn.frba.dds.modelo.Servicio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Consola")
public class NotificadorConsola extends Notificador{

    @Override
    public void notificarIncidente(Incidente incidente, Persona persona) {
        System.out.println("Notificado incidente en el servicio "+incidente.getServicio().toString()+" por "+persona.getNombre()+" "+persona.getApellido());
        System.out.println("Incidente informado a las "+incidente.getFechaApertura().toString()+". Notificado a las "+ LocalDateTime.now().toString());
        if(incidente.getObservaciones()!=null){
            System.out.println("Observaciones: "+incidente.getObservaciones());
        }
    }

    @Override
    public void notificarServicioCercano(Servicio servicio, Persona persona) {
        System.out.println("");
    }

    @Override
    public void notificarRemocionDeComunidad(Comunidad comunidad) {
        final String[] mensaje = {"Le informamos muy educadamente que ested fué echado de la comunidad " + comunidad.getNombre() + " por su vil e indevido comportamiento." +
            "Atte:"};

        comunidad.getAdministradores().forEach(admin ->{
            mensaje[0] = mensaje[0] + admin.getNombre()+" "+admin.getApellido()+".\n";
        });

        System.out.println(mensaje[0]);
    }


}
