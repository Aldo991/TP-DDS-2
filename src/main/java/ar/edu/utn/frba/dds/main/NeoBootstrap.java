package ar.edu.utn.frba.dds.main;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class NeoBootstrap implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void PersonasInteresantes() {
    withTransaction(() -> {


    });
  }
}
