package ar.edu.utn.frba.dds.TestDB;

import ar.edu.utn.frba.dds.modelo.Localizacion;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Centroide;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Departamento;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Municipio;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Provincia;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class LocalizacionDBTest implements SimplePersistenceTest {

  private Provincia provincia1;
  private Provincia provincia2;
  private Departamento departamento1;
  private Departamento departamento2;
  private Departamento departamento3;
  private Municipio municipio1;
  private Municipio municipio2;
  private Municipio municipio3;
  private Municipio municipio4;

  @BeforeEach
  public void inicio() {
    provincia1 = new Provincia();
    provincia2 = new Provincia();
    departamento1 = new Departamento();
    departamento2 = new Departamento();
    departamento3 = new Departamento();
    municipio1 = new Municipio();
    municipio2 = new Municipio();
    municipio3 = new Municipio();
    municipio4 = new Municipio();

    provincia1.setNombre("caba");
    provincia2.setNombre("cordoba");
    provincia1.setId_api(5);
    provincia2.setId_api(4);
    provincia1.setCentroide(new Centroide(2,5));
    provincia2.setCentroide(new Centroide(3,5));

    departamento1.setNombre("dep1");
    departamento2.setNombre("dep2");
    departamento3.setNombre("dep3");
    departamento1.setId_api(1);
    departamento2.setId_api(2);
    departamento3.setId_api(3);
    departamento1.setCentroide(new Centroide(1,1));
    departamento2.setCentroide(new Centroide(1,2));
    departamento3.setCentroide(new Centroide(1,3));

    municipio1.setNombre("muni1");
    municipio2.setNombre("muni2");
    municipio3.setNombre("muni3");
    municipio4.setNombre("muni4");
    municipio1.setId_api(6);
    municipio2.setId_api(7);
    municipio3.setId_api(8);
    municipio4.setId_api(9);
    municipio1.setCentroide(new Centroide(2,1));
    municipio2.setCentroide(new Centroide(2,2));
    municipio3.setCentroide(new Centroide(2,3));
    municipio4.setCentroide(new Centroide(2,4));
  }

  @Test
  public void guardarUnaLocalizacion() {
    Localizacion loc1 = provincia1;
    Localizacion loc2 = departamento1;
    Localizacion loc3 = municipio1;

    entityManager().persist(loc1);
    entityManager().persist(loc2);
    entityManager().persist(loc3);

    Localizacion _loc1 = entityManager().find(Localizacion.class, loc1.getId_Db());
    Localizacion _loc2 = entityManager().find(Localizacion.class, loc2.getId_Db());
    Localizacion _loc3 = entityManager().find(Localizacion.class, loc3.getId_Db());

    Assertions.assertEquals(_loc1, loc1);
    Assertions.assertEquals(_loc2, loc2);
    Assertions.assertEquals(_loc3, loc3);
  }
  @Test
  public void guardarProvinciaConMunicipios() {
    provincia1.agregarMunicipio(municipio1);
    provincia1.agregarMunicipio(municipio2);
    Localizacion loc = provincia1;

    entityManager().persist(municipio1);
    entityManager().persist(municipio2);
    entityManager().persist(loc);

    Localizacion _loc = entityManager().find(Localizacion.class, loc.getId_Db());

    Assertions.assertEquals(_loc, loc);
  }
  @Test
  public void guardarProvinciaConDepartamentos() {
    provincia1.agregarDepartamento(departamento1);
    provincia1.agregarDepartamento(departamento2);
    Localizacion loc = provincia1;

    entityManager().persist(departamento1);
    entityManager().persist(departamento2);
    entityManager().persist(loc);

    Localizacion _loc = entityManager().find(Localizacion.class, loc.getId_Db());

    Assertions.assertEquals(_loc, loc);
  }
  @Test
  public void guardarProvinciaConDepartamentosYMunicipios() {
    provincia1.agregarDepartamento(departamento1);
    provincia1.agregarDepartamento(departamento2);
    provincia1.agregarMunicipio(municipio1);
    provincia1.agregarMunicipio(municipio2);

    Localizacion loc = provincia1;

    entityManager().persist(municipio1);
    entityManager().persist(municipio2);

    entityManager().persist(departamento1);
    entityManager().persist(departamento2);
    entityManager().persist(loc);

    Localizacion _loc = entityManager().find(Localizacion.class, loc.getId_Db());

    Assertions.assertEquals(_loc, loc);
  }
  @Test
  public void guardarProvinciasConDepartamentosYMunicipios() {
    provincia1.agregarDepartamento(departamento1);
    provincia1.agregarDepartamento(departamento2);
    provincia1.agregarMunicipio(municipio1);
    provincia1.agregarMunicipio(municipio2);

    provincia2.agregarDepartamento(departamento3);
    provincia2.agregarMunicipio(municipio3);
    provincia2.agregarMunicipio(municipio4);

    Localizacion loc1 = provincia1;
    Localizacion loc2 = provincia2;

    entityManager().persist(municipio1);
    entityManager().persist(municipio2);
    entityManager().persist(municipio3);
    entityManager().persist(municipio4);

    entityManager().persist(departamento1);
    entityManager().persist(departamento2);
    entityManager().persist(departamento3);

    entityManager().persist(loc1);
    entityManager().persist(loc2);

    Localizacion _loc1 = entityManager().find(Localizacion.class, loc1.getId_Db());
    Localizacion _loc2 = entityManager().find(Localizacion.class, loc2.getId_Db());

    Assertions.assertEquals(_loc1, loc1);
    Assertions.assertEquals(_loc2, loc2);
  }
}
