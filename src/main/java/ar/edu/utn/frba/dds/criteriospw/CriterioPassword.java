package ar.edu.utn.frba.dds.criteriospw;

import java.io.IOException;

/**
 * javadoc.
 */
public abstract class CriterioPassword {
  int minPasswordLength = 8;
  int maxPasswordLength = 16;

  /**
   * javadoc.
   */
  public void cumpleCriterio(String password) throws Exception {
    if (testFallaCriterio(password)) {
      throw new FalloCriterioPassword(getErrorMsg());
    }
  }

  public abstract boolean testFallaCriterio(String password) throws IOException;

  public abstract String getErrorMsg();
}
