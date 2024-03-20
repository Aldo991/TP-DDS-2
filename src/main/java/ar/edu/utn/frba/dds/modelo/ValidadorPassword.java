package ar.edu.utn.frba.dds.modelo;

import ar.edu.utn.frba.dds.criteriospw.CriterioPassword;

/**
 * javadoc.
 */
public class ValidadorPassword {

  public void validatePassword(String password, CriterioPassword[] criterios) throws Exception {
    StringBuilder errors = new StringBuilder();
    for (CriterioPassword criterio : criterios) {
      try {
        criterio.cumpleCriterio(password);
      } catch (Exception exception) {
        errors.append(exception);
      }
    }

    if (errors.length() > 0) {
      throw new Exception(errors.toString());
    }
  }
}
