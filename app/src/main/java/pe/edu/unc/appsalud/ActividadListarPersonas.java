package pe.edu.unc.appsalud;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import AccesoDatos.DAOPersona;
import Models.Persona;

public class ActividadListarPersonas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ly_listar_personas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ListView lvListaPersonas = findViewById(R.id.lvListaPersonas);
        //lvListaPersonas.setAdapter(new ArrayAdapter<Persona>(this,
        //        android.R.layout.simple_list_item_1, ActividadRegistrarPersona.listaPersonas));
        DAOPersona oDAOPersona = new DAOPersona();
        oDAOPersona.cagarLista(this);
        lvListaPersonas.setAdapter(new AdaptadorPersonas(oDAOPersona,this));
    }
}