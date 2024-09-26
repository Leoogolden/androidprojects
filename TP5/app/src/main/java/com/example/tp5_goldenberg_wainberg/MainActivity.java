package com.example.tp5_goldenberg_wainberg;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

        FragmentManager manejadorF;
        FragmentTransaction transac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BuscarPelicula(View vista){
        EditText a = findViewById(R.id.TextoBuscar);
        String texto = a.getText().toString();


        Bundle cositas= new Bundle();

        RadioGroup radioid= findViewById(R.id.LinearRadio);

        int radio = radioid.getCheckedRadioButtonId();
        if(radio == findViewById(R.id.RadioUno).getId()){
            cositas.putString("textobuscar",texto);

            Fragment BuscaPel;
            BuscaPel = new fragmentBuscar();
            BuscaPel.setArguments(cositas);

            manejadorF= getFragmentManager();

            transac= manejadorF.beginTransaction();
            transac.replace(R.id.FragmentId, BuscaPel);
            transac.commit();
        }
        else{

        }




    }


}
