package ar.edu.utn.frba.dds.TestDB;

import ar.edu.utn.frba.dds.modelo.*;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ServicioDBTest implements SimplePersistenceTest {

  RepoServicios repoServicios = RepoServicios.instancia();

  @Test
  public void guardarServicio() {
    Servicio servicio = new Servicio("Servicio",TipoDeServicio.ESCALERA, true, null);

    repoServicios.registrar(servicio);

    Servicio servicioRecuperado = repoServicios.buscar(servicio.getId());

    assertEquals(servicio,servicioRecuperado);

  }

  @Test
  public void recuperarServicio() {
    Servicio servicio = new Servicio(TipoDeServicio.ESCALERA, true, null);

    repoServicios.registrar(servicio);

    entityManager().flush();
    entityManager().clear();

    Servicio servicioRecuperado = repoServicios.buscar(servicio.getId());

    assertEquals(servicio.getId(),servicioRecuperado.getId());
    assertEquals(servicio.getNombre(),servicioRecuperado.getNombre());

  }

  @Test
  public void recuperarTodosLosServicios() {
    Servicio servicio1 = new Servicio("Servicio 1",TipoDeServicio.ESCALERA, true, null);
    Servicio servicio2 = new Servicio("Servicio 2",TipoDeServicio.ESCALERA, true, null);
    Servicio servicio3 = new Servicio("Servicio 3",TipoDeServicio.ESCALERA, true, null);

    repoServicios.registrar(servicio1);
    repoServicios.registrar(servicio2);
    repoServicios.registrar(servicio3);

    entityManager().flush();
    entityManager().clear();

    List<Servicio> servicios = repoServicios.todos();

    servicios.forEach(servicio -> {
      System.out.println(servicio.getId());
        }
    );
  }

  @Test
  public void sePersistenLasEntidadesYEstablecimientosDelServicio(){
    Entidad superDia = new Entidad("Super Dia");

    Establecimiento superDiaCentro = new Establecimiento("Super dia centro",TipoEstablecimiento.SUCURSAL,"centro");

    superDiaCentro.agregarAEntidad(superDia);

    Servicio banioPublico = new Servicio("baño super dia",TipoDeServicio.BANIO,true,superDiaCentro);
    repoServicios.registrar((banioPublico));

    entityManager().flush();
    entityManager().clear();

    //Se revisa que la estación se haya agregado correctamente
    Servicio banioRecuperado = repoServicios.buscar(banioPublico.getId());
    Establecimiento superRecuperado = banioRecuperado.getEstablecimiento();
    assertThat(superRecuperado).isNotNull(); //Se pudo recuperar el establecimiento

    Entidad diaRecuperado = superRecuperado.getEntidades().get(0);
    assertThat(diaRecuperado).isNotNull();  //Se pudo recuperar la entidad

    assertThat(banioRecuperado.getEstablecimiento().getId()).isEqualTo(superDiaCentro.getId()); //Me aseguro que sea el mismo establecimiento
    assertThat(superRecuperado.getEntidades().get(0).getId()).isEqualTo(superDia.getId());      //Me aseguro que sea la misma entidad
  }

  @Test
  public void recuperarServiciosDeIncidentesPersistidos(){
    Servicio servicio1 = new Servicio("servicio1",TipoDeServicio.ESCALERA, true, null);
    Servicio servicio2 = new Servicio("Baño impapelado",TipoDeServicio.BANIO, true, null);
    Servicio servicio3 = new Servicio("Ascensor trabado después de caida",TipoDeServicio.ASCENSOR, true, null);

    Incidente incidente1 = new Incidente(LocalDateTime.now(),servicio1,"");
    Incidente incidente2 = new Incidente(LocalDateTime.now(),servicio2,"No hay papael");
    Incidente incidente3 = new Incidente(LocalDateTime.now(),servicio3,"Mis piernas se quedaron adentro");

    RepoIncidentes.instancia().registrar(incidente1);
    RepoIncidentes.instancia().registrar(incidente2);
    RepoIncidentes.instancia().registrar(incidente3);

    entityManager().flush();
    entityManager().clear();

    List<Servicio> serviciosRecuperados = repoServicios.todos();
    System.out.println("Servicios:");
    serviciosRecuperados.forEach( servicio ->{
          System.out.println(servicio.getId()+": "+servicio.getNombre());
        }
      );

    }

  @Test
  public void recuperarSoloAlgunosServiciosQueCumplanUnaCondicion(){
    Servicio escalera1 = new Servicio("Escalera 1",TipoDeServicio.ESCALERA,true,null);
    Servicio escalera2 = new Servicio("Escalera 2",TipoDeServicio.ESCALERA,true,null);
    Servicio ascensor1 = new Servicio("Ascensor 1",TipoDeServicio.ASCENSOR,true,null);
    Servicio ascensor2 = new Servicio("Ascensor 2",TipoDeServicio.ASCENSOR,true,null);
    RepoServicios.instancia().registrar(escalera1);
    RepoServicios.instancia().registrar(escalera2);
    RepoServicios.instancia().registrar(ascensor2);
    RepoServicios.instancia().registrar(ascensor1);

    assertThat(repoServicios.todos()).isNotEmpty();

    List<Servicio> soloEscaleras = RepoServicios.instancia().serviciosQue(s -> {
      return (s.getTipoDeServicio()==TipoDeServicio.ESCALERA);
    });

    Assertions.assertNotNull(soloEscaleras);
    Assertions.assertEquals(2,soloEscaleras.size()); //soloEscaleras tiene solo 2 elementos
    Assertions.assertEquals(TipoDeServicio.ESCALERA,soloEscaleras.get(0).getTipoDeServicio());
    Assertions.assertEquals(TipoDeServicio.ESCALERA,soloEscaleras.get(1).getTipoDeServicio());


  }

  @Test
  public void sePuedePersistirUnServicioYRecuperarSuEstablecimientoYEntidades(){

    Linea entidad1 = new Linea(TipoTransporte.SUBTERRANEO,"Entidad 1");
    Linea entidad2 = new Linea(TipoTransporte.SUBTERRANEO,"Entidad 2");

    Establecimiento establecimiento = new Establecimiento("Establecimiento de prueba",TipoEstablecimiento.ESTACION,"Entre enti1 y enti2");
    establecimiento.agregarAEntidad(entidad1);
    establecimiento.agregarAEntidad(entidad2);

    Servicio servicio = new Servicio("Servcio",TipoDeServicio.ESCALERA,true,establecimiento);

    repoServicios.registrar(servicio);

    entityManager().flush();
    entityManager().clear();

    Servicio servicioRecuperado = repoServicios.buscar(servicio.getId());
    Establecimiento establecimientoRecuperado = servicioRecuperado.getEstablecimiento();
    System.out.println(establecimientoRecuperado.nombre);

    List<Entidad> entidadesRecuperadas = entityManager().createQuery("from Entidad").getResultList();
    entidadesRecuperadas.forEach(e -> System.out.println(e.nombreEntidad)
    );


  }
  /*
  @Test
  public void */
}

