package com.example.restaurante.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class SucursalesActivity extends ActionBarActivity {
ListView listView;
    ArrayList<HashMap<String,String>> lista = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);

        listView = (ListView)findViewById(R.id.listaSuc);
        poblarLista();
    }

    private void poblarLista() {
        //Arreglo de Hashmaps
        //Adaptador
        //Lsta
        lista= new ArrayList<HashMap<String, String>>();
        HashMap<String,String> sucursal = new HashMap<String, String>();
        sucursal.put("Sucursal","Centro");
        sucursal.put("Telefono","123456");
        sucursal.put("Coordenada","28.703659,-106.110163");
        lista.add(sucursal);
        HashMap<String,String> sucursal2 = new HashMap<String, String>();
        sucursal2.put("Sucursal","Norte");
        sucursal2.put("Telefono","789123");
        sucursal2.put("Coordenada","28.758138,-106.119955");
        lista.add(sucursal2);

        int[] ids = {android.R.id.text1, android.R.id.text2};
        String[] llaves = {"Sucursal","Telefono"};
        //Adaptador para listas
        SimpleAdapter adapter = new SimpleAdapter(
                this, lista, android.R.layout.simple_list_item_2,
                llaves, ids
        );
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                HashMap<String,String> hashMapSeleccionado = lista.get(i);
                String nombre = hashMapSeleccionado.get("Sucursal");
                //Toast.makeText(getApplicationContext(),"Sucursal: "+ i, Toast.LENGTH_SHORT).show();
                String coordenada = hashMapSeleccionado.get("Coordenada");
                String uri = "geo: "+ coordenada + "?q=" + coordenada + "(" + nombre + ")";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                if(intent.resolveActivity(getPackageManager())!= null){
                   startActivity(intent);
                }
            }
        });
        //Incluir en la lista el adaptador
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sucursales, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
