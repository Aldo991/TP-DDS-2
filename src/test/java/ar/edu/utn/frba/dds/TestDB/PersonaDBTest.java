package ar.edu.utn.frba.dds.TestDB;

import ar.edu.utn.frba.dds.modelo.*;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class PersonaDBTest implements SimplePersistenceTest {

  @Test
  public void guardarPersona() {
    Persona persona1 = new Persona("Juan", "Rodrigues", null,"jrodri@mail.com","");

    Establecimiento establecimiento = new Establecimiento(TipoEstablecimiento.ESTACION, "CABA");

    Servicio servicio1 = new Servicio(TipoDeServicio.ESCALERA, true, establecimiento);
    Servicio servicio2 = new Servicio(TipoDeServicio.ESCALERA, true, establecimiento);

    persona1.registrarNuevoServicioDeInteres(servicio1);
    persona1.registrarNuevoServicioDeInteres(servicio2);

    entityManager().persist(servicio1);
    entityManager().persist(servicio2);
    entityManager().persist(persona1);
    System.out.println(persona1.getId());
  }

  @Test
  public void sePuedeEncontrarUnaPersonaPorUsuarioYcontrasenia(){

    String mail = "mail@facil.com";
    String contra = "contrasenia";
    Persona testMan = new Persona("Johny","Test",null,
        mail,
        contra);

    RepoPersonas.instancia().registrar(testMan);

    Persona personaEncontrada = RepoPersonas.instancia().validarUsuarioYContrasenia(mail,contra);

    Assertions.assertNotNull(personaEncontrada);


  }
  @Test
  public void sePuedeEncontrarUnaPersonaPorUsuarioYcontrasenia2(){

    Persona rodrigo = new Persona("Rodrigo","Olea", LocalDate.of(1991,7,8),"rodrimax@lolo.com","LaMasSecreta");
    Persona messi = new Persona("Leonel","Messi",LocalDate.of(1987,6,24),"leo10@gmail.com","daleCampeon");
    Persona bruceLee = new Persona("Bruce","Lee",LocalDate.of(1940,11,27),"dragonPunch@kungfu.com","deathToNorris");
    Persona agente86 = new Persona("Maxwell","Smart",LocalDate.of(1930,6,1),"86forever@control.com","topSecret");

    RepoPersonas.instancia().registrar(rodrigo);
    RepoPersonas.instancia().registrar(messi);
    RepoPersonas.instancia().registrar(bruceLee);
    RepoPersonas.instancia().registrar(agente86);


    Persona personaEncontrada = RepoPersonas.instancia().validarUsuarioYContrasenia("rodrimax@lolo.com","LaMasSecreta");

    System.out.println(personaEncontrada.getNombre()+" "+personaEncontrada.getApellido()+" - "+personaEncontrada.getPassword());
    Assertions.assertNotNull(personaEncontrada);

  }

  @Test
  public void puedoEncontrarLasComunidadesALasQuePerteneceUnaPersona(){
    Persona persona1 = new Persona("Juan", "Rodrigues", null,"jrodri@mail.com","");
    RepoPersonas.instancia().registrar(persona1);

    Comunidad comu1 = new Comunidad("Comunidad 1");
    Comunidad comu2 = new Comunidad("Comunidad 2");
    Comunidad comu3 = new Comunidad("Comunidad 3");

    RepositorioCominudades.instancia().registrar(comu1);
    RepositorioCominudades.instancia().registrar(comu2);
    RepositorioCominudades.instancia().registrar(comu3);

    comu1.agregarMiembro(persona1);
    comu2.agregarMiembro(persona1);

    List<Comunidad> comunidadesEncontradas = RepositorioCominudades.instancia().comunidadesDePersona(persona1);
    comunidadesEncontradas.forEach(comu ->
        System.out.println(comu.getNombre()));

    Assertions.assertTrue(comunidadesEncontradas.size()==2);
    Assertions.assertTrue(comunidadesEncontradas.contains(comu1));
    Assertions.assertTrue(comunidadesEncontradas.contains(comu2));
    Assertions.assertFalse(comunidadesEncontradas.contains(comu3));
  }
}
