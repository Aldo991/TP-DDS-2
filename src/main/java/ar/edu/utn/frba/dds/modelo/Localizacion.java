package ar.edu.utn.frba.dds.modelo;

import ar.edu.utn.frba.dds.geoRef.clasesMolde.Centroide;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

/**
 * javadoc.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminador")
public abstract class Localizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id_db;
    protected int id;
    protected String nombre;
    @Embedded
    protected Centroide centroide;
    @Enumerated(EnumType.ORDINAL)
    protected TipoLocalizacion tipo;

    public Long getId_Db() {
        return id_db;
    }

    public int getId_api() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getLat() {
        return centroide.lat;
    }
    public float getLon() {
        return centroide.lon;
    }

    public TipoLocalizacion getTipo() {
        return tipo;
    }

    public void setId_api(int id_api) {
        this.id = id_api;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCentroide(Centroide centroide) {
        this.centroide = centroide;
    }
}
