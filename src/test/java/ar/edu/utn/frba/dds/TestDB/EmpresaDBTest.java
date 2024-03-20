package ar.edu.utn.frba.dds.TestDB;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.modelo.CreadorDeEmpresas;
import ar.edu.utn.frba.dds.modelo.Empresa;
import ar.edu.utn.frba.dds.modelo.TipoEmpresa;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EmpresaDBTest implements SimplePersistenceTest {

  //implements WithSimplePersistenceUnit

  @Test
  public void guardarUnaEmpresa() {
    Empresa empresa = new Empresa("Edesur", TipoEmpresa.ENTIDAD_PRESTADORA, "edesur@servicio.com");

    entityManager().persist(empresa);

  }

  @Test

  public void guardarTodasLasEmpresas() throws IOException {

    List<Empresa> empresas = (new CreadorDeEmpresas()).cargaMasiva("entidades_y_organismos.csv");

    empresas.forEach((emp -> {entityManager().persist(emp);}));

  }

  @Test
  public void TraerLasEntidadPrestadoras() throws IOException {
    List<Empresa> empresas = (new CreadorDeEmpresas()).cargaMasiva("entidades_y_organismos.csv");
    empresas.forEach((emp -> {entityManager().persist(emp);}));

    List<Empresa> empresaTraida = (List<Empresa>) entityManager().createQuery("FROM " + Empresa.class.getName() + " WHERE tipoDeEmpresa = 'ENTIDAD_PRESTADORA'").getResultList();

    empresaTraida.forEach((emp -> {System.out.println(emp.getNombreEmpresa());}));
  }

  @Test
  public void traerUnaEmpresa() throws IOException {

    Empresa empresa = new Empresa("Edesur", TipoEmpresa.ENTIDAD_PRESTADORA, "edesur@servicio.com");
    entityManager().persist(empresa);

    /*    Otra forma de traer una empresa
    Empresa empresasTraidas = (Empresa) entityManager().createQuery("FROM " + Empresa.class.getName() +
        " WHERE nombreEmpresa = 'Edesur' ").getSingleResult(); */

    Empresa empresasTraidas = entityManager().find(Empresa.class, (Long)1L);

    assertEquals("Edesur", empresasTraidas.getNombreEmpresa());
  }
}
