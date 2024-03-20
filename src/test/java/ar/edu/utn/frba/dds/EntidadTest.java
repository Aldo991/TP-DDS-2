package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.modelo.*;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;

public class EntidadTest implements SimplePersistenceTest {

  private Establecimiento retiro;
  private Establecimiento sanMartin;
  private Establecimiento lavalle;
  private Establecimiento diagonalNorte;
  private Establecimiento avenidaDeMayo;
  private Establecimiento moreno;
  private Establecimiento independencia;
  private Establecimiento sanJuan;
  private Establecimiento constitucion;
  private Linea lineaC;
  private Linea lineaA;

  ArrayList<Establecimiento> recorridoLineaC = new ArrayList<>();

  private RepoServicios repoServicios;

  @BeforeEach
  public void instanciadeEntidadesYservicios() {
    //lineaA = new Linea(TipoTransporte.SUBTERRANEO, "Subte Linea A");
    lineaC = new Linea(TipoTransporte.SUBTERRANEO, "Subte Linea C");

    repoServicios = RepoServicios.instancia();

    retiro = new Establecimiento(TipoEstablecimiento.ESTACION,"Ramos Mejia 1430");
    sanMartin = new Establecimiento(TipoEstablecimiento.ESTACION,"Av Santa Fe 730");
    lavalle = new Establecimiento(TipoEstablecimiento.ESTACION,"Esmeralda 488");
    diagonalNorte = new Establecimiento(TipoEstablecimiento.ESTACION,"Sarmiento y Suipacha");
    avenidaDeMayo = new Establecimiento(TipoEstablecimiento.ESTACION,"Bernardo de Irigoyen y Av de Mayo");
    moreno = new Establecimiento(TipoEstablecimiento.ESTACION,"Bernardo de Irigoyen 341");
    independencia = new Establecimiento(TipoEstablecimiento.ESTACION,"Bernardo de Irigoyen 858");
    sanJuan = new Establecimiento(TipoEstablecimiento.ESTACION,"Bernardo de Irigoyen y San Juan");
    constitucion = new Establecimiento(TipoEstablecimiento.ESTACION,"Martin Rodriguez 1149");

    retiro.agregarAEntidad(lineaC);
    recorridoLineaC.add(retiro);
    sanMartin.agregarAEntidad(lineaC);
    recorridoLineaC.add(sanMartin);
    lavalle.agregarAEntidad(lineaC);
    recorridoLineaC.add(lavalle);
    diagonalNorte.agregarAEntidad(lineaC);
    recorridoLineaC.add(diagonalNorte);
    avenidaDeMayo.agregarAEntidad(lineaC);
    recorridoLineaC.add(avenidaDeMayo);
    moreno.agregarAEntidad(lineaC);
    recorridoLineaC.add(moreno);
    independencia.agregarAEntidad(lineaC);
    recorridoLineaC.add(independencia);
    sanJuan.agregarAEntidad(lineaC);
    recorridoLineaC.add(sanJuan);
    constitucion.agregarAEntidad(lineaC);
    recorridoLineaC.add(constitucion);

  }

  @Test
  public void seCreaUnEstablecimientoYSeLeAgreganServicios(){

    Entidad superDia = new Entidad("Super Dia");

    Establecimiento superDiaCentro = new Establecimiento(TipoEstablecimiento.SUCURSAL,"centro");

    superDiaCentro.agregarAEntidad(superDia);

    Servicio banioPublico = new Servicio(TipoDeServicio.BANIO,true,superDiaCentro);
    repoServicios.registrar((banioPublico));

    entityManager().flush();

    //Se revisa que la estación se haya agregado correctamente
    assertThat(superDiaCentro.esDeLaEntidad(superDia));
    assertThat(banioPublico.esDeLaEntidad(superDia));

  }

  //LINEAS
  @Test
  public void crearUnaLineaConSusEstacionesYSePuedeSaberOrigenYDestino(){
    Linea lineaCida = new Linea(TipoTransporte.SUBTERRANEO, "Subte Linea C Ida");

    recorridoLineaC.forEach(estacion ->{
      estacion.agregarAEntidad(lineaCida);
    });

    lineaCida.setOrigen(retiro);
    lineaCida.setDestino(constitucion);


    recorridoLineaC.forEach(estacion ->{
      estacion.esDeLaEntidad(lineaCida);
    });

    assertThat(lineaCida.getOrigen()).isEqualTo(retiro);
    assertThat(lineaCida.getDestino()).isEqualTo(constitucion);
  }

