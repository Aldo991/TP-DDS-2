package ar.edu.utn.frba.dds.geoRef;

import ar.edu.utn.frba.dds.geoRef.clasesMolde.Departamento;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.ListadoDeDepartamentos;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.ListadoDeProvincias;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Municipio;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.Provincia;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioGeoref implements GeoRef {
  private static ServicioGeoref instancia = null;
  private static final String urlApi = "https://apis.datos.gob.ar/georef/api/";
  private Retrofit retrofit;

  private ServicioGeoref() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static ServicioGeoref instancia(){
    if(instancia== null){
      instancia = new ServicioGeoref();
    }
    return instancia;
  }

  @Override
  public List<Provincia> provincias() throws IOException {
    GeoRefService georefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeProvincias> requestProvinciasArgentinas = georefService.provincias();
    Response<ListadoDeProvincias> responseProvinciasArgentinas = requestProvinciasArgentinas.execute();

    return responseProvinciasArgentinas.body().provincias;
  }

  @Override
  public Provincia BuscarProvincia(int idProvincia) throws IOException{
    GeoRefService georefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeProvincias> requestProvinciasArgentinas = georefService.provincias(idProvincia);
    Response<ListadoDeProvincias> responseProvinciasArgentinas = requestProvinciasArgentinas.execute();
    return responseProvinciasArgentinas.body().provincias.get(0);
  }

  @Override
  public Provincia BuscarProvincia(String nombreProvincia) throws IOException{
    GeoRefService georefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeProvincias> requestProvinciasArgentinas = georefService.provincias(nombreProvincia);
    Response<ListadoDeProvincias> responseProvinciasArgentinas = requestProvinciasArgentinas.execute();
    if(responseProvinciasArgentinas.body()==null){
      throw new IOException("Limite de API Superado");
    }
    return responseProvinciasArgentinas.body().provincias.get(0);
  }
  @Override
  public List<Municipio> buscarMunicipios(int idProvincia, String municipio) throws IOException {
    GeoRefService georefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeMunicipios> requestMinicipios = georefService.municipios(idProvincia,municipio);
    Response<ListadoDeMunicipios> respuestaResultadoMunicipios = requestMinicipios.execute();
    System.out.println(respuestaResultadoMunicipios.code());
    return respuestaResultadoMunicipios.body().municipios;
  }

  @Override
  public List<Departamento> buscarDepartamentos(int idProvincia, String departamento) throws IOException {
    GeoRefService georefService = this.retrofit.create(GeoRefService.class);
    Call<ListadoDeDepartamentos> requestMinicipios = georefService.departamentos(idProvincia,departamento,"true");
    Response<ListadoDeDepartamentos> respuestaResultadoDepartamentos = requestMinicipios.execute();
    System.out.println(respuestaResultadoDepartamentos.code());
    return respuestaResultadoDepartamentos.body().departamentos;
  }
}
