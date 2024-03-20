package ar.edu.utn.frba.dds.interpretes;

import ar.edu.utn.frba.dds.modelo.Empresa;
import ar.edu.utn.frba.dds.modelo.TipoEmpresa;

import java.util.List;

public class InterpreteEmpresaCSV implements InterpreteEmpresa {
  public Empresa interpretarEmpresa(List<String> lineas) {
    var campos = lineas.get(0).split(";");

    return new Empresa (
        campos[0],
        TipoEmpresa.valueOf(campos[1]),
        campos[2]);
  }
}

