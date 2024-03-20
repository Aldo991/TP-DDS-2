package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.lectores.*;
import ar.edu.utn.frba.dds.modelo.*;
import org.junit.jupiter.api.Test;

public class CargadorMasivoEmpresasTest {
  ManipuladorCSV archivo = new ManipuladorCSV("entidades_y_organismos.csv");

  public CargadorMasivoEmpresasTest() throws IOException {
  }

  @Test
  public void elCsvRespetaElOrdenDeLosDatos() {
    String disenio = archivo.getDisenioCsv();

    String[] columnas = disenio.split(";");

    assertEquals(columnas[0], "Nombre");
    assertEquals(columnas[1], "Tipo");
    assertEquals(columnas[2], "Mail");
  }

  @Test
  public void hayTresColumnasEnElCsv() {
    String disenio = archivo.getDisenioCsv();

    String[] columnas = disenio.split(";");

    assertEquals(3, columnas.length);
  }

  @Test
  public void miArchivoCsvTieneDatos() {
    List<String> entidades = archivo.getEntidades();

    assertNotEquals(0, entidades.size());
  }

  @Test
  public void laPrimeraEntidadEsLaColchonetaSRL() {
    List<String> entidades = archivo.getEntidades();

    String entidad = entidades.get(0);//cada elemento de la lista es una entidad

    String[] partes = entidad.split(";");

    assertEquals(partes[0], "La colchoneta SRL");
    assertEquals(partes[1], "ENTIDAD_PRESTADORA");
    assertEquals(partes[2], "lacolchoneta@gmail.com");
  }

  @Test
  public void sePuedeRealizarLaCargaMasivaDeUnaEmpresasCompleta() throws IOException {

    CreadorDeEmpresas creador = new CreadorDeEmpresas();//O debería ser un Singleton??

    List<Empresa> empresas = creador.cargaMasiva("entidades_y_organismos.csv");

    assertEquals(empresas.get(0).getNombreEmpresa(),"La colchoneta SRL");
    assertEquals(empresas.get(0).getTipoDeEmpresa(),TipoEmpresa.ENTIDAD_PRESTADORA);
    assertEquals(empresas.get(0).getMail(),"lacolchoneta@gmail.com");
  }

  @Test
  public void sePuedeRealizarLaCargaMasivaVariasEmpresas() throws IOException {

    CreadorDeEmpresas creador = new CreadorDeEmpresas();//O debería ser un Singleton??

    List<Empresa> empresas = creador.cargaMasiva("entidades_y_organismos.csv");

    assertEquals(empresas.get(0).getNombreEmpresa(),"La colchoneta SRL");
    assertEquals(empresas.get(1).getNombreEmpresa(),"ministerio publico fiscal");
    assertEquals(empresas.get(2).getNombreEmpresa(),"Envio expres EyE");

  }



  @Test
  public void testcsv() {
    LectorCSV lectorCSV = new LectorCSV();

    ArrayList<List<String>> leido = lectorCSV.leerArchivo("entidades_y_organismos.csv");

    for (List<String> list : leido) {
      for (String element : list) {
        System.out.print(element + " ");
      }
      System.out.println();
    }

  }
}
