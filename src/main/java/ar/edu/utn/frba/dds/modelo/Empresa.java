package ar.edu.utn.frba.dds.modelo;


import javax.persistence.*;

/**
 * javadoc.
 */
@Entity
public class Empresa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  public String nombreEmpresa;

  @Enumerated(EnumType.STRING)
  public TipoEmpresa tipoDeEmpresa;
  public String mail;

  public Empresa() {
  }

  public Empresa(String nombre, TipoEmpresa tipoDeEmpresa, String mail) {
    this.nombreEmpresa = nombre;
    this.tipoDeEmpresa = tipoDeEmpresa;
    this.mail = mail;
  }

  public String getNombreEmpresa() {
    return nombreEmpresa;
  }

  public TipoEmpresa getTipoDeEmpresa() {
    return tipoDeEmpresa;
  }

  public String getMail() {
    return mail;
  }
}

