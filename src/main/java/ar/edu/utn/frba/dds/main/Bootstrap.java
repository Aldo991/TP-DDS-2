package ar.edu.utn.frba.dds.main;
import ar.edu.utn.frba.dds.modelo.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bootstrap implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
    withTransaction(() -> {
      if (entityManager().createQuery("from Servicio").getResultList().isEmpty()) {

        //------ENTIDADES------

        Entidad casitasBaratas = new Entidad("Casitas Baratas");
        Entidad casasAltaGama = new Entidad("Casas de alta gama");
        Entidad laAnonima = new Entidad("Supermercados La Anonima");
        Entidad coto = new Entidad("Supermercados coto");
        Entidad carrefour = new Entidad("Supermercados carrefour");
        Linea lineaA = new Linea(TipoTransporte.SUBTERRANEO,"Linea A");
        Linea lineaB = new Linea(TipoTransporte.SUBTERRANEO,"Linea B");

        //------ESTABLECIMIENTOS------
        List<Establecimiento> establecimientos = new ArrayList<>();

        //Casitas
        Establecimiento casaDeEric = new Establecimiento("Casa de Eric",TipoEstablecimiento.SUCURSAL, "Direccion Secreta");
        Establecimiento casaDeAldo = new Establecimiento("Casa de Aldo",TipoEstablecimiento.SUCURSAL, "Direccion Super Secreta");
        Establecimiento casaDeRodrigo = new Establecimiento("Cuartito del Obelisco - Casa de Rodrigo",TipoEstablecimiento.SUCURSAL, "La punta del Obelisco");
        casaDeEric.agregarAEntidad(casitasBaratas);
        casaDeRodrigo.agregarAEntidad(casitasBaratas);
        casaDeAldo.agregarAEntidad(casasAltaGama);

        //Supers
        Establecimiento cotoCentro = new Establecimiento("Coto Microcentro",TipoEstablecimiento.SUCURSAL,"Microcentro");
        Establecimiento cotoCaballito = new Establecimiento("Coto Caballito",TipoEstablecimiento.SUCURSAL,"Caballito");
        cotoCentro.agregarAEntidad(coto);
        cotoCaballito.agregarAEntidad(coto);
        establecimientos.add(cotoCaballito);
        establecimientos.add(cotoCentro);

        Establecimiento carrefourCentro = new Establecimiento("Carrefour Microcentro",TipoEstablecimiento.SUCURSAL,"Microcentro");
        Establecimiento carrefourCaballito = new Establecimiento("Carrefour Caballito",TipoEstablecimiento.SUCURSAL,"Caballito");
        carrefourCentro.agregarAEntidad(carrefour);
        carrefourCaballito.agregarAEntidad(carrefour);
        establecimientos.add(carrefourCentro);
        establecimientos.add(carrefourCaballito);

        Establecimiento laAnonimaBariloche = new Establecimiento("La Anonima Bariloche",TipoEstablecimiento.SUCURSAL,"Bariloche");
        laAnonimaBariloche.agregarAEntidad(laAnonima);
        establecimientos.add(laAnonimaBariloche);

        //Subtes
        Establecimiento estacionCarlosPellegrini = new Establecimiento("Estación Carlos Pellegrini/Diagonal Norte",TipoEstablecimiento.ESTACION,"Corrientes y 9 de Julio");
        estacionCarlosPellegrini.agregarAEntidad(lineaA);
        estacionCarlosPellegrini.agregarAEntidad(lineaB);
        establecimientos.add(estacionCarlosPellegrini);
        Establecimiento estacionAvenidaDeMayo = new Establecimiento("Estación Avenida de Mayo/Lima",TipoEstablecimiento.ESTACION,"Avenida De Mayyo y 9 de Julio");
        estacionAvenidaDeMayo.agregarAEntidad(lineaA);
        establecimientos.add(estacionAvenidaDeMayo);
        Establecimiento estacionMedrano = new Establecimiento("Estación Medrano",TipoEstablecimiento.ESTACION,"Medrano y Corrientes");
        estacionMedrano.agregarAEntidad(lineaB);
        establecimientos.add(estacionMedrano);


        //------SERVICIOS------

        //Baños de casas
        Servicio banio_eric = new Servicio("Baño de Eric",TipoDeServicio.BANIO, false, casaDeEric);
        Servicio banio_aldo = new Servicio("Baño de Aldo",TipoDeServicio.BANIO, false, casaDeAldo);
        Servicio banio_rodrigo = new Servicio("Baño de Rodrigo",TipoDeServicio.BANIO, false, casaDeRodrigo);
        RepoServicios.instancia().registrar(banio_eric);
        RepoServicios.instancia().registrar(banio_aldo);
        RepoServicios.instancia().registrar(banio_rodrigo);

        //A cada establecimiento se le registra un baño púbilico y un baño de servicio
        establecimientos.forEach(establecimiento -> {
          Servicio banioPublico = new Servicio("Baño público - "+establecimiento.getNombre(),TipoDeServicio.BANIO,true,establecimiento);
          Servicio banioServicio = new Servicio("Baño de Servicio - "+establecimiento.getNombre(),TipoDeServicio.BANIO,true,establecimiento);
          RepoServicios.instancia().registrar(banioPublico);
          RepoServicios.instancia().registrar(banioServicio);
        });

        //Escaleras
        Servicio escaleraObelisco = new Servicio("Escaleras del Obelisco",TipoDeServicio.ESCALERA,true,casaDeRodrigo);
        Servicio escaleraServicioCoto = new Servicio("Escalera de Servicio - CotoCaballito",TipoDeServicio.ESCALERA,true,cotoCaballito);
        Servicio escaleraCarlosPellegrini1 = new Servicio("Escalera con salida a Diagonal Norte",TipoDeServicio.ESCALERA,true,estacionCarlosPellegrini);
        Servicio escaleraCarlosPellegrini2 = new Servicio("Escalera con salida Corrientes",TipoDeServicio.ESCALERA,true,estacionCarlosPellegrini);
        Servicio escaleraLaAnoinima = new Servicio("Escalera al segundo piso - LaAnonimaBariloche",TipoDeServicio.ESCALERA,true,laAnonimaBariloche);
        RepoServicios.instancia().registrar(escaleraObelisco);
        RepoServicios.instancia().registrar(escaleraServicioCoto);
        RepoServicios.instancia().registrar(escaleraCarlosPellegrini1);
        RepoServicios.instancia().registrar(escaleraCarlosPellegrini2);
        RepoServicios.instancia().registrar(escaleraLaAnoinima);

        //Ascensores
        Servicio ascensorDiscapacitados = new Servicio("Ascensor para discapacitados",TipoDeServicio.ASCENSOR,true,estacionMedrano);
        Servicio ascensorServicioCarrefour = new Servicio("Ascensor De Servicio (Esclusivo para empleados!)",TipoDeServicio.ASCENSOR,true,carrefourCaballito);
        Servicio ascensorClienteCarrefour = new Servicio("Ascensor De la plebe",TipoDeServicio.ASCENSOR,true,carrefourCaballito);
        Servicio ascensorAldo = new Servicio("Ascensor casero de la casa de aldo",TipoDeServicio.ASCENSOR,true,casaDeAldo);
        RepoServicios.instancia().registrar(ascensorDiscapacitados);
        RepoServicios.instancia().registrar(ascensorServicioCarrefour);
        RepoServicios.instancia().registrar(ascensorClienteCarrefour);
        RepoServicios.instancia().registrar(ascensorAldo);

        //------ PERSONAS ------
        Persona eric = new Persona("Eric", "Huanto", null,"jeric@mail.com","secreta");
        Persona rodrigo = new Persona("Rodrigo","Olea", LocalDate.of(1991,7,8),"rodrimax@lolo.com","LaMasSecreta",Rol.ADMINISTRADOR);
        Persona messi = new Persona("Leonel","Messi",LocalDate.of(1987,6,24),"leo10@gmail.com","daleCampeon",Rol.ADMINISTRADOR);
        Persona bruceLee = new Persona("Bruce","Lee",LocalDate.of(1940,11,27),"dragonPunch@kungfu.com","deathToNorris");
        Persona agente86 = new Persona("Maxwell","Smart",LocalDate.of(1930,6,1),"86forever@control.com","topSecret");
        Persona camila = new Persona("Camila","Albite",LocalDate.of(1999,1,25),"camila@mail.com","UnaPassword",Rol.ADMINISTRADOR);

        RepoPersonas.instancia().registrar(eric);
        RepoPersonas.instancia().registrar(rodrigo);
        RepoPersonas.instancia().registrar(messi);
        RepoPersonas.instancia().registrar(bruceLee);
        RepoPersonas.instancia().registrar(agente86);

        //------COMUNIDADES------

        Comunidad amigosDeLaPesca = new Comunidad("Amigos de la pesca");
        Comunidad amantesDeLosAscensores = new Comunidad("Amantes de los Ascensores");
        Comunidad cotoNosPreocupa = new Comunidad("Los Preocupados por Coto");
        Comunidad bandaUTN = new Comunidad("La Banda de la UTN");

        //Personas y comunidades
        bandaUTN.agregarMiembro(rodrigo);
        bandaUTN.agregarAdministrador(rodrigo);
        bandaUTN.agregarMiembro(eric);

        amigosDeLaPesca.agregarMiembro(rodrigo);
        amigosDeLaPesca.agregarAdministrador(bruceLee);

        cotoNosPreocupa.agregarMiembro(bruceLee);
        cotoNosPreocupa.agregarAdministrador(bruceLee);

        amantesDeLosAscensores.agregarMiembro(agente86);
        amantesDeLosAscensores.agregarAdministrador(agente86);
        amigosDeLaPesca.agregarMiembro(agente86);

        //Registro de personas y comunidades (No pueden existir indepentientemente, ergo se registral a la vez)


        RepositorioCominudades.instancia().registrar(amigosDeLaPesca);
        RepositorioCominudades.instancia().registrar(amantesDeLosAscensores);
        RepositorioCominudades.instancia().registrar(cotoNosPreocupa);
        RepositorioCominudades.instancia().registrar(bandaUTN);

        //Comunidades y servicios

        //Los amantes de la pesca consiguen suministros del carrefour de caballito
        RepoServicios.instancia().serviciosDelEstablecimiento(carrefourCaballito).forEach(servicio ->{
          servicio.suscribirComunidad(amigosDeLaPesca);
        });

        //La estacion Medrano le inntersa a los chicos de la UTN
        RepoServicios.instancia().serviciosDelEstablecimiento(estacionMedrano).forEach(servicio ->{
          servicio.suscribirComunidad(bandaUTN);
        });

        //Todos los servicios de coto le interesa a los preocupados por coto
        RepoServicios.instancia().serviciosDeLaEntidad(coto).forEach(servicio ->{
          servicio.suscribirComunidad(cotoNosPreocupa);
        });

        //Los amantes de los ascensores se suscriben a todos los ascensores
        RepoServicios.instancia().serviciosQue(s->(s.getTipoDeServicio().equals(TipoDeServicio.ASCENSOR))).forEach(servicio ->{
          servicio.suscribirComunidad(amantesDeLosAscensores);
        });


        //Incidentes

        ascensorAldo.informarIncidenteAsincronico(rodrigo,"Se rompió el hilo de hierro");
        escaleraServicioCoto.informarIncidenteAsincronico(bruceLee,"Alguien ha deshonrrado los botones del ascensor con sus lamentos");
        ascensorServicioCarrefour.informarIncidenteAsincronico(agente86,"El botón para el piso secreto no funciona, hay que usar las escaleras");


        //------ INCIDENTES ------
        //LocalDateTime cincoDeMayo = LocalDateTime.of(2023, 5, 5, 0, 0);
        //LocalDateTime ochoDeJulio = LocalDateTime.of(2023, 8, 8, 0, 0);



        //Incidente incidente1 = new Incidente(cincoDeMayo, banio_rodrigo);
        //Incidente incidente2 = new Incidente(ochoDeJulio, banio_eric, "Eric no tiró la cadena!");

        //RepoIncidentes.instancia().agregarIncidente(incidente1);
        //RepoIncidentes.instancia().agregarIncidente(incidente2);


      }
    });
  }

}