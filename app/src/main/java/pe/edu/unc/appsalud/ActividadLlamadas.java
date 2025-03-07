package pe.edu.unc.appsalud;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import AccesoDatos.DAOLlamadas;

public class ActividadLlamadas extends AppCompatActivity {
    ListView lvListaLlamadas;
    DAOLlamadas oLlamadas;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ly_llamadas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String tipoLLamada[]={"Todas las llamadas","Entrante","Saliente","Perdida","No Contesta","Bloqueada"};
        Spinner sp_tipoLlamada = findViewById(R.id.sp_tipoLLamada);
        sp_tipoLlamada.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,tipoLLamada));

        lvListaLlamadas = findViewById(R.id.lvListaLlamadas);

        oLlamadas = new DAOLlamadas();

        sp_tipoLlamada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(ActividadLlamadas.this, "Indice "+i, Toast.LENGTH_SHORT).show();
                lvListaLlamadas.setAdapter(new ArrayAdapter<String>(ActividadLlamadas.this,
                        android.R.layout.simple_list_item_1,
                        oLlamadas.ListarLlamadas(ActividadLlamadas.this,i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}