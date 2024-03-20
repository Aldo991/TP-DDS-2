package ar.edu.utn.frba.dds.interpretes;

import ar.edu.utn.frba.dds.modelo.Empresa;

import java.util.List;

public interface InterpreteEmpresa {
  Empresa interpretarEmpresa(List<String> lineas);
}
