package ar.edu.utn.frba.dds.geoRef.clasesMolde;

import ar.edu.utn.frba.dds.modelo.Localizacion;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("municipio")
public class Municipio extends Localizacion {
    //----EQUALS----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Municipio)) return false;
        Municipio municipio = (Municipio) o;
        return id == municipio.id && nombre.equals(municipio.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

}
