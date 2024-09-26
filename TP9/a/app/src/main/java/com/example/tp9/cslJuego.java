package com.example.tp9;

import android.util.Log;

import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCSize;

public class cslJuego {

    CCGLSurfaceView _VistaJuego1;
    CCSize _Pantalla;
    Sprite _Jugador;

    public cslJuego(CCGLSurfaceView VistadelJuego) {
        _VistaJuego1 = VistadelJuego;

    }

    public void ComenzarJuego() {
        Log.d("logaso", "arrancamion");
        Director.sharedDirector().attachInView(_VistaJuego1);

        _Pantalla = Director.sharedDirector().displaySize();

        Scene escena;
        escena = EscenaComienzo();
        Director.sharedDirector().runWithScene(escena);
    }

    private Scene EscenaComienzo() {
        Scene escenadevuelve;
        escenadevuelve = Scene.node();

        capaJuego Capa1 = new capaJuego();
        escenadevuelve.addChild(Capa1);

        return escenadevuelve;
    }

    class capaJuego extends Layer {
        ponerJugador();
    }

    void ponerJugador(){
        _Jugador=Sprite.sprite("bokita.png");
        _Jugador.setPosition(100, 300);
        super.addChild(_Jugador);
    }
}
