package ar.edu.utn.frba.dds.modelo;

import javax.persistence.*;

/**
 * javadoc.
 */
@Entity
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;
  @Enumerated(EnumType.STRING)
  private Rol rol;


  public Usuario() {
  }

  public Usuario(String username, String password, Rol rol) {
    this.username = username;
    this.password = password;
    this.rol = rol;
  }

  public Usuario(String username) {
    this.username = username;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Rol getRol() {
    return rol;
  }

  public Usuario setRol(Rol rol) {
    this.rol = rol;
    return this;
  }
}
