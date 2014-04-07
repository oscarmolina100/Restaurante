package com.example.restaurante.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MenuActivity extends ActionBarActivity {

    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        obtenerDatosInternet();
    }

    private void obtenerDatosInternet() {
        if(hayConexion()){
            progressBar = (ProgressBar)findViewById(R.id.progMenu);
            progressBar.setVisibility(View.VISIBLE);
            ObtenerInformacionInternet obtenerInformacionInternet = new ObtenerInformacionInternet();
        obtenerInformacionInternet.execute();
        }
        else{
            Toast.makeText(this, "Sin Conexión",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hayConexion() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){return true;}
        return false;
    }

//Clase para trabajar en segundo plano
private class ObtenerInformacionInternet extends AsyncTask<Object,Void,String>{
    @Override
    protected String doInBackground(Object... params) {
        int respuesta = -1;
        String respuestaTexto = "";
        try {
            URL url = new URL("http://codipaj.com/itchihuahuaii/eq3/10550372.php");
            HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();

            httpConnection.connect();
            respuesta = httpConnection.getResponseCode();

            if (respuesta == HttpURLConnection.HTTP_OK);{
                InputStream inputStream = httpConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"),8);

                StringBuilder stringBuilder = new StringBuilder();

                String linea = null;
                while((linea = bufferedReader.readLine())!=null){
                    stringBuilder.append(linea);
                }
                inputStream.close();
                respuestaTexto = stringBuilder.toString();
            }
            Log.d("HOLA",respuesta+"");
            Log.d("HOLA",respuestaTexto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respuestaTexto;
    }

    @Override
    protected void onPostExecute(String respuesta){
        progressBar.setVisibility(View.GONE);
        ListView listView = (ListView)findViewById(R.id.listMenu);
        ArrayList <HashMap <String, String>> arrayList = new ArrayList<HashMap<String, String>>();
        //JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        try{
            jsonArray = new JSONArray(respuesta);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("platillo", jsonObject.getString("nombre"));
                hashMap.put("precio", jsonObject.getString("precio"));
                hashMap.put("ingredientes",jsonObject.getString("ingrediente"));
                arrayList.add(hashMap);
            }
            listView.setAdapter(new AdaptadorPlatillo(getApplicationContext(),arrayList));
            /*String[] llaves = {"platillo","precio"};
            int[] ids = {android.R.id.text1, android.R.id.text2};

            SimpleAdapter adapter = new SimpleAdapter(
                    getApplicationContext(), arrayList, android.R.layout.simple_list_item_2,
                    llaves, ids
            );
            listView.setAdapter(adapter);*/
            //TextView result = (TextView)findViewById(R.id.txtResultado);
            //result.setText(jsonObject.getString("platillo"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
