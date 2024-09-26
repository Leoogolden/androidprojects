package com.example.dai_tp3_goldenbergwainberg;

import android.app.Fragment;
import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    android.app.FragmentManager admFragment;
    FragmentTransaction TransacFragments;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admFragment = getFragmentManager();


    }

    public void BusCat(View Vistonta) {
        Button boton;
        boton = findViewById(R.id.BotonCat);
        boton.setVisibility(View.GONE);
        Button boton2;
        boton2 = findViewById(R.id.BotonNom);
        boton2.setVisibility(View.GONE);
        Button boton3;
        boton3 = findViewById(R.id.BotonGeo);
        boton3.setVisibility(View.GONE);

        fragment = new FragmentBuscarxCat();
        TransacFragments = admFragment.beginTransaction();
        TransacFragments.replace(R.id.AlojadorFragments, fragment);
        TransacFragments.commit();
//        Intent ActividadDestino;
//        ActividadDestino = new Intent(MainActivity.this, BuscarXcat.class);
//        startActivity(ActividadDestino);
    }

    public void BuscNom(View Vistonta) {
        Button boton;
        boton = findViewById(R.id.BotonCat);
        boton.setVisibility(View.GONE);
        Button boton2;
        boton2 = findViewById(R.id.BotonNom);
        boton2.setVisibility(View.GONE);
        Button boton3;
        boton3 = findViewById(R.id.BotonGeo);
        boton3.setVisibility(View.GONE);

        fragment = new FragmentBuscarxNom();
        TransacFragments = admFragment.beginTransaction();
        TransacFragments.replace(R.id.AlojadorFragments, fragment);
        TransacFragments.commit();
//        Intent ActividadDestino;
//        ActividadDestino = new Intent(MainActivity.this, BuscarXnom.class);
//        startActivity(ActividadDestino);
    }

    public void BuscGeo(View Vistonta) {
        Intent ActividadDestino;
        ActividadDestino = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(ActividadDestino);
    }


}

