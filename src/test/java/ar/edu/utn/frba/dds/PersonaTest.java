package ar.edu.utn.frba.dds;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.modelo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonaTest {

  Persona gustavo;
  Empresa organismoDeControl;
  Empresa entidadPrestadora;
  Entidad lineaE;
  Establecimiento estacionRetiro;
  Establecimiento estacionPalermo;
  Entidad ferrocarrilSanMartin;
  Servicio escaleraMecanicaHaciaAnden;
  Servicio banioDeMujeres;
  Servicio banioDeHombres;
  //Localizacion ubicacionUsuario;
  String ubicacionEstacionRetiro;
  String ubicacionEstacionPalermo;

  @BeforeEach
  void initUsuarios() {
    //ubicacionUsuario = new Localizacion("-34.59883880928743, -58.51117846374651");
    ubicacionEstacionRetiro = "Ramos Mejia 1430";
    ubicacionEstacionPalermo = "Av. Juan B. Justo 622";
    gustavo = new Persona("Gustavo","Caceres",null,"gustavo@gmail.com","");
    organismoDeControl = new Empresa("Organismo De Control", TipoEmpresa.ORGANISMO_DE_CONTROL,"org.de.control@gmail.com");
    entidadPrestadora = new Empresa("Entidad Prestadora",TipoEmpresa.ENTIDAD_PRESTADORA,"ent.prestadora@gmail.com");
    lineaE = new Linea(TipoTransporte.SUBTERRANEO, "Subte Linea E");
    ferrocarrilSanMartin = new Linea(TipoTransporte.FERROCARRIL, "Ferrocarril San Martin");
    estacionRetiro = new Establecimiento(TipoEstablecimiento.ESTACION,ubicacionEstacionRetiro);
    estacionPalermo = new Establecimiento(TipoEstablecimiento.ESTACION,ubicacionEstacionPalermo);
    escaleraMecanicaHaciaAnden = new Servicio(TipoDeServicio.ESCALERA, true,estacionRetiro);
    banioDeMujeres = new Servicio(TipoDeServicio.BANIO, false,estacionPalermo);
    banioDeHombres = new Servicio(TipoDeServicio.BANIO, true,estacionPalermo);
  }


  //TODO Actualizar test de persona para conocer sus intereses, en caso de ser necesario, sino este test suit queda obsoleto
 /*@Test
  void crearUnInteresadoSinServiciosNiEntidadesDeInteres() {
    Assertions.assertTrue(gustavo.serviciosDeInteresConIncidentesRegistrados().isEmpty());
  }

  @Test
  void crearUnInteresadoYAsignarleServiciosYEntidadesDeInteresSinIncidentes() {
    gustavo.registrarNuevoServicioDeInteres(escaleraMecanicaHaciaAnden);
    lineaE.agregarEstablecimiento(estacionRetiro);
    gustavo.registrarNuevaEntidadDeInteres(lineaE);

    Assertions.assertTrue(gustavo.serviciosDeInteresConIncidentesRegistrados().isEmpty());
  }

  @Test
  void crearUnaPersonaYAsignarleEntidadesDeInteresConIncidentes() {
    lineaE.agregarEstablecimiento(estacionRetiro);
    ferrocarrilSanMartin.agregarEstablecimiento(estacionPalermo);

    escaleraMecanicaHaciaAnden.setDisponibilidad(false);
    estacionRetiro.agregarServicio(escaleraMecanicaHaciaAnden);
    estacionPalermo.agregarServicio(banioDeMujeres);
    estacionPalermo.agregarServicio(banioDeHombres);

    gustavo.registrarNuevaEntidadDeInteres(lineaE);
    gustavo.registrarNuevaEntidadDeInteres(ferrocarrilSanMartin);

    Assertions.assertEquals(2, gustavo.serviciosDeInteresConIncidentesRegistrados().size());
  }

  @Test
  void crearUnaPersonaYAsignarleServiciosDeInteres() {
    gustavo.registrarNuevoServicioDeInteres(escaleraMecanicaHaciaAnden);
    gustavo.registrarNuevoServicioDeInteres(banioDeHombres);
    gustavo.registrarNuevoServicioDeInteres(banioDeMujeres);

    Assertions.assertEquals(3, gustavo.serviciosDeInteres().size());
  }

  @Test
  void crearUnaPersonaYAsignarleEntidadesDeInteres() {
  lineaE.agregarEstablecimiento(estacionRetiro);
  ferrocarrilSanMartin.agregarEstablecimiento(estacionPalermo);

  escaleraMecanicaHaciaAnden.setDisponibilidad(false);
  estacionRetiro.agregarServicio(escaleraMecanicaHaciaAnden);
  estacionPalermo.agregarServicio(banioDeMujeres);
  estacionPalermo.agregarServicio(banioDeHombres);

  gustavo.registrarNuevaEntidadDeInteres(lineaE);
  gustavo.registrarNuevaEntidadDeInteres(ferrocarrilSanMartin);

  Assertions.assertEquals(2, gustavo.serviciosDeInteresConIncidentesRegistrados().size());
  }

  @Test
  void seDetectaQueUnMiembroSeEncuentraCercaDeUnServicioEnMalFuncionamaiento() {
    Persona persona = new Persona("Gustavo", "Caceres","gustavo@gmail.com");

    Establecimiento estacionCatalinasLineaE = new Establecimiento(TipoEstablecimiento.ESTACION, "Leandro N. Alem 963");
    estacionCatalinasLineaE.agregarServicio(new Servicio(TipoDeServicio.ESCALERA,false));

    PositionServiceMagic positionService = mock(PositionServiceMagic.class);
    when(positionService.ubicacionActual()).thenReturn("Leandro N. Alem 963");
    persona.setPositionService(positionService);

    Assertions.assertTrue(persona.meEncuentroCerca(estacionCatalinasLineaE.ubicacion));
  }*/



}
