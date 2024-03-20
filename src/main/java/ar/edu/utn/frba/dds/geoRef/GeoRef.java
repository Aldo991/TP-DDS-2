package ar.edu.utn.frba.dds.geoRef;

import ar.edu.utn.frba.dds.geoRef.clasesMolde.Departamento;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Municipio;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Provincia;
import java.io.IOException;
import java.util.List;

public interface GeoRef {
  public List<Provincia> provincias() throws IOException;
  public Provincia BuscarProvincia(int idProvincia) throws IOException;
  public Provincia BuscarProvincia(String nombreProvincia) throws IOException;
  public List<Municipio> buscarMunicipios(int idProvincia, String municipio) throws IOException;
  public List<Departamento> buscarDepartamentos(int idProvincia, String departamento) throws IOException;
}
