package AccesoDatos;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Models.Persona;

public class DAOPersona {
    private String nombreBD;
    private int versionBD;
    private List<Persona> listaPersonas;
    public DAOPersona(){
        nombreBD="BDSalud";
        versionBD=1;
        listaPersonas = new ArrayList<>();
    }
    public boolean Agregar(Activity contexto, Persona oP){
       boolean rpta = false;
        BDOpenHelper oOH = new BDOpenHelper(contexto,nombreBD,null,versionBD);
        SQLiteDatabase oBD = oOH.getWritableDatabase();
        if(oBD!=null){
            ContentValues oColumnas = new ContentValues();
            oColumnas.put("nombres",oP.getNombres());
            oColumnas.put("apellidos",oP.getApellidos());
            oColumnas.put("sexo",oP.getSexo());
            oColumnas.put("ciudad",oP.getCiudad());
            oColumnas.put("edad",oP.getEdad());
            oColumnas.put("dni",oP.getDni());
            oColumnas.put("peso",oP.getPeso());
            oColumnas.put("altura",oP.getAltura());
            oColumnas.put("foto",oP.getImgFoto());
            long fila= oBD.insert("Persona",null,oColumnas);
            if (fila>0){
                rpta = true;
            }
            oBD.close();
            oOH.close();
        }
        return rpta;
    }
    public boolean cagarLista(Activity contexto){
        boolean rpta =false;
        BDOpenHelper oOH = new BDOpenHelper(contexto,nombreBD,null,versionBD);
        SQLiteDatabase oBD = oOH.getReadableDatabase();
        String sql = "Select * from Persona";
        Cursor oRegistros = oBD.rawQuery(sql,null);
        if(oRegistros.moveToFirst()){
            rpta = true;
            do{
                String nombres = oRegistros.getString(1);
                String apellidos = oRegistros.getString(2);
                String sexo = oRegistros.getString(3);
                String ciudad = oRegistros.getString(4);
                int edad = oRegistros.getInt(5);
                String dni = oRegistros.getString(6);
                double peso = oRegistros.getDouble(7);
                double altura = oRegistros.getDouble(8);
                byte[] foto = oRegistros.getBlob(9);
                Persona oP = new Persona(nombres,apellidos,sexo,ciudad,edad,dni,peso,altura,foto);
                listaPersonas.add(oP);
            }while (oRegistros.moveToNext());
            oBD.close();
            oOH.close();
        }
        return rpta;
    }
    public List<Persona> getListaPersonas(){
        return listaPersonas;
    }
    public int getSize(){
        return listaPersonas.size();
    }
    public Persona getObjetoPersona(int indice){
        return listaPersonas.get(indice);
    }
    
}
