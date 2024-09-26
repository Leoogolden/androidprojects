package com.example.tp7;


import android.util.Log;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

public class Juego {

    CCGLSurfaceView _VistaJuego;
    CCSize _Pantalla;
    Sprite _Punto1;

    public Juego(CCGLSurfaceView VistaJuego) {

        Log.d("capo", "constructor clase juego");
        _VistaJuego = VistaJuego;
    }

    public void comenzarJuego() {
     //empieza juego
        Director.sharedDirector().attachInView(_VistaJuego);

        _Pantalla = Director.sharedDirector().displaySize();
        Log.d("dou", "Pantalla - Ancho: " + _Pantalla.getWidth() + " - Alto: " + _Pantalla.getHeight());

        Scene escenaAUsar;
        escenaAUsar = EscenaComienzo();


        Director.sharedDirector().runWithScene(escenaAUsar);
    }

    private Scene EscenaComienzo() {
        Log.d("dou", "arrancamion");
        Scene escena1;
        escena1 = Scene.node();
        Log.d("dou", "la capa");
        LaCapa Capa1;
        Capa1 = new LaCapa();
        escena1.addChild(Capa1);
        Log.d("dou", "ddevolviendo gato");
        return escena1;
    }

    class LaCapa extends Layer {
        public LaCapa() {
            super.schedule("ponerPunto", 3.0f);
        }

        public void ponerPunto(float diferenciaTiempo) {
            _Punto1 = Sprite.sprite("bokitaU.png");
            float anchoPunto = _Punto1.getWidth();
            float altoPunto = _Punto1.getHeight();
            Log.d("capo", "Posicion Inicial");
            Random r = new Random();
            CCPoint posicionInicial = new CCPoint();
            posicionInicial.x = r.nextInt((int) (_Pantalla.getWidth() - anchoPunto));
            posicionInicial.x += anchoPunto / 2;
            posicionInicial.y = r.nextInt((int) (_Pantalla.getHeight() - altoPunto));
            posicionInicial.y += altoPunto / 2;

            Log.d("capo", "Ubico el punto");
            _Punto1.setPosition(posicionInicial.x, posicionInicial.y);

            Log.d("capo", "determino la posicion final");
            CCPoint posicionFinal = new CCPoint();
            posicionFinal.x = posicionInicial.x;
            posicionFinal.y = 0 + _Punto1.getHeight() / 2;

            Log.d("capo", "inicio el movimiento");
            _Punto1.runAction(MoveTo.action(3, posicionFinal.x, posicionFinal.y));

            CCPoint destino = new CCPoint();
            if (_Punto1.getPositionX() > _Pantalla.getWidth() / 2) {
                destino.x = -_Punto1.getWidth() / 2;
            } else {
                destino.x = _Pantalla.getWidth() + _Punto1.getWidth() / 2;
            }
            if (_Punto1.getPositionY() > _Pantalla.getHeight() / 2) {
                destino.y = -_Punto1.getHeight() / 2;
            } else {
                destino.y = _Pantalla.getHeight() + _Punto1.getHeight() / 2;
            }
            _Punto1.runAction(MoveTo.action(3, destino.x, destino.y));

            super.addChild(_Punto1);
        }
    }


}
