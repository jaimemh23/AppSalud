package pe.edu.unc.appsalud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class MainActivity extends AppCompatActivity {
    EditText txtNombres,txtApellidos,txtEdad,txtDNI;
    EditText txtPeso,txtAltura;
    Button btnRegistrar,btnListar;
    List<Persona> listaPersonas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos=findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtDNI = findViewById(R.id.txtDNI);
        txtPeso=findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarPersona();
            }
        });
        listaPersonas = new ArrayList<>();
    }

    private void registrarPersona() {
        //tarea validar todas los EditText

        //almacenar en variables los valores EditText
        String nombres=txtNombres.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        int edad =Integer.valueOf(txtEdad.getText().toString());
        String dni = txtDNI.getText().toString();
        double peso = Double.valueOf(txtPeso.getText().toString());
        double altura =Double.valueOf(txtAltura.getText().toString());
        Persona oPersona = new Persona(nombres,apellidos,edad
                            ,dni,peso,altura);
        //Se registre si el dni es válido
        if(oPersona.verificarDNI())
        {
            Toast.makeText(this, "Registro correcto "+
                     oPersona.toString(),Toast.LENGTH_SHORT).show();
            listaPersonas.add(oPersona);
            limpiar();
        }else
        {Toast.makeText(this, "No se registró DNI inválido",
                Toast.LENGTH_SHORT).show();
            txtDNI.requestFocus();
        }
    }

    private void limpiar() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtDNI.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        txtNombres.requestFocus();
    }
}