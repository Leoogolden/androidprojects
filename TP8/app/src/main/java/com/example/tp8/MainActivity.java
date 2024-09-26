package com.example.tp8;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {

    CCGLSurfaceView VistaPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //  requestWindowFeature(Window.FEATURE_NO_wq4TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        VistaPrincipal = new CCGLSurfaceView(this);
        setContentView(VistaPrincipal);
    }
    protected void  onStart(){
        super.onStart();
        Juego juego;
        juego = new Juego(VistaPrincipal);
        juego.comenzarJuego();
    }
}