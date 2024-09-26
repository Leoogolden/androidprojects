package com.example.myapplication;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {


    CCGLSurfaceView VistaPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        VistaPrincipal = new CCGLSurfaceView(this);
        setContentView(VistaPrincipal);

    }

    @Override
    protected void onStart() {
        super.onStart();
        clsJuego juegaso;
        juegaso = new clsJuego(VistaPrincipal);
        juegaso.ComenzarJuego();
    }
}
