package ar.edu.utn.frba.dds.criteriospw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * javadoc.
 */
public class CriterioPasswordFuerte extends CriterioPassword {

  /**
   * javadoc.
   */
  public boolean testFallaCriterio(String password) {
    Pattern pattern;
    Matcher matcher;

    pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=/.!,?-])(?=.*[A-Z])\\S*");
    matcher = pattern.matcher(password);

    return !matcher.find();
  }

  public String getErrorMsg() {
    return "Password no es fuerte.\n";
  }
}
