package pe.edu.unc.appsalud;

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
        // crear un objeto de la interfaz apiServicio
        ApiServicioSalud oApiServicio = RetrofitClient.getClient().create(ApiServicioSalud.class);
        //llamar metodo get del Api
        Call<List<PersonaAPI>> oCargaPersonas = oApiServicio.GetPersonas();
        oCargaPersonas.enqueue(new Callback<List<PersonaAPI>>() {
            @Override
            public void onResponse(Call<List<PersonaAPI>> call, Response<List<PersonaAPI>> response) {
                if(response.isSuccessful()){
                    List<PersonaAPI> lista = response.body();
                   lvListaPersonas.setAdapter( new ArrayAdapter<PersonaAPI>(ActividadListarPersonasAPI.this,
                           android.R.layout.simple_list_item_1,
                           lista));
                }
            }

            @Override
            public void onFailure(Call<List<PersonaAPI>> call, Throwable t) {
                Toast.makeText(ActividadListarPersonasAPI.this,
                        "Error API: "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}