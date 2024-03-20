package ar.edu.utn.frba.dds.geoRef;

import ar.edu.utn.frba.dds.geoRef.clasesMolde.ListadoDeDepartamentos;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.geoRef.clasesMolde.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface GeoRefService {
  @GET("provincias")
  Call<ListadoDeProvincias> provincias();
  @GET("provincias")
  Call<ListadoDeProvincias> provincias(@Query("id") int id);

  @GET("provincias")
  Call<ListadoDeProvincias> provincias(@Query("nombre") String nombre);
  @GET("municipios")
  Call<ListadoDeMunicipios> municipios(@Query("provincia") int id,
                                       @Query("nombre") String nombre);
  @GET("departamentos")
  Call<ListadoDeDepartamentos> departamentos(@Query("provincia") int id,
                                             @Query("nombre") String nombre,
                                             @Query("exacto") String exctitud);
}
