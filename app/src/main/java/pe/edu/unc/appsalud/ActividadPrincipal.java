package pe.edu.unc.appsalud;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActividadPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ly_principal);
        Toolbar oBarra = findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(oBarra);
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
        if(item.getItemId()==R.id.itemListarPersonas){
            oIntento = new Intent(this,ActividadListarPersonas.class);
            startActivity(oIntento);
        }
        return super.onOptionsItemSelected(item);
    }
}