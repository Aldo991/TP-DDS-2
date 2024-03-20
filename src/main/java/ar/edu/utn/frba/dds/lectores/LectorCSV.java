package ar.edu.utn.frba.dds.lectores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV implements Lector {
  public ArrayList<List<String>> leerArchivo(String rutaArchivo) {
    ArrayList<List<String>> resultado = new ArrayList<>();
    //TODO manejar excepciones de dominio

    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
      String linea = br.readLine();//Se lee la primera linea, que es de encabezados

      while ((linea = br.readLine()) != null) {
        resultado.add(List.of(linea));
      }
    } catch (Exception e) {
      throw new NoSePudoLeerArchivo("Error al leer archivo CSV");
    }

    return resultado;
  }
}
