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

import org.w3c.dom.Text;

import java.util.List;

import AccesoDatos.DAOPersona;
import Models.Persona;
import kotlin.contracts.Returns;

public class AdaptadorPersonas extends BaseAdapter {
    //private List<Persona> listaPersonas;
    private DAOPersona oDAOPersona;
    private Context contexto;
    // para asociar al recurso layout(diseño tarjeta)
    LayoutInflater inflater;
    public AdaptadorPersonas(DAOPersona oDAOPersona, Context contexto) {
        this.oDAOPersona = oDAOPersona;
        this.contexto = contexto;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return oDAOPersona.getSize();
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
        Persona oP = oDAOPersona.getObjetoPersona(i);

        lbNombre.setText(oP.getNombreCompleto());
        lbTipoPeso.setText(oP.getTipoPeso());
        lbTipoPersona.setText(oP.getTipoPersona());
        if(oP.getSexo().equals("Femenino"))
            imgSexo.setImageResource(R.drawable.femenino);
        else
            imgSexo.setImageResource(R.drawable.masculino);
        lbProcedencia.setText(oP.getCiudad());
        //imgFoto.setImageURI(oP.getFoto());
        imgFoto.setImageBitmap(convertirBitMap(oP.getImgFoto()));
        return vista;
    }

    private Bitmap convertirBitMap(byte[] foto) {
        return BitmapFactory.decodeByteArray(foto,0,foto.length);
    }
}
