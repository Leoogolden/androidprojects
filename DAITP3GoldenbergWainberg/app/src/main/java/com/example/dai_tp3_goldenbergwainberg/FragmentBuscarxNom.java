package com.example.dai_tp3_goldenbergwainberg;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class FragmentBuscarxNom extends Fragment implements View.OnClickListener {
    Button boton;
    ListView listardovich;
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
    public View onCreateView(LayoutInflater inflador, ViewGroup GrupoVistas, Bundle Datos) {
        View vistadevuelta;
        vistadevuelta = inflador.inflate(R.layout.buscar_xnom, GrupoVistas, false);

        textoingresado = vistadevuelta.findViewById(R.id.IngText);
        boton = vistadevuelta.findViewById(R.id.Buscar);
        listardovich = vistadevuelta.findViewById(R.id.ListaNombres);
        boton.setOnClickListener(this);

        listajava = vistadevuelta.findViewById(R.id.ListaNombres);
        textoingresado = vistadevuelta.findViewById(R.id.IngText);
        adapter = new ArrayAdapter<>(vistadevuelta.getContext(), android.R.layout.simple_list_item_1, results);

        return vistadevuelta;
    }
    @Override
    public void onClick(View v) {
        results.clear();
        nome = textoingresado.getText().toString();
        new BuscarResultados().execute();

    }
}
