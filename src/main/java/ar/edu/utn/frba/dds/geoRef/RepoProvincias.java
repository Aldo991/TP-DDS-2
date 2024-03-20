package ar.edu.utn.frba.dds.geoRef;

import ar.edu.utn.frba.dds.geoRef.clasesMolde.Departamento;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Municipio;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Provincia;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.io.IOException;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepoProvincias implements WithSimplePersistenceUnit {
  private Set<Provincia> provincias = new HashSet<Provincia>();
  private static RepoProvincias repoProvincias = null;
  private static GeoRef geoRef;

  //-----SINGLETON-----
  private RepoProvincias() {

  }
  public static RepoProvincias instacia(GeoRef newGeoRef) {
    if(repoProvincias == null) {
      repoProvincias = new RepoProvincias();
    }
    geoRef = newGeoRef;
    return repoProvincias;
  }

  //----PROVINCIAS----

  public void cargarProvincias() throws IOException {
    provincias = new HashSet<Provincia>(geoRef.provincias());
    provincias.forEach(provincia -> provincia.setNombre(normalizarTexto(provincia.getNombre())));
    provincias.forEach(provincia -> guardarProvincia(provincia));
  }
  public Provincia buscarProvincia(String nombreProvincia) throws IOException {
    Provincia provincia = (Provincia) provincias.stream()
        .filter(provincia1 -> provincia1.getNombre().equals(normalizarTexto(nombreProvincia)))
        .findFirst().orElse(null);

    if(provincia==null){
      provincia = geoRef.BuscarProvincia(nombreProvincia);
      provincias.add(provincia);
    }

    return provincia;
  }
  private Provincia buscarProvincia(int id) throws IOException {
    Provincia provincia = provincias.stream()
        .filter(p -> p.getId_api() == id)
        .findFirst().orElse(null);
    if(provincia==null){
      provincia = geoRef.BuscarProvincia(id);
      provincias.add(provincia);
    }
    return provincia;
  }
  public Set<Provincia> getProvincias() {
    return provincias;
  }

  public Municipio buscarMunicipio(int idProvincia, String municipio) throws IOException {
    Provincia provincia = buscarProvincia(idProvincia);
    if (provincia.existeDepartamento(municipio)) {
      return provincia.buscarMunicipio(municipio);
    }
    Municipio municipioNuevo = geoRef.buscarMunicipios(idProvincia, municipio).get(0);

    provincia.agregarMunicipio(municipioNuevo);
    guardarMunicipio(municipioNuevo);

    return municipioNuevo;
  }

  public Departamento buscarDepartamento(int idProvincia, String departamento) throws IOException {
    Provincia provincia = buscarProvincia(idProvincia);
    if (provincia.existeDepartamento(departamento)) {
      return provincia.buscarDepartamento(departamento);
    }
    Departamento departamentoNuevo= geoRef.buscarDepartamentos(idProvincia, departamento).get(0);

    provincia.agregarDepartamento(departamentoNuevo);
    guardarDepartamento(departamentoNuevo);

    return departamentoNuevo;
  }
  private String normalizarTexto(String texto) {
    String texto1 = Normalizer.normalize(texto, Normalizer.Form.NFD);
    return texto1.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
        .toLowerCase();
  }
  private void guardarProvincia(Provincia provincia) {
    entityManager().persist(provincia);
  }
  private void guardarDepartamento(Departamento departamento) {
    entityManager().persist(departamento);
  }
  private void guardarMunicipio(Municipio municipio) {
    entityManager().persist(municipio);
  }

  private List<Departamento> buscarDepartamentosEnDB(Provincia provincia, Departamento departamento) {
     return entityManager()
         .createQuery("from Localizacion " +
             "where provincia_id = :provincia and nombre = :nombre and discriminador = 'departamento'")
         .setParameter("nombre", departamento.getNombre())
         .setParameter("provincia", provincia.getId_Db())
         .getResultList();
  }
  private List<Departamento> buscarMunicipioEnDB(Provincia provincia, Departamento departamento) {
    return entityManager()
        .createQuery("from Localizacion " +
            "where provincia_id = :provincia and nombre = :nombre and discriminador = 'municipio'")
        .setParameter("nombre", departamento.getNombre())
        .setParameter("provincia", provincia.getId_Db())
        .getResultList();
  }
}
