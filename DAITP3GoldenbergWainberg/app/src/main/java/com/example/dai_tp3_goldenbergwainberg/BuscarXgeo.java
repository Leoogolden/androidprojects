package com.example.dai_tp3_goldenbergwainberg;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BuscarXgeo extends AppCompatActivity {

    ArrayList<String> ListaRes;
    ArrayAdapter<String> AdpRes;

    int posicao;
    ArrayList<String> ListaCat;
    ArrayAdapter<String> AdpCat;
    String categoria;

    Spinner Spinner;
    ListView Listonta;
    EditText coordenadaX;
    EditText coordenadaY;
    EditText ingRadio;

    String stringX;
    String stringY;
    String stringradio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_xgeo);
        ListaCat = new ArrayList<>();
        AdpCat = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListaCat);
        Spinner = findViewById(R.id.SpnCat);
        ListaRes = new ArrayList<>();
        Listonta = findViewById(R.id.ListaGeo);
        AdpRes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListaRes);
        coordenadaX = findViewById(R.id.IngX);
        coordenadaY = findViewById(R.id.IngY);
        ingRadio = findViewById(R.id.IngRadio);
        BuscarXgeo.tareaAsincronica miTarea = new BuscarXgeo.tareaAsincronica();
        miTarea.execute();
    }



    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL Ruta = new URL("http://epok.buenosaires.gob.ar/getCategorias");
                HttpURLConnection miConexion = (HttpURLConnection) Ruta.openConnection();

                if (miConexion.getResponseCode() == 200) {
                    InputStream cuerpoRespuesta = miConexion.getInputStream();
                    InputStreamReader lectorRespuesta = new InputStreamReader(cuerpoRespuesta, "UTF-8");
                    procesarJSONleido(lectorRespuesta);
                }
                miConexion.disconnect();
            } catch (Exception error) {
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Spinner.setAdapter(AdpCat);
            posicao = Spinner.getSelectedItemPosition();
            categoria = Spinner.getItemAtPosition(posicao).toString();
        }
    }

    public void procesarJSONleido(InputStreamReader chocloLeido) {
        JsonReader JSONleido = new JsonReader(chocloLeido);
        try {
            JSONleido.beginObject();
            while (JSONleido.hasNext()) {
                String nombre = JSONleido.nextName();
                if (nombre.equals("cantidad_de_categorias")) {
                    int intcat = JSONleido.nextInt();
                    Log.d("LeerCat", "el nombre es cant cat "+ intcat);
                } else {
                    JSONleido.beginArray();
                    while (JSONleido.hasNext()) {
                        JSONleido.beginObject();
                        while (JSONleido.hasNext()) {
                            nombre = JSONleido.nextName();
                            if (nombre.equals("nombre")) {
                                String nombreActual = JSONleido.nextString();
                                ListaCat.add(nombreActual);
                            } else {
                                JSONleido.skipValue();
                            }
                        }
                        JSONleido.endObject();
                    }
                    JSONleido.endArray();
                }
            }
            JSONleido.endObject();
        } catch (Exception Error) {
        }
    }

    private class tareaAsincronicaGeo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("Cat", "entro al try");
                Log.d("Cat", "" + categoria);
                URL Ruta = new URL("http://epok.buenosaires.gob.ar/reverseGeocoderLugares/?x=" + stringX + "&y=" + stringY + "&categorias=" + categoria.replace(' ', '_').replace('í', 'i').replace('ú', 'u').replace('é', 'e').replace('á', 'a').replace('ó', 'o').toLowerCase() + "&radio=" + stringradio);
                Log.d("Cat", "" + Ruta);
                HttpURLConnection miConexion = (HttpURLConnection) Ruta.openConnection();
                if (miConexion.getResponseCode() == 200) {
                    InputStream cuerpoRespuesta = miConexion.getInputStream();
                    InputStreamReader lectorRespuesta = new InputStreamReader(cuerpoRespuesta, "UTF-8");
                    procesarJSONBusqueda(lectorRespuesta);
                    Log.d("Cat", "que pasa aca");
                }
                miConexion.disconnect();
            } catch (Exception error) {
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Listonta.setAdapter(AdpRes);
        }
    }

    public void procesarJSONBusqueda(InputStreamReader JSONingresado) {
        JsonReader JSONleido = new JsonReader(JSONingresado);
        try {
            JSONleido.beginObject();
            while (JSONleido.hasNext()) {
                String nombre = JSONleido.nextName();
                if (nombre.equals("totalFull")) {
                    Log.d("procesando", "1 "+ nombre);
                    JSONleido.skipValue();
                } else if (nombre.equals("total")) {
                    Log.d("procesando", "2 "+ nombre);
                    JSONleido.skipValue();
                } else {
                    Log.d("procesando", "3 "+ nombre);
                    JSONleido.beginArray();
                    while (JSONleido.hasNext()) {
                        JSONleido.beginObject();
                        while (JSONleido.hasNext()) {
                            nombre = JSONleido.nextName();
                            Log.d("procesando", "capaz es nombre "+ nombre);
                            if (nombre.equals("nombre")) {
                                String nombreActual = JSONleido.nextString();
                                Log.d("procesando", "vamoo "+ nombreActual);
                                ListaRes.add(nombreActual);
                            } else {
                                JSONleido.skipValue();
                            }
                        }
                        JSONleido.endObject();
                    }
                    JSONleido.endArray();
                }
            }
            JSONleido.endObject();
        } catch (Exception Error) {
        }
    }

    public void Busqueda(View vistonta) {
        Log.d("ResBusqueda", "entro a buscar");
        stringX = coordenadaX.getText().toString();
        stringY = coordenadaY.getText().toString();
        stringradio = ingRadio.getText().toString();
        ListaRes.clear();
        categoria = (String) Spinner.getSelectedItem();
        BuscarXgeo.tareaAsincronicaGeo miTareaGeo = new BuscarXgeo.tareaAsincronicaGeo();
        miTareaGeo.execute();
    }
}
