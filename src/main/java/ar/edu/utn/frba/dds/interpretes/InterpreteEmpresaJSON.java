package ar.edu.utn.frba.dds.interpretes;

import ar.edu.utn.frba.dds.modelo.Empresa;
import com.google.gson.Gson;

import java.util.List;

public class InterpreteEmpresaJSON implements InterpreteEmpresa {
  public Empresa interpretarEmpresa(List<String> lineas) {
    String json = String.join("", lineas);
    Gson gson = new Gson();

    return gson.fromJson(json, Empresa.class);
  }
}
