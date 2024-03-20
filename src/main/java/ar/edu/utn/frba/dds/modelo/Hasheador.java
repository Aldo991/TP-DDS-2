package ar.edu.utn.frba.dds.modelo;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasheador {

  MessageDigest md;

  public Hasheador() throws NoSuchAlgorithmException {
     md = MessageDigest.getInstance("MD5");
  }

}
