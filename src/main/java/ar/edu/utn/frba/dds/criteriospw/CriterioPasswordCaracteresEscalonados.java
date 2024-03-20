package ar.edu.utn.frba.dds.criteriospw;

/**
 * javadoc.
 */
public class CriterioPasswordCaracteresEscalonados extends CriterioPassword {

  /**
   * javadoc.
   */
  public boolean testFallaCriterio(String password) {
    for (int i = 0; i < password.length() - 3; i++) {
      if (password.charAt(i) + 1 == password.charAt(i + 1)
          && password.charAt(i + 1) + 1 == password.charAt(i + 2)
          && password.charAt(i + 2) + 1 == password.charAt(i + 3)) {
        return true;
      }
    }
    return false;
  }

  public String getErrorMsg() {
    return "Password contiene caracteres escalonados.\n";
  }
}
