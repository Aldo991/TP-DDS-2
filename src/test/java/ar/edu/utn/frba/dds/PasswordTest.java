package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.criteriospw.*;
import ar.edu.utn.frba.dds.modelo.ValidadorPassword;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;

public class PasswordTest {

  // Criterios Password
  CriterioPasswordCaracteresEscalonados criterioPasswordCaracteresEscalonados = new CriterioPasswordCaracteresEscalonados();
  CriterioPasswordCaracteresRepetidos criterioPasswordCaracteresRepetidos = new CriterioPasswordCaracteresRepetidos();
  CriterioPasswordFuerte criterioPasswordFuerte = new CriterioPasswordFuerte();
  CriterioPasswordLongitud criterioPasswordLongitud = new CriterioPasswordLongitud();
  CriterioPasswordLista10K criterioPasswordLista10K = new CriterioPasswordLista10K();

  BufferedReader mockBufferedReader;

  @BeforeEach
  void initMocks() {
    mockBufferedReader = mock(BufferedReader.class);
  }

  @Test
  public void validarPasswordCaracteresEscalonados() {
    Assertions.assertDoesNotThrow(() -> criterioPasswordCaracteresEscalonados.cumpleCriterio("asdf"));
    Assertions.assertThrows(Exception.class, () -> criterioPasswordCaracteresEscalonados.cumpleCriterio("abcd"));
  }

  @Test
  public void validarPasswordCaracteresRepetidos() {
    Assertions.assertDoesNotThrow(() -> criterioPasswordCaracteresRepetidos.cumpleCriterio("asdfghjk"));
    Assertions.assertThrows(Exception.class, () -> criterioPasswordCaracteresRepetidos.cumpleCriterio("aaaabcd"));
  }

  @Test
  public void validarPasswordFuerte() {
    Assertions.assertDoesNotThrow(() -> criterioPasswordFuerte.cumpleCriterio("myPa$$word1"));
    Assertions.assertThrows(Exception.class, () -> criterioPasswordFuerte.cumpleCriterio("myPassword1"));
  }

  @Test
  public void validarPasswordLongitud() {
    Assertions.assertDoesNotThrow(() -> criterioPasswordLongitud.cumpleCriterio("asdfghjk"));
    Assertions.assertThrows(Exception.class, () -> criterioPasswordLongitud.cumpleCriterio("asdf"));
    Assertions.assertThrows(Exception.class, () -> criterioPasswordLongitud.cumpleCriterio("asdfghjklzxcvbnmqwe"));
  }

  @Test
  public void validarPasswordLista10K() throws IOException {
    criterioPasswordLista10K.setBufferedReader(mockBufferedReader);

    // Repetido para probar ambos casos al mismo tiempo
    when(mockBufferedReader.readLine())
        .thenReturn("123456")
        .thenReturn("password")
        .thenReturn("asdfgh")
        .thenReturn(null)
        .thenReturn("123456")
        .thenReturn("password")
        .thenReturn("asdfgh")
        .thenReturn(null);

    Assertions.assertDoesNotThrow(() -> criterioPasswordLista10K.cumpleCriterio("klajsdfñkljas"));
    Assertions.assertThrows(Exception.class, () -> criterioPasswordLista10K.cumpleCriterio("asdfgh"));
  }

  @Test
  public void validarContraseniaConCriterios() {
    ValidadorPassword validador = new ValidadorPassword();

    CriterioPassword[] criterios = new CriterioPassword[2];
    criterios[0] = criterioPasswordCaracteresEscalonados ;
    criterios[1] = criterioPasswordLongitud;

    Assertions.assertDoesNotThrow(() -> validador.validatePassword("asddfghj", criterios));
    Assertions.assertThrows(Exception.class, () -> validador.validatePassword("asdfgh", criterios));
    try{
      validador.validatePassword("asdfgh", criterios);
    }catch(Exception e) {
      System.out.println(e.toString());
    }
  }

  @Test
  public void validarContraseniaConMasCriterios() {
    ValidadorPassword validador = new ValidadorPassword();

    CriterioPassword[] criterios = new CriterioPassword[4];
    criterios[0] = criterioPasswordCaracteresEscalonados ;
    criterios[1] = criterioPasswordCaracteresRepetidos;
    criterios[2] = criterioPasswordFuerte;
    criterios[3] = criterioPasswordLongitud;

    Assertions.assertDoesNotThrow(() -> validador.validatePassword("myPa$$word1", criterios));
    Assertions.assertThrows(Exception.class, () -> validador.validatePassword("abcdddd", criterios));
    try{
      validador.validatePassword("abcdddd", criterios);
    }catch(Exception e) {
      System.out.println(e.toString());
    }
  }
}
