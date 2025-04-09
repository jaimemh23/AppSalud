package pe.edu.unc.appsalud;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class ActividadPrincipal extends AppCompatActivity {
//public static List<Persona> listaPersonas = new ArrayList<>();
TextView lbContar;
int contador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ly_principal);
        Toolbar oBarra = findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(oBarra);
        lbContar = findViewById(R.id.lbContador);
        //Archivo preferencia
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        contador = oFlujo.getInt("contar",1);
        lbContar.setText("N° ingresos "+contador);
        //Solicitar Permiso para que el usuario pueda aprobar o denegar
        if(ContextCompat.checkSelfPermission
                (this, Manifest.permission.READ_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences oFlujo = getSharedPreferences("control", Context.MODE_PRIVATE);
        SharedPreferences.Editor oEditar = oFlujo.edit();
        contador++;
        oEditar.putInt("contar",contador);
        oEditar.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent oIntento=null;
        if(item.getItemId()==R.id.itemSalir)
        {
            finish();
        }
        if(item.getItemId()==R.id.itemRegistrarPersonas)
        {
            oIntento = new Intent(this, ActividadRegistrarPersona.class);
            startActivity(oIntento);
        }
        if(item.getItemId()==R.id.itemRegistrarPersonaAPI)
        {
            oIntento = new Intent(this, ActividadRegistrarPersonaAPI.class);
            startActivity(oIntento);
        }
        if(item.getItemId()==R.id.itemRegistrarPersonaAPI)
        {
            oIntento = new Intent(this, ActividadRegistrarPersonaAPI.class);
            startActivity(oIntento);
        }
        if(item.getItemId()==R.id.itemListarPersonas){
            oIntento = new Intent(this,ActividadListarPersonas.class);
            startActivity(oIntento);
        }
        if(item.getItemId()==R.id.itemListaPersonaAPI){
            oIntento = new Intent(this,ActividadListarPersonasAPI.class);
            startActivity(oIntento);
        }
        if(item.getItemId()==R.id.itemLlamadas){
            oIntento = new Intent(this,ActividadLlamadas.class);
            startActivity(oIntento);
        }
        return super.onOptionsItemSelected(item);
    }
}