package Network;

import java.util.List;

import Models.PersonaAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServicioSalud {
   @GET("Personas")
   Call<List<PersonaAPI>> GetPersonas();
   @POST("Personas")
   Call<PersonaAPI> PostPersonas(@Body PersonaAPI persona);

}
