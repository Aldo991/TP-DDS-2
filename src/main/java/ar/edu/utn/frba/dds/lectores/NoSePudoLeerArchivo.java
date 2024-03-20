package ar.edu.utn.frba.dds.lectores;

/**
 * javadoc.
 */
public class NoSePudoLeerArchivo extends RuntimeException {
  public NoSePudoLeerArchivo(String mensajeError) {
      super(mensajeError);
    }
}