  @Test
  public void crearUnaLineaDeIdaYUnaDeVuelta(){
    Linea lineaCida = new Linea(TipoTransporte.SUBTERRANEO, "Subte Linea C Ida");
    Linea lineaCVuelta = new Linea(TipoTransporte.SUBTERRANEO, "Subte Linea C Vuelta");

    recorridoLineaC.forEach(estacion ->{
      estacion.agregarAEntidad(lineaCida);
      estacion.agregarAEntidad(lineaCVuelta);
    });

    lineaCida.setOrigen(retiro);
    lineaCida.setDestino(constitucion);

    lineaCVuelta.setOrigen(constitucion);
    lineaCVuelta.setDestino(retiro);

    //Collections.reverse(lineaCVuelta.getRecorrido()); //Revierte el orden de la lista

    //Se asegura que el destino y origen esten invertidos
    assertThat(lineaCida.getOrigen()).isEqualTo(lineaCVuelta.getDestino());
    assertThat(lineaCida.getDestino()).isEqualTo(lineaCVuelta.getOrigen());
    recorridoLineaC.forEach(estacion ->{
      assertThat(estacion.esDeLaEntidad(lineaCida));
      assertThat(estacion.esDeLaEntidad(lineaCVuelta));
    });
  }

  @Test
  public void crearUnServicioYAgregarloAUnaEstacion(){
    Establecimiento plazaDeMayo = new Establecimiento(TipoEstablecimiento.ESTACION,"Hipólito Yrigoyen 300");
    Servicio escalerasElectricas = new Servicio(TipoDeServicio.ESCALERA,true, plazaDeMayo);
    repoServicios.registrar(escalerasElectricas);


    assertThat(escalerasElectricas.getEstablecimiento()).isEqualTo(plazaDeMayo);
    assertThat(repoServicios.todos().contains(escalerasElectricas));

  }

  @Test
  public void crearVariosServiciosYAgregarelosAUnaEstacion(){

    Establecimiento plazaDeMayo = new Establecimiento(TipoEstablecimiento.ESTACION,"Hipólito Yrigoyen 300");

    //Al servicio se le define el establecimiento en la instanciación
    Servicio escaleraDesdeCalleHastaMolinete = new Servicio(TipoDeServicio.ESCALERA,true, plazaDeMayo);
    repoServicios.registrar(escaleraDesdeCalleHastaMolinete);
    Servicio escaleraDesdeMolineteHastaAnden = new Servicio(TipoDeServicio.ESCALERA,true, plazaDeMayo);
    repoServicios.registrar(escaleraDesdeMolineteHastaAnden);
    Servicio banioPublico = new Servicio(TipoDeServicio.BANIO,true, plazaDeMayo);
    repoServicios.registrar(banioPublico);

    assertThat(escaleraDesdeCalleHastaMolinete.getEstablecimiento()).isEqualTo(plazaDeMayo);
    assertThat(escaleraDesdeMolineteHastaAnden.getEstablecimiento()).isEqualTo(plazaDeMayo);
    assertThat(banioPublico.getEstablecimiento()).isEqualTo(plazaDeMayo);
  }

  @Test
  public void agregarUnServicioAUnaEstacionYDarloDeBaja() {

    Entidad superCarrefour = new Entidad("Super Carrefour");
    Establecimiento superCarrefourCentro = new Establecimiento("Carrefour centro",TipoEstablecimiento.SUCURSAL,"centro");
    superCarrefourCentro.agregarAEntidad(superCarrefour);

    Servicio escalerasElectricas = new Servicio("Escaleras Eléctricas",TipoDeServicio.ESCALERA,true,superCarrefourCentro);
    Servicio banioPublico = new Servicio("Baño público ",TipoDeServicio.BANIO,true, superCarrefourCentro);

    repoServicios.registrar(escalerasElectricas);
    repoServicios.registrar(banioPublico);

    //entityManager().persist(superCarrefourCentro);
    //entityManager().persist(superCarrefour);

    entityManager().flush();

    assertThat(repoServicios.todos()).isNotEmpty();

    assertThat(repoServicios.establecimientos()).isNotEmpty();
    assertThat(repoServicios.entidades().size()).isNotZero();

    ///Revisa los servicios de la entidad que definimos
    Assertions.assertEquals(repoServicios.serviciosDeLaEntidad(superCarrefour).size(),2);

    repoServicios.remover(escalerasElectricas);

    Assertions.assertEquals(repoServicios.serviciosDeLaEntidad(superCarrefour).size(),1);
  }

  @Test
  public void darDeAltaUnServicioEnTodasLasEstacionesDeUnaLineaYDarUnoDeBaja() {

    recorridoLineaC.forEach(estacion -> {//agrega un nuevo servicio funcional a cada estación
      repoServicios.registrar(new Servicio(TipoDeServicio.BANIO, true,estacion));
    });

    Servicio banioLavalle = repoServicios.serviciosDeLaEntidad(lineaC).get(2);
    banioLavalle.setDisponibilidad(false);

    //DEFINICION DE CONDICIONES
    Condition<Servicio> servivioDisponible = new Condition<>(s -> s.estaDisponible()
            , "está disponibles");


    Condition<Servicio> servicioNoDisponible = new Condition<>(s -> !s.estaDisponible(), "no está disponible");

    //ASSERTS
    assertThat(repoServicios.serviciosDeLaEntidad(lineaC)).haveAtLeastOne(servivioDisponible);
    assertThat(repoServicios.serviciosDeLaEntidad(lineaC)).haveExactly(1,servicioNoDisponible);

    assertThat(repoServicios.serviciosDelEstablecimiento(lavalle)).element(0).is(servicioNoDisponible);
  }
}
