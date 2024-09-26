package com.example.dai_tp3_goldenbergwainberg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentBuscarxCat extends Fragment implements View.OnClickListener{

    Spinner espiner;
    Button boton;
    ListView lista;
    List<String> listacat;
    Spinner SpnCat;
    ArrayAdapter<String> AdaptCat;

    ArrayAdapter<String> Adaptador2;
    ArrayList<String> ListaDeDados;
    ListView listonta;
    String TextoSelecionado;

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup GrupoVistas, Bundle Datos) {
        View vistadevuelta;
        vistadevuelta = inflador.inflate(R.layout.buscar_xcat, GrupoVistas, false);


        listacat = new ArrayList<>();
        ListaDeDados = new ArrayList<String>();
        Adaptador2 = new ArrayAdapter<String>(vistadevuelta.getContext(), android.R.layout.simple_list_item_1, ListaDeDados);
        Adaptador2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        listonta = vistadevuelta.findViewById(R.id.ListRes);
        listonta.setOnItemClickListener(Escuchando);

        AdaptCat = new ArrayAdapter<String>(vistadevuelta.getContext(), android.R.layout.simple_spinner_item, listacat);
        AdaptCat.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        SpnCat = (Spinner) vistadevuelta.findViewById(R.id.cmbCat);
        tareaAsincronica miTarea = new tareaAsincronica();
        miTarea.execute();

        espiner = vistadevuelta.findViewById(R.id.cmbCat);
        boton = vistadevuelta.findViewById(R.id.Buscar);
        lista = vistadevuelta.findViewById(R.id.ListRes);
        boton.setOnClickListener(this);

        return vistadevuelta;
    }
    AdapterView.OnItemClickListener Escuchando = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };
    @Override
    public void onClick(View vista) {
        Log.d("Veamos", "Entre");

        //Log.d("Veamos","1 "+ spnOpcoes.getSelectedItem().toString());
        int posicao;
        posicao = SpnCat.getSelectedItemPosition();

        Log.d("Veamos", "2");

        TextoSelecionado = listacat.get(posicao);
        Log.d("Veamos", "" + TextoSelecionado);
        new buscar().execute();
    }
    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL rutatlantica = new URL("http://epok.buenosaires.gob.ar/getCategorias/");
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI", "Me conecto");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    ProcessJSONLeido(lectorrespuesta);
                } else {
                    Log.d("AccesoAPI", "Error en la conexion");
                }
            } catch (Exception error) {
                Log.d("AccesoAPI", "Huno un error al conectarme" + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SpnCat.setAdapter(AdaptCat);
        }
    }
    public void ProcessJSONLeido(InputStreamReader streamLeido) {

        JsonReader JSONleido = new JsonReader(streamLeido);
        try {
            JSONleido.beginObject();
            while (JSONleido.hasNext()) {
                String NomeDuElemento = JSONleido.nextName();
                Log.d("LecturaJSON", "o nome du elemento e " + NomeDuElemento);
                if (NomeDuElemento.equals("cantidad_de_categorias")) {
                    int QuantidadeCategorias = JSONleido.nextInt();
                    Log.d("LecturaJSON", "O número de categorias é " + QuantidadeCategorias);
                } else {
                    JSONleido.beginArray();
                    while (JSONleido.hasNext()) {
                        JSONleido.beginObject();
                        while (JSONleido.hasNext()) {
                            NomeDuElemento = JSONleido.nextName();
                            if (NomeDuElemento.equals("nombre")) {
                                String ValorDoItem = JSONleido.nextString();
                                Log.d("LecturaJSON", "valor lido: " + ValorDoItem);
                                listacat.add(ValorDoItem);
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


        } catch (Exception error) {
            Log.d("LecturaJSON", "" + error);
        }

    }
    public class buscar extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("AccesoAPI2", "http://epok.buenosaires.gob.ar/buscar/?texto=" + TextoSelecionado);
                URL rutatlantica = new URL("http://epok.buenosaires.gob.ar/buscar/?texto=" + TextoSelecionado);
                HttpURLConnection conexion = (HttpURLConnection) rutatlantica.openConnection();
                Log.d("AccesoAPI2", "me conecto wachin");
                if (conexion.getResponseCode() == 200) {
                    Log.d("AccesoAPI2", "conexion ok");
                    InputStream cuerporesspuesta = conexion.getInputStream();
                    InputStreamReader lectorrespuesta = new InputStreamReader(cuerporesspuesta, "UTF-8");
                    JsonReader JSONleido = new JsonReader(lectorrespuesta);
                    JSONleido.beginObject();
                    JSONleido.nextName();
                    JSONleido.nextInt();
                    while (JSONleido.hasNext()) {
                        Log.d("AccesoAPI2", "entro capo");
                        String NomeDuElemento = JSONleido.nextName();

                        if(NomeDuElemento.equals("instancias")){
                            JSONleido.beginArray();
                            while (JSONleido.hasNext()) {
                                Log.d("AccesoAPI2", "entro al segundo capo");
                                JSONleido.beginObject();
                                while (JSONleido.hasNext()) {
                                    Log.d("AccesoAPI2", "entro al tercero capo");
                                    NomeDuElemento = JSONleido.nextName();
                                    Log.d("AccesoAPI2", "nombre "+NomeDuElemento);
                                    if (NomeDuElemento.equals("nombre")) {
                                        String ValorDoItem = JSONleido.nextString();
                                        Log.d("BusquedaJSON", "valor lido: " + ValorDoItem);
                                        ListaDeDados.add(ValorDoItem);
                                    } else {
                                        JSONleido.skipValue();
                                    }
                                }
                                JSONleido.endObject();
                            }
                            JSONleido.endArray();
                        }else{
                            JSONleido.skipValue();
                        }
                    }
                    JSONleido.endObject();
                } else {
                    Log.d("AccesoAPI2", "Algo salio mal bro");
                }
            } catch (Exception error) {
                Log.d("AccesoAPI2", "Hubo un error al conectarme " + error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listonta.setAdapter(Adaptador2);

        }
    }
    public void Buscar(View recibi2) {
        Log.d("Veamos", "Entre");

        //Log.d("Veamos","1 "+ spnOpcoes.getSelectedItem().toString());
        int posicao;
        posicao = SpnCat.getSelectedItemPosition();

        Log.d("Veamos", "2");

        TextoSelecionado = listacat.get(posicao);
        Log.d("Veamos", "" + TextoSelecionado);
        new buscar().execute();
    }
}
