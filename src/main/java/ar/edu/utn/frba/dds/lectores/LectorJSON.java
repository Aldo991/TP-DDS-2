package ar.edu.utn.frba.dds.lectores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LectorJSON implements Lector {
    public ArrayList<List<String>> leerArchivo(String rutaArchivo) {
        ArrayList<List<String>> resultado = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            ArrayList<String> lineasJson = new ArrayList<String>();
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {

                if (linea.contains("},")) {
                    lineasJson.add(linea.replace("},", "}"));

                    resultado.add(lineasJson);
                    lineasJson = new ArrayList<String>();
                } else if (!linea.contains("]")) {
                    lineasJson.add(linea);
                }
            }
            resultado.add(lineasJson);

        } catch (Exception e) {
            throw new NoSePudoLeerArchivo("Error al leer archivo JSON");
        }

        return resultado;
    }
}
