package com.example.trabajopractico6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void TomarFoto(View view) {

        Intent asd = new Intent(this, TomarFoto.class);
        startActivity(asd);
    }

    public void ElegirFoto(View view) {
        Intent asd = new Intent(this, ElegirFoto.class);
        startActivity(asd);
    }

    public void VerEstadisticas(View view) {

        
    }

}
