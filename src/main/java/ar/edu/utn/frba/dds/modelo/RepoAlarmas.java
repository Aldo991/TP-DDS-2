package ar.edu.utn.frba.dds.modelo;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

public class RepoAlarmas {

private Timer timer;
    private Set<Alarma> alarmas;
    public static RepoAlarmas instancia;

    public RepoAlarmas(){
        this.alarmas = new HashSet<Alarma>();
        this.timer = new Timer();
    }

    //----SINGLETON----

    public static RepoAlarmas instancia(){
        if (instancia == null) {
            instancia = new RepoAlarmas();
        }

        return instancia;
    }

    //----REPOSITORIO----
    private void agregarAlarma(Alarma alarma){

        //Se programa la alarma para que suceda a la hora indicada (Dentro de un tiempo de delay calculado) y se repita cada 24 horas
        timer.scheduleAtFixedRate(alarma,calcularDelay(alarma.getHora()),24 * 60 * 60 * 1000);//24 horas * 60 minutos * 60 segundos * 1000 milisegundos

        //Y se agrega a la lista de alarmas conocidas
        alarmas.add(alarma);
    }

    public Alarma buscarAlarama(LocalTime hora){

        //Busca la alarma en la lista de alarmas
        Alarma alarma = alarmas.stream()
                .filter(a -> a.mismaHoraQue(hora))
                .findFirst()
                .orElse(null);

        //Si no la encuentra la crea
        if(alarma==null){
            alarma = new Alarma(hora);
            //Y la agrega al repositorio
            agregarAlarma(alarma);
        }

        //Devuelva la alarma encontrada o creada
        return alarma;
    }

    public Alarma buscarAlarama(String hora){
        LocalTime horaLocalTime = LocalTime.parse(hora);

        return buscarAlarama(horaLocalTime);
    }


    //----FUNCION PRIVADA----
    private long calcularDelay(LocalTime horaAlarma) {
        long currentMillis = LocalTime.now().toNanoOfDay() / 1_000_000;
        long alarmMillis = horaAlarma.toNanoOfDay() / 1_000_000;
        long delay = alarmMillis - currentMillis;
        if (delay < 0) {
            //Si la hora de la alarma la pasó, se programa para 24 horas después
            delay += 24 * 60 * 60 * 1000;
        }
        return delay;
    }

    //----GETTERS----


    public Set<Alarma> getAlarmas() {
        return alarmas;
    }
}
