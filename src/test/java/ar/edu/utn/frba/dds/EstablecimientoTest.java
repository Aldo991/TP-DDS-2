package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.modelo.*;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstablecimientoTest implements SimplePersistenceTest {

  private RepoServicios repoServicios;

  Establecimiento estacionCatalinasLineaE;
  Servicio escaleraMecanicaAccesoAMolinetes;
  Servicio escaleraMecanicaAccesoAlAnden;
  Servicio banioDeMujeres;
  Servicio banioDeHombres;

  @BeforeEach
  public void instanciarRepositorioDeServicios(){
    repoServicios = RepoServicios.instancia();
  }

  @BeforeEach
  public void initEstacionesYServicios() {

    repoServicios = RepoServicios.instancia();

    estacionCatalinasLineaE = new Establecimiento(TipoEstablecimiento.ESTACION,"Leandro N. Alem 963");

    banioDeHombres = new Servicio(TipoDeServicio.BANIO,false,estacionCatalinasLineaE);
    banioDeMujeres = new Servicio(TipoDeServicio.BANIO,true,estacionCatalinasLineaE);
    repoServicios.registrar(banioDeHombres);
    repoServicios.registrar(banioDeMujeres);
  }

  //TODO este test suit parece redundante con lo que está en EntidadTest, considerar unificarlo y pasarlo a un ServiciosTest
  @Test
  public void seLeAgreganServiciosAunaEstacion() {

    escaleraMecanicaAccesoAMolinetes = new Servicio(TipoDeServicio.ESCALERA,true,estacionCatalinasLineaE);
    repoServicios.registrar(escaleraMecanicaAccesoAMolinetes);
    escaleraMecanicaAccesoAlAnden = new Servicio(TipoDeServicio.ESCALERA,true,estacionCatalinasLineaE);
    repoServicios.registrar(escaleraMecanicaAccesoAlAnden);

    Assertions.assertEquals(estacionCatalinasLineaE,escaleraMecanicaAccesoAMolinetes.getEstablecimiento());
    Assertions.assertEquals(estacionCatalinasLineaE,escaleraMecanicaAccesoAlAnden.getEstablecimiento());
    Assertions.assertTrue(repoServicios.serviciosDelEstablecimiento(estacionCatalinasLineaE).contains(escaleraMecanicaAccesoAMolinetes));
    Assertions.assertTrue(repoServicios.serviciosDelEstablecimiento(estacionCatalinasLineaE).contains(escaleraMecanicaAccesoAlAnden));
  }

  @Test
  public void seCreaUnaEstacionYSeLeAgreganServiciosNoDisponibles() {
    Establecimiento plazaDeMayo = new Establecimiento(TipoEstablecimiento.ESTACION,"Hipólito Yrigoyen 300");
    Servicio banioPlazaDeMayo = new Servicio(TipoDeServicio.BANIO,false,plazaDeMayo);
    Servicio escaleraPlazaDeMayo = new Servicio(TipoDeServicio.ESCALERA,false,plazaDeMayo);
    repoServicios.registrar(banioPlazaDeMayo);
    repoServicios.registrar(escaleraPlazaDeMayo);

    Assertions.assertEquals(false, repoServicios.serviciosDelEstablecimiento(plazaDeMayo).get(0).estaDisponible());
    Assertions.assertEquals(false, repoServicios.serviciosDelEstablecimiento(plazaDeMayo).get(1).estaDisponible());
  }

  @Test
  public void seCreaUnaSucursalYSeLeAgreganServiciosConDistintaDisponibilidad() {
    Establecimiento sucursal = new Establecimiento(TipoEstablecimiento.SUCURSAL,"Calle falsa 123");
    Servicio ascensor = new Servicio(TipoDeServicio.ASCENSOR, true,sucursal);
    Servicio escaleraMecanica = new Servicio(TipoDeServicio.ESCALERA, false,sucursal);
    repoServicios.registrar(ascensor);
    repoServicios.registrar(escaleraMecanica);


    Assertions.assertEquals(TipoEstablecimiento.SUCURSAL, sucursal.getTipoEstablecimiento());
    Assertions.assertEquals(2,repoServicios.serviciosDelEstablecimiento(sucursal).size());
    Assertions.assertEquals(false, repoServicios.serviciosDelEstablecimiento(sucursal).get(1).estaDisponible());
  }

}
