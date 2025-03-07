package pe.edu.unc.appsalud;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import Models.PersonaAPI;
import Network.ApiServicioSalud;
import Network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadListarPersonasAPI extends AppCompatActivity {
    ListView lvListaPersonas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ly_listar_personas_api);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvListaPersonas = findViewById(R.id.lvListaPersonasAPI);
        cargarPersonasAPI();
    }

    private void cargarPersonasAPI() {
        ProgressDialog oProgreso = new ProgressDialog(this);
        oProgreso.setMessage("Cargando Lista de Personas");
        oProgreso.setCancelable(false); // no puede utilizar la interfaz
        oProgreso.show();

        // crear un objeto de la interfaz apiServicio
        ApiServicioSalud oApiServicio = RetrofitClient.getClient().create(ApiServicioSalud.class);
        //llamar metodo get del Api
        Call<List<PersonaAPI>> oCargaPersonas = oApiServicio.GetPersonas();
        oCargaPersonas.enqueue(new Callback<List<PersonaAPI>>() {
            @Override
            public void onResponse(Call<List<PersonaAPI>> call, Response<List<PersonaAPI>> response) {
                if(response.isSuccessful()){
                    oProgreso.cancel();
                    List<PersonaAPI> lista = response.body();
                   lvListaPersonas.setAdapter(new AdaptadorPersonasAPI(
                           ActividadListarPersonasAPI.this,lista));
                }
            }

            @Override
            public void onFailure(Call<List<PersonaAPI>> call, Throwable t) {
                oProgreso.cancel();
                Toast.makeText(ActividadListarPersonasAPI.this,
                        "Error API: "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}