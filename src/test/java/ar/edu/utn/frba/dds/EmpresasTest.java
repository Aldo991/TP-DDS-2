package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.interpretes.InterpreteEmpresa;
import ar.edu.utn.frba.dds.interpretes.InterpreteEmpresaCSV;
import ar.edu.utn.frba.dds.interpretes.InterpreteEmpresaJSON;
import ar.edu.utn.frba.dds.lectores.Lector;
import ar.edu.utn.frba.dds.lectores.LectorCSV;
import ar.edu.utn.frba.dds.lectores.LectorJSON;
import ar.edu.utn.frba.dds.modelo.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmpresasTest {

    /*@Test
    public void sePuedeLeerUnaEmpresaDeUnArchivoCSVoJSON() {
        Lector lector = new LectorJSON();
        //Lector lector = new LectorCSV();

        String archivo = "entidades_y_organismos.json";
        ArrayList<List<String>> leido = lector.leerArchivo(archivo);

        System.out.println("Leidos: "+leido.size());
        leido.forEach(lista -> {
            System.out.println(">---------:" + String.valueOf(lista.size())+" lineas");
            lista.forEach(linea ->
                    System.out.println(linea)
            );
        });
    }*/

  @Test
  public void sePuedeLeerEInterpretarUnArchivoCSV() {
    Lector lector = new LectorCSV();
    ArrayList<List<String>> leido = lector.leerArchivo("entidades_y_organismos.csv");

    InterpreteEmpresa interprete = new InterpreteEmpresaCSV();

    Empresa empresa = interprete.interpretarEmpresa(leido.get(0));


    assertEquals("La colchoneta SRL", empresa.getNombreEmpresa());
    assertEquals(TipoEmpresa.ENTIDAD_PRESTADORA, empresa.getTipoDeEmpresa());
    assertEquals("lacolchoneta@gmail.com", empresa.getMail());
  }

  @Test
  public void sePuedeLeerEInterpretarUnArchivoJSON() {
    Lector lector = new LectorJSON();
    ArrayList<List<String>> leido = lector.leerArchivo("entidades_y_organismos.json");

    InterpreteEmpresa interprete = new InterpreteEmpresaJSON();

    Empresa empresa = interprete.interpretarEmpresa(leido.get(0));

    assertEquals("La colchoneta SRL", empresa.getNombreEmpresa());
    assertEquals(TipoEmpresa.ENTIDAD_PRESTADORA, empresa.getTipoDeEmpresa());
    assertEquals("lacolchoneta@gmail.com", empresa.getMail());
  }

  @Test
  public void sePuedeCargarMasivamenteMultiplesEmpresasDeUnArchivo() {
    Lector lector = new LectorCSV();
    InterpreteEmpresa interprete = new InterpreteEmpresaCSV();

    CargadorMasivoDeEmpresas cargador = new CargadorMasivoDeEmpresas(lector, interprete);

    List<Empresa> empresas = cargador.cargaMasivaDeEmpresas("entidades_y_organismos.csv");

    assertEquals(6, empresas.size());
    Empresa empresa = empresas.get(5);

    assertEquals("Ministerio publico de la defensa", empresa.getNombreEmpresa());
    assertEquals(TipoEmpresa.ORGANISMO_DE_CONTROL, empresa.getTipoDeEmpresa());
    assertEquals("ministeriopublicodeladefensa@gmail.com", empresa.getMail());

  }
}
