package ar.edu.utn.frba.dds.criteriospw;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * javadoc.
 */
public class CriterioPasswordLista10K extends CriterioPassword {
  private BufferedReader bufferedReader;

  public void setBufferedReader(BufferedReader bufferReader) {
    this.bufferedReader = bufferReader;
  }

  /**
   * javadoc.
   */
  public boolean testFallaCriterio(String password) throws IOException {
    String fila;

    while ((fila = bufferedReader.readLine()) != null) {
      if (fila.equals(password)) {
        return true;
      }
    }
    return false;
  }

  public String getErrorMsg() {
    return "Password pertenece a lista 10k.\n";
  }
}
