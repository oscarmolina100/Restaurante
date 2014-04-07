package com.example.restaurante.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by oscar_molina100 on 02/04/14.
 */
public class AdaptadorPlatillo extends BaseAdapter{
    Context context;
    ArrayList<HashMap<String,String>> datos;
    private static LayoutInflater inflater = null;

    public AdaptadorPlatillo(Context contexto, ArrayList<HashMap<String,String>> datosDeAfuera) {
        context = contexto;
        datos = datosDeAfuera;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi==null){
            vi = inflater.inflate(R.layout.item_platillo, null);
        }
        TextView text = (TextView)vi.findViewById(R.id.txtPlatillo);
        TextView text2 = (TextView)vi.findViewById(R.id.txtIngred);
        TextView text3 = (TextView)vi.findViewById(R.id.txtPrecio);

        text.setText(datos.get(position).get("platillo"));
        text2.setText(datos.get(position).get("ingredientes"));
        text3.setText(datos.get(position).get("precio"));

        return vi;
    }
}
