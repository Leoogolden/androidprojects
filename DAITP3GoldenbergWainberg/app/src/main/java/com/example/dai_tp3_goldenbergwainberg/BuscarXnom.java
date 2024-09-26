package com.example.dai_tp3_goldenbergwainberg;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class BuscarXnom extends AppCompatActivity {
    String nome = "";
    EditText textoingresado;
    ArrayList<String> results = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listajava;

    private class BuscarResultados extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("http://epok.buenosaires.gob.ar/buscar/?texto=" + nome);
                String text = new Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next();
                JSONObject json = new JSONObject(text);
                JSONArray json_resultados = json.getJSONArray("instancias");
                for (int i = 0; i < json_resultados.length(); i++) {
                    JSONObject categoria = json_resultados.getJSONObject(i);
                    results.add(categoria.getString("nombre"));
                }
            } catch (Exception e) {
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void _void) {
            super.onPostExecute(_void);
            listajava.setAdapter(adapter);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_xnom);
        listajava = findViewById(R.id.ListaNombres);
        textoingresado = findViewById(R.id.IngText);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
    }
    public void BusXnom(View view) {
        results.clear();
        nome = textoingresado.getText().toString();
        new BuscarResultados().execute();
    }

}
