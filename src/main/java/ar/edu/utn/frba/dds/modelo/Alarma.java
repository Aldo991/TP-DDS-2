package ar.edu.utn.frba.dds.modelo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TimerTask;

public class Alarma extends TimerTask {
    private LocalTime hora;
    private ArrayList<Persona> usuariosSuscritos;

    //----CONSTRUCTORES----

    public Alarma(LocalTime hora){
        this.hora = hora;
        this.usuariosSuscritos = new ArrayList<>();
    }

    public Alarma(String hora){
        LocalTime nuevaHora = LocalTime.parse(hora);

        this.hora = nuevaHora;
        this.usuariosSuscritos = new ArrayList<>();
    }

    //----METODO RUN----

    @Override
    public void run() {
        //Se ejecuta a la hora especificada

        usuariosSuscritos.forEach(p -> p.serAlarmado());

    }

    //----SUSCRIPTORES----

    public void suscribirUsuario(Persona usuario){
        this.usuariosSuscritos.add(usuario);
    }

    public void desuscribirUsuario(Persona usuario){
        this.usuariosSuscritos.remove(usuario);
    }

    //----COMPARADORES-----

    public boolean mismaHoraQue(LocalTime otraHora){
        return this.hora.equals(otraHora);
    }

    //----GETTERS----

    public LocalTime getHora() {
        return hora;
    }

    public ArrayList<Persona> getUsuariosSuscritos() {
        return usuariosSuscritos;
    }
}
