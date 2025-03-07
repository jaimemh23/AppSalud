package Network;

import java.util.List;

import Models.PersonaAPI;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServicioSalud {
   @GET("Personas")
   Call<List<PersonaAPI>> GetPersonas();
  // @POST("Personas")
   //Call<PersonaAPI> PostPersonas(@Body PersonaAPI persona);
   @Multipart
   @POST("Personas")
   Call<PersonaAPI> PostPersonas(
           @Part("IdPersona") RequestBody idPersona,
           @Part("Nombres") RequestBody nombres,
           @Part("Apellidos") RequestBody apellidos,
           @Part("Sexo") RequestBody sexo,
           @Part("Ciudad") RequestBody ciudad,
           @Part("Edad") RequestBody edad,
           @Part("Dni") RequestBody dni,
           @Part("Peso") RequestBody peso,
           @Part("Altura") RequestBody altura,
           @Part("Foto") RequestBody foto,
           @Part("Ruta") RequestBody ruta,
           @Part MultipartBody.Part archivo
           );

}
