package ar.edu.utn.frba.dds.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreadorDeEmpresas {


  private Empresa generarEmpresa(String entidad){

    String[] elementos = entidad.split(";");

    return new Empresa(elementos[0],
        TipoEmpresa.valueOf(elementos[1]),
        elementos[2]);
  }

  public List<Empresa> cargaMasiva(String path) throws IOException {

    ManipuladorCSV manipulador = new ManipuladorCSV(path);
    return manipulador.getEntidades().stream().map(e -> generarEmpresa((e))).toList();

  }
}
