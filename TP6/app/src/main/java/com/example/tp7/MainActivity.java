package com.example.tp7;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {


    CCGLSurfaceView VistaNro1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        VistaNro1 = new CCGLSurfaceView(this);
        setContentView(VistaNro1);

    }
    protected void  onStart(){
        super.onStart();
        Juego juego;
        juego = new Juego(VistaNro1);
        juego.comenzarJuego();
    }

}
