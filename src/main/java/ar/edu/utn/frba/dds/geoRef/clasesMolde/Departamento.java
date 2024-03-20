package ar.edu.utn.frba.dds.geoRef.clasesMolde;

import ar.edu.utn.frba.dds.modelo.Localizacion;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("departamento")
public class Departamento extends Localizacion {
    //----EQUALS----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departamento)) return false;
        Departamento that = (Departamento) o;
        return id == that.id && nombre.equals(that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

}
