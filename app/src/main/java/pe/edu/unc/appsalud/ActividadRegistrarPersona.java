package pe.edu.unc.appsalud;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class ActividadRegistrarPersona extends AppCompatActivity {
    EditText txtNombres,txtApellidos,txtEdad,txtDNI;
    EditText txtPeso,txtAltura;
    RadioGroup rgSexo;
    Spinner sp_ciudad;
    ImageView imgFoto;
    Button btnRegistrar,btnListar;

    String[] ciudades={"Seleccionar ciudad","Cajamarca","Trujillo","Chiclayo"};
    Uri imgSeleccionado=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_registrar_personas);
        Toolbar oBarra = findViewById(R.id.tbRegistrarPersonas);
        setSupportActionBar(oBarra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos=findViewById(R.id.txtApellidos);
        rgSexo = findViewById(R.id.rgSexo);
        sp_ciudad = findViewById(R.id.sp_ciudad);
        //Cargar los item para sp_ciudad(lista)
        sp_ciudad.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,ciudades));
        txtEdad = findViewById(R.id.txtEdad);
        txtDNI = findViewById(R.id.txtDNI);
        txtPeso=findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        imgFoto = findViewById(R.id.imgFoto);
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarFoto();
            }
        });
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListar = findViewById(R.id.btnListar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarPersona();
            }
        });

    }

    private void seleccionarFoto() {
        Intent oIntento = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //filtrar los archivos de tipo Imagen
        oIntento.setType("image/*");
        startActivityIfNeeded(Intent.createChooser(oIntento,"Seleccionar Imagen"),
                100);
    }
//Metodo(superClase) que recoge la imagen seleccionada

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent oIntento) {
        super.onActivityResult(requestCode, resultCode, oIntento);
        if(requestCode==100){
            if(resultCode==RESULT_OK){
                imgSeleccionado=oIntento.getData();
                imgFoto.setImageURI(imgSeleccionado);
            }
        }
    }

    private void registrarPersona() {
        //tarea validar todas los EditText
        if(validarControles())
            return;
        //almacenar en variables los valores EditText
        String nombres=txtNombres.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String sexo = obtenerSexoSeleccionado();
        String ciudad = sp_ciudad.getSelectedItem().toString();
        int edad =Integer.valueOf(txtEdad.getText().toString());
        String dni = txtDNI.getText().toString();
        double peso = Double.valueOf(txtPeso.getText().toString());
        double altura =Double.valueOf(txtAltura.getText().toString());

        Persona oPersona = new Persona(nombres,apellidos,sexo,ciudad,edad
                            ,dni,peso,altura,imgSeleccionado);
        //Se registre si el dni es válido
        if(oPersona.verificarDNI())
        {
            Toast.makeText(this, "Registro correcto "+
                     oPersona.toString(),Toast.LENGTH_SHORT).show();
            //listaPersonas.add(oPersona);
            ActividadPrincipal.listaPersonas.add(oPersona);
            cuadroDialogo();
            //limpiar();
        }else
        {Toast.makeText(this, "No se registró DNI inválido",
                Toast.LENGTH_SHORT).show();
            txtDNI.requestFocus();
        }
    }

    private void cuadroDialogo() {
        AlertDialog.Builder oDialogo = new AlertDialog.Builder(this);
        oDialogo.setTitle("Aviso");
        oDialogo.setMessage("¿Desea seguir registrando?");
        oDialogo.setIcon(R.drawable.baseline_add_alert_24);
        oDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ActividadRegistrarPersona.this.finish();
            }
        });
        oDialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                limpiar();
            }
        });
        oDialogo.create();
        oDialogo.show();
    }

    private boolean validarControles() {
        if(comprobarCampoObligatorio(txtNombres,"Nombres")) return true;
        if(comprobarCampoObligatorio(txtApellidos,"Apellidos")) return true;
        if(obtenerSexoSeleccionado().isEmpty()){
            mostrarMensaje("Seleccionar un tipo de sexo de la persona");
            rgSexo.requestFocus();
            return true;
        }
        if(sp_ciudad.getSelectedItemPosition()==0)
        {
            mostrarMensaje("Seleccionar ciudad de procedencia");
            sp_ciudad.requestFocus();
            return true;
        }
        if(comprobarCampoObligatorio(txtEdad,"Edad")) return true;
        if(comprobarCampoObligatorio(txtDNI,"Dni")) return true;
        if(comprobarCampoObligatorio(txtPeso,"Peso")) return true;
        if(comprobarCampoObligatorio(txtAltura,"Altura")) return true;
        if(imgSeleccionado==null)
        {
            mostrarMensaje("Seleccionar una foto de galeria");
            imgFoto.requestFocus();
            return true;
        }
        return false;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    private boolean comprobarCampoObligatorio(EditText campo, String mensaje){
        if(campo.getText().toString().trim().isEmpty()){
            campo.setError("Campo "+mensaje+" obligatorio");
            campo.requestFocus();
            return true;
        }
        return false;
    }

    private String obtenerSexoSeleccionado() {
        int identificador = rgSexo.getCheckedRadioButtonId();
        if(identificador==R.id.rbFemenino) return "Femenino";
        if(identificador==R.id.rbMasculino) return "Masculino";
        return "";
    }

    private void limpiar() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtDNI.setText("");
        txtPeso.setText("");
        txtAltura.setText("");
        rgSexo.check(R.id.rbFemenino);
        sp_ciudad.setSelection(0);
        imgFoto.setImageResource(R.drawable.click);
        imgSeleccionado=null;
        txtNombres.requestFocus();
    }
    public void onClickListar(View boton){
        Intent oIntento = new Intent(this,ActividadListarPersonas.class);
        startActivity(oIntento);
    }
}