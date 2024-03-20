package ar.edu.utn.frba.dds.criteriospw;

/**
 * javadoc.
 */
public class CriterioPasswordLongitud extends CriterioPassword {

  /**
   * javadoc.
   */

  public CriterioPasswordLongitud(int min,int max){
    this.minPasswordLength=min;
    this.maxPasswordLength=max;
  }
  public boolean testFallaCriterio(String password) {
    return password.length() < minPasswordLength || password.length() > maxPasswordLength;
  }

  public String getErrorMsg() {
    return "Password no tiene la longitud esperada.\n";
  }
}
