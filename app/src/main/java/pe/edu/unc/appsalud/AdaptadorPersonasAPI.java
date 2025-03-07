package pe.edu.unc.appsalud;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import AccesoDatos.DAOPersona;
import Models.Persona;
import Models.PersonaAPI;
import com.bumptech.glide.Glide;

public class AdaptadorPersonasAPI extends BaseAdapter {
    private List<PersonaAPI> listaPersonas;

    private Context contexto;
    // para asociar al recurso layout(diseño tarjeta)
    LayoutInflater inflater;
    public AdaptadorPersonasAPI(Context contexto, List<PersonaAPI> listaPersonas) {
        this.listaPersonas = listaPersonas;
        this.contexto = contexto;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaPersonas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View vista = inflater.inflate(R.layout.ly_item_lista_persona,null);
        ImageView imgFoto = vista.findViewById(R.id.imgItemFoto);
        TextView lbNombre = vista.findViewById(R.id.lbNombreCompleto);
        TextView lbTipoPeso = vista.findViewById(R.id.lbTipoPeso);
        TextView lbTipoPersona= vista.findViewById(R.id.lbTipoPersona);
        ImageView imgSexo =vista.findViewById(R.id.imgItemSexo);
        TextView lbProcedencia = vista.findViewById(R.id.lbProcedencia);
        //Llenar valores en los objetos de tipo View
        PersonaAPI oP = listaPersonas.get(i);
        lbNombre.setText(oP.getNombreCompleto());
        lbTipoPeso.setText(oP.getTipoPeso());
        lbTipoPersona.setText(oP.getTipoPersona());
        if(oP.getSexo().equals("Femenino"))
            imgSexo.setImageResource(R.drawable.femenino);
        else
            imgSexo.setImageResource(R.drawable.masculino);
        lbProcedencia.setText(oP.getCiudad());
        //imgFoto.setImageURI(oP.getFoto());
    //    imgFoto.setImageBitmap(convertirBitMap(oP.getImgFoto()));
        //leer la url del servidor web de la imagen y muestra en el view imgFoto
        Glide.with(contexto).load(oP.getRuta()).into(imgFoto);
        return vista;
    }


}
