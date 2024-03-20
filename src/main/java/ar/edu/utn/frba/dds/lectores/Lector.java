package ar.edu.utn.frba.dds.lectores;

import java.util.ArrayList;
import java.util.List;

public interface Lector {
  //TODO Manejar excepciones de Dominio al leer el archivo
  ArrayList<List<String>> leerArchivo(String rutaArchivo);
}
