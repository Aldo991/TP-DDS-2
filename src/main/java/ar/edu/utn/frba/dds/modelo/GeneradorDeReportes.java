package ar.edu.utn.frba.dds.modelo;

import ar.edu.utn.frba.dds.CriterioRanking.Ranking;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorDeReportes {
  public static GeneradorDeReportes instancia;

  //----SINGLETON----
  public static GeneradorDeReportes instancia(){
    if (instancia == null) {
      instancia = new GeneradorDeReportes();
    }
    return instancia;
  }

  public String generarReporte(Ranking ranking, String fileName) throws IOException {
    FileWriter fw = new FileWriter(fileName);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write("Entidad;Valor\n");
    bw.flush();

    for (ElementoRanking elemento : ranking.getResultado()) {
      String lineToWrite = elemento.getEntidad().getNombreEntidad() + ";" + elemento.getValor().toString();
      bw.write(lineToWrite + "\n");
      bw.flush();
    }
    return fileName;
  }
}

