package ar.edu.utn.frba.dds.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManipuladorCSV {

  private String path;
  private String disenio;
  private List<String> entidades = new ArrayList<>();
  private BufferedReader lector;

  public ManipuladorCSV(String path) throws IOException {
    this.path = path;
    this.ejecutarse(this.path);
  }

  private void ejecutarse(String path) throws IOException {

    String entidad = null;

    this.lector = new BufferedReader(new FileReader(path));
    disenio = this.lector.readLine();

    while( (entidad = this.lector.readLine()) != null) {
      entidades.add(entidad);
    }

    this.lector.close();
  }

  public List<String> getEntidades() {
    return entidades;
  }

  public String getDisenioCsv() {
    return disenio;
  }


}

