package ar.edu.utn.frba.dds.geoRef.clasesMolde;

import ar.edu.utn.frba.dds.modelo.Localizacion;

import java.util.*;
import javax.persistence.*;

@Entity
@DiscriminatorValue("provincia")
public class Provincia extends Localizacion {
    @OneToMany
    @JoinColumn(name = "id_provincia")
    private Set<Municipio> municipios = new HashSet<>();
    @OneToMany
    @JoinColumn(name = "id_provincia")
    private Set<Departamento> departamentos = new HashSet<>();

    //----FUNCIONAMIENTO PROVINCIA----

    public Municipio buscarMunicipio(String nombre) {
        return municipios.stream()
                .filter(municipio -> municipio.getNombre().equals(nombre))
                .findFirst().get();
    }
    public Departamento buscarDepartamento(String nombre) {
        return departamentos.stream()
                .filter(municipio -> municipio.getNombre().equals(nombre))
                .findFirst().get();
    }
    public boolean existeMunicipio(String nombre) {
        return municipios.stream()
                .anyMatch(municipio -> municipio.getNombre().equals(nombre));
    }
    public boolean existeDepartamento(String nombre) {
        return departamentos.stream()
                .anyMatch(departamento -> departamento.getNombre().equals(nombre));
    }
    public void agregarMunicipio(Municipio municipio) {
        municipios.add(municipio);
    }
    public void agregarDepartamento(Departamento departamento) {
        departamentos.add(departamento);
    }

    //----TEST----
    public Set<Municipio> getMunicipios() {
        return municipios;
    }
    public Set<Departamento> getDepartamentos() {
        return departamentos;
    }

    //----EQUALS----
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provincia)) return false;
        Provincia provincia = (Provincia) o;
        return id == provincia.id && nombre.equals(provincia.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
