package AccesoDatos;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class DAOLlamadas {
    private Uri oTabla;
    private String[] oColumna;
    public DAOLlamadas(){
        oTabla = CallLog.Calls.CONTENT_URI;
        oColumna = new String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER};
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> ListarLlamadas(Activity contexto,int p_tipo){
        List<String> lista = new ArrayList<>();
        try{
            ContentResolver oCR = contexto.getContentResolver();
            String[] args = null;
            String filtro=null;
            if(p_tipo!=0) {
                filtro = oColumna[0] + "=?";
                args = new String[] {String.valueOf(p_tipo)};
            }
            Cursor oRegistro = oCR.query(oTabla,oColumna,filtro,args,null);
            if(oRegistro.moveToFirst()){
                do{
                    int iTipo = oRegistro.getInt(0);
                    String numero = oRegistro.getString(1);
                    String tipoLLamada[]={"Entrante","Saliente","Perdida","No Contesta","Bloqueada"};

                    lista.add("Llamada "+tipoLLamada[iTipo-1]+" numero telf: "+numero);

                }while (oRegistro.moveToNext());
                oRegistro.close();
            }
        }catch (Exception ex){
            Toast.makeText(contexto, "Error: "+ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return lista;
    }
}
