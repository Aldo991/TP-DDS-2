package ar.edu.utn.frba.dds.criteriospw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * javadoc.
 */
public class CriterioPasswordCaracteresRepetidos extends CriterioPassword {

  /**
   * javadoc.
   */
  public boolean testFallaCriterio(String password) {
    Pattern pattern;
    Matcher matcher;

    pattern = Pattern.compile("(.)\\1{3}");
    matcher = pattern.matcher(password);

    return matcher.find();
  }

  public String getErrorMsg() {
    return "Password contiene caracteres repetidos.\n";
  }
}
