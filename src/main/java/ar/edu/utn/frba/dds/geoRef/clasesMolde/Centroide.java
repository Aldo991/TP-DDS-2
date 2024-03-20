package ar.edu.utn.frba.dds.geoRef.clasesMolde;

import javax.persistence.Embeddable;

@Embeddable
public class Centroide {
    public float lat;
    public float lon;
    public Centroide() {

    }
    public Centroide(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
