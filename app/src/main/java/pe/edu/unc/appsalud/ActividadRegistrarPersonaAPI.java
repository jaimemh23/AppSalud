package pe.edu.unc.appsalud;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;

import AccesoDatos.DAOPersona;
import Models.Persona;
import Models.PersonaAPI;
import Network.ApiServicioSalud;
import Network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadRegistrarPersonaAPI extends AppCompatActivity {
    EditText txtNombres,txtApellidos,txtEdad,txtDNI;
    EditText txtPeso,txtAltura;
    RadioGroup rgSexo;
    Spinner sp_ciudad;
    ImageView imgFoto;
    Button btnRegistrar,btnListar;

    String[] ciudades={"Seleccionar ciudad","Cajamarca","Trujillo","Chiclayo"};
    byte[] imgSeleccionado=null;
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
                //imgSeleccionado=oIntento.getData();
                Uri foto = oIntento.getData();
                imgFoto.setImageURI(foto);
                imgFoto.buildDrawingCache();
                Bitmap oBitMap = imgFoto.getDrawingCache();
                ByteArrayOutputStream oFlujoSalida = new ByteArrayOutputStream();

                oBitMap.compress(Bitmap.CompressFormat.PNG,0,oFlujoSalida);
                imgSeleccionado = oFlujoSalida.toByteArray();

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

        PersonaAPI oPersona = new PersonaAPI(nombres,apellidos,sexo,ciudad,edad
                ,dni,peso,altura,"no");
        //Se registre si el dni es válido
        if(oPersona.verificarDNI())
        {
             enviarPost(oPersona);
            //   cuadroDialogo();
            //   Toast.makeText(this, "No se registró", Toast.LENGTH_SHORT).show();

        }else
        {Toast.makeText(this, "No se registró DNI inválido",
                Toast.LENGTH_SHORT).show();
            txtDNI.requestFocus();
        }
    }

    private void enviarPost(PersonaAPI oPersona) {
        ApiServicioSalud apiServicio = RetrofitClient.getClient().
                create(ApiServicioSalud.class);
        Call<PersonaAPI> oRegistroAPI = apiServicio.PostPersonas(oPersona);
        oRegistroAPI.enqueue(new Callback<PersonaAPI>() {
            @Override
            public void onResponse(Call<PersonaAPI> call, Response<PersonaAPI> response) {
                if(response.isSuccessful()){
                    cuadroDialogo();
                }else{
                    Toast.makeText(ActividadRegistrarPersonaAPI.this,
                            "Error de Registro",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonaAPI> call, Throwable t) {
                Toast.makeText(ActividadRegistrarPersonaAPI.this,
                        "Error API: "+t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
                ActividadRegistrarPersonaAPI.this.finish();
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
