package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.geoRef.GeoRef;
import ar.edu.utn.frba.dds.geoRef.RepoProvincias;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GeoRefTest {
  //@Mock
  private GeoRef mockGeoref = mock(GeoRef.class);//ServicioGeoref.instancia();
  private Random random = new Random();


  @Test
  public void  sePuedeObtenerUnaProvincia() throws IOException {
    RepoProvincias repo = RepoProvincias.instacia(mockGeoref);

    System.out.println(repo.getProvincias().size());

    Provincia baires = repo.buscarProvincia("Buenos Aires");
    assertNotEquals(0,repo.getProvincias().size());
    //assertEquals(1,repo.getProvincias().size());

    System.out.println(baires.getId_api()+": "+baires.getNombre());
    System.out.println("lat: "+baires.getLat()+" lon: "+baires.getLon() );
  }

  @Test
  public void  sePuedeObtenerUnMunicipio() throws IOException {
    RepoProvincias repo = RepoProvincias.instacia(mockGeoref);

    Municipio bahia = repo.buscarMunicipio(6,"Bahía Blanca");

    assertNotEquals(0,repo.getProvincias().size());
    //assertEquals(1,repo.getProvincias().size());
    assertNotNull(bahia);

    System.out.println(bahia.getId_api()+": "+bahia.getNombre());
    System.out.println("lat: "+bahia.getLat()+" lon: "+bahia.getLon() );

  }
  @Test
  public void  sePuedeObtenerUnDepartamento() throws IOException {
    RepoProvincias repo = RepoProvincias.instacia(mockGeoref);
    repo.cargarProvincias();

    Departamento tandil = repo.buscarDepartamento(6,"Tandil");

    assertNotNull(tandil);

    System.out.println(tandil.getId_api()+": "+tandil.getNombre());
    System.out.println("lat: "+tandil.getLat()+" lon: "+tandil.getLon() );
  }

  @BeforeEach
  public void mockSetUp() throws IOException {
    //----BUSCAR PROVINCIA----
    //Cuando se llama a BuscarProvincia(nombreProvincia)

    when(mockGeoref.BuscarProvincia(anyString())).thenAnswer(invocation ->{
      String nombre = (String) invocation.getArgument(0);
      //Crea una provincia nueva con id al azar y el nombre de la provincia
      Provincia provincia = new Provincia();
      provincia.setNombre(nombre);
      provincia.setId_api((int) nombre.hashCode()%100);//Se usa un resto del Hash para que una provincia tenga siempre el mismo ID

      Centroide centroide = new Centroide();
      centroide.lat= (float) random();
      centroide.lon= (float) random();
      provincia.setCentroide(centroide);


      return provincia;
    });

    when(mockGeoref.BuscarProvincia(anyInt())).thenAnswer(invocation ->{
      int id = (int) invocation.getArgument(0);
      //Crea una provincia nueva con un nombre falso y el id pasado por parametro
      Provincia provincia = new Provincia();
      provincia.setNombre("Poovincia Mock");
      provincia.setId_api(id);

      Centroide centroide = new Centroide();
      centroide.lat= (float) random();
      centroide.lon= (float) random();
      provincia.setCentroide(centroide);


      return provincia;
    });

    //----BUSCAR MUNICIPIO----

    when(mockGeoref.buscarMunicipios(anyInt(),anyString())).thenAnswer(invocation ->{
      String nombre = (String) invocation.getArgument(1);

      Municipio municipio = new Municipio();
      municipio.setNombre(nombre);
      municipio.setId_api((int) nombre.hashCode()%100);

      Centroide centroide = new Centroide();
      centroide.lat= (float) random();
      centroide.lon= (float) random();
      municipio.setCentroide(centroide);

      ArrayList<Municipio> municipios = new ArrayList<>();
      municipios.add(municipio);

      return municipios;
    });

    //----BUSCAR DEPARTAMENTO----

    when(mockGeoref.buscarDepartamentos(anyInt(),anyString())).thenAnswer(invocation ->{
      String nombre = (String) invocation.getArgument(1);

      Departamento departamento = new Departamento();
      departamento.setNombre(nombre);
      departamento.setId_api((int) nombre.hashCode()%100);

      Centroide centroide = new Centroide();
      centroide.lat= (float) random();
      centroide.lon= (float) random();
      departamento.setCentroide(centroide);

      ArrayList<Departamento> departamentos = new ArrayList<>();
      departamentos.add(departamento);

      return departamentos;
    });
  }
}
