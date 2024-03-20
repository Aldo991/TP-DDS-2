package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.modelo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComunidadTest {

  Persona juan;
  Persona tomas;
  Persona francisco;
  Persona admin;

  @BeforeEach
  void initComunidad() {
    juan = new Persona("Juan","Perez",null,"juanp@gmail.com","");
    tomas = new Persona("Tomas", "Gomez",null,"tomasg@gmail.com","");
    francisco = new Persona("Francisco", "Alvarez",null,"franciscoa@hotmail.com","");
    admin = new Persona("Administrador","Ferraro",null,"ferraro@gmail.com","");
  }

  @Test
  public void seCreaUnaComunidadYAgregarleUnMiembro() {
    Comunidad clubFerroviario = new Comunidad();

    clubFerroviario.agregarMiembro(juan);
    clubFerroviario.agregarMiembro(tomas);
    clubFerroviario.agregarMiembro(francisco);

    assertThat(clubFerroviario.getMiembros()).containsOnly(juan,tomas,francisco);

    //Assertions.assertEquals(clubFerroviario.getMiembros().size(),3);

  }

  @Test
  public void seAgregaUnMiembroAUnaComunidadYSeElimina() {
    Comunidad clubFerroviario = new Comunidad();
    clubFerroviario.agregarMiembro(tomas);
    clubFerroviario.agregarMiembro(francisco);

    assertThat(clubFerroviario.getMiembros()).contains(tomas, francisco);

    clubFerroviario.eliminarMiembro(tomas);

    assertThat(clubFerroviario.getMiembros()).doesNotContain(tomas);
    assertThat(clubFerroviario.getMiembros()).containsOnly(francisco);
    //assertEquals(clubFerroviario.getMiembros().size(),1);

  }

  @Test
  public void seAgragaUnMiembroAMultiplesComunidades(){
    Comunidad amantesDelGolf = new Comunidad();
    Comunidad clubFerroviario = new Comunidad();

    clubFerroviario.agregarMiembro(tomas);
    clubFerroviario.agregarMiembro(juan);

    amantesDelGolf.agregarMiembro(tomas);
    amantesDelGolf.agregarMiembro(francisco);

    assertThat(tomas)
            .isIn(amantesDelGolf.getMiembros())
            .isIn(clubFerroviario.getMiembros());

    assertThat(clubFerroviario.getMiembros()).containsOnly(tomas,juan);
    assertThat(amantesDelGolf.getMiembros()).containsOnly(tomas,francisco);

    assertThat(clubFerroviario.getMiembros()).doesNotContain(francisco);
    assertThat(amantesDelGolf.getMiembros()).doesNotContain(juan);


    //Assertions.assertEquals(clubFerroviario.getMiembros().get(0),amantesDelGolf.getMiembros().get(0));
  }

  @Test
  public void seAgregaUnAdministradorYSeElimina(){
    Comunidad clubFerroviario = new Comunidad();

    clubFerroviario.agregarAdministrador(admin);

    assertThat(clubFerroviario.getAdministradores()).containsOnly(admin);

    clubFerroviario.eliminarAdministrador(admin);

    assertThat(clubFerroviario.getAdministradores()).doesNotContain(admin);
    assertThat(clubFerroviario.getAdministradores()).isEmpty();
  }
}
