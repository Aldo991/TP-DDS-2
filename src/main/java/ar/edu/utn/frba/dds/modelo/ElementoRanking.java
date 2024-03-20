package ar.edu.utn.frba.dds.modelo;

public class ElementoRanking {
  Entidad entidad;
  double valor;
  int porcentaje;

  public ElementoRanking(Entidad entidad, Double valor) {
    this.entidad = entidad;
    this.valor = valor;
  }

  public int getPorcentaje() {
    return porcentaje;
  }

  public void setPorcentaje(int porcentaje) {
    this.porcentaje = porcentaje;
  }

  public Entidad getEntidad() {
    return entidad;
  }

  public Double getValor() {
    return valor;
  }

  public ElementoRanking modificar(Double valor) {
    this.valor = valor;
    return this;
  }

  public boolean esDeLaEntidad(Entidad entidad) {
    return this.entidad == entidad;
  }
}
