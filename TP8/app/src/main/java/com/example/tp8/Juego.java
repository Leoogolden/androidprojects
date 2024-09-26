package com.example.tp8;

import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

public class Juego {
    CCGLSurfaceView Vista;
    CCSize Pantalla;
    Sprite _fot1, _fot2;
    boolean Foto1 = false, Foto2 = false;


    public Juego(CCGLSurfaceView VistaJuego) {

        Log.d("doulol", "arranca la clase");
        Vista = VistaJuego;

    }

    private Scene EscenaArranca() {
        Log.d("arrancamion", "primer log");
        Scene escenadevuelve;
        escenadevuelve = Scene.node();
        Log.d("arrancamion", "segundo log y capa");
        capaJuego capa;
        capa = new capaJuego();
        escenadevuelve.addChild(capa);
        Log.d("arrancamion", "tercer log y vuelve la escena");
        return escenadevuelve;
    }

    public void comenzarJuego() {
        Log.d("arrancamion", "Comienza el juego");
        Director.sharedDirector().attachInView(Vista);

        Pantalla = Director.sharedDirector().displaySize();
        Log.d("arrancamion", "Ancho " + Pantalla.getWidth() + " - Alto " + Pantalla.getHeight());

        Scene escena;
        escena = EscenaArranca();

        Log.d("arrancamion", "Inicia la escena");
        Director.sharedDirector().runWithScene(escena);
    }

    class capaJuego extends Layer {
        public capaJuego() {
            setIsTouchEnabled(true);
            _fot1 = Sprite.sprite("bokitaU.png");
            ponerFoto(_fot1);
            _fot2 = Sprite.sprite("bokita.png");
            ponerFoto(_fot2);
            super.schedule("chkColision", 0.25f);
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            float xTocada, yTocada;
            xTocada = event.getX();
            yTocada = Pantalla.getHeight() - event.getY();
            if (Interseccion(_fot1, xTocada, yTocada)) {
                Foto1 = true;
                Foto2 = false;
            } else if (Interseccion(_fot2, xTocada, yTocada)) {
                Foto2 = true;
                Foto1 = false;
            } else {
                Foto1 = false;
                Foto2 = false;
            }

            Log.d("ControlTocar", "Toque x=" + xTocada + " en y=" + yTocada);
            return true;
        }


        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            float xTocada, yTocada;
            xTocada = event.getX();
            yTocada = Pantalla.getHeight() - event.getY();
            Log.d("ControlTocar", "mueve x=" + xTocada + " y=" + yTocada);
            if (Foto1) {
                moverFoto(_fot1, xTocada, yTocada);
            }
            if (Foto2) {
                moverFoto(_fot2, xTocada, yTocada);
            }

            return true;
        }

        void moverFoto(Sprite foto, float xAMover, float yAMover) {
            Log.d("mueve", "me piden que me ubique en x" + xAMover + "y" + yAMover);
            foto.setPosition(xAMover, yAMover);
        }


        public void ponerFoto(Sprite foto) {

            float anchoFoto = foto.getWidth();
            float altoFoto = foto.getHeight();
            Log.d("eskerit", "Primer Pos");
            Random r = new Random();
            CCPoint posicionInicial = new CCPoint();
            posicionInicial.x = r.nextInt((int) (Pantalla.getWidth() - anchoFoto));
            posicionInicial.x += anchoFoto / 2;
            posicionInicial.y = r.nextInt((int) (Pantalla.getHeight() - altoFoto));
            posicionInicial.y += altoFoto / 2;
            Log.d("Comenzar", "Ubico la foto");
            foto.setPosition(posicionInicial.x, posicionInicial.y);
            CCPoint destino = new CCPoint();
            super.addChild(foto);
        }

        public boolean Interseccion(Sprite SpriteAVerificar, Float puntoXAVerificar, Float puntoYAVerificar) {

            boolean HayInterseccion = false;
            Float SpArriba, SpAbajo, SpDerecha, SpIzquierda;
            SpArriba = SpriteAVerificar.getPositionY() + SpriteAVerificar.getHeight() / 2;
            SpAbajo = SpriteAVerificar.getPositionY() - SpriteAVerificar.getHeight() / 2;
            SpDerecha = SpriteAVerificar.getPositionX() + SpriteAVerificar.getWidth() / 2;
            SpIzquierda = SpriteAVerificar.getPositionX() - SpriteAVerificar.getWidth() / 2;


            if (puntoXAVerificar >= SpIzquierda && puntoXAVerificar <= SpDerecha && puntoYAVerificar >= SpAbajo && puntoYAVerificar <= SpArriba) {
                HayInterseccion = true;
            }

            return HayInterseccion;
        }

        public void chkColision(float f) {
            if (detectarColision(_fot1, _fot2)) {
                if (Foto1) {

                    MoveBy moverseAbajo, moverseDerecha, moverseArriba, moverseIzquierda;
                    moverseAbajo = MoveBy.action(1, 0, -300);
                    moverseDerecha = MoveBy.action(1, 200, 0);
                    moverseArriba = MoveBy.action(1, 0, 300);
                    moverseIzquierda = MoveBy.action(1, -200, 0);

                    IntervalAction movimiento;
                    movimiento = Sequence.actions(moverseAbajo, moverseDerecha, moverseArriba, moverseIzquierda);
                    _fot2.runAction(movimiento);
                } else {

                    MoveBy moverseAbajo, moverseDerecha, moverseArriba, moverseIzquierda;
                    moverseAbajo = MoveBy.action(1, 0, -300);
                    moverseDerecha = MoveBy.action(1, 200, 0);
                    moverseArriba = MoveBy.action(1, 0, 300);
                    moverseIzquierda = MoveBy.action(1, -200, 0);

                    IntervalAction movimiento;
                    movimiento = Sequence.actions(moverseAbajo, moverseDerecha, moverseArriba, moverseIzquierda);
                    _fot1.runAction(movimiento);
                }
            }


            Log.d("asd", "P");
        }

        public Boolean detectarColision(Sprite Sprite1, Sprite Sprite2) {

            boolean HayInterseccion = false;

            Float Sp1Arriba, Sp1Abajo, Sp1Derecha, Sp1Izquierda;
            Float Sp2Arriba, Sp2Abajo, Sp2Derecha, Sp2Izquierda;

            Sp1Arriba = Sprite1.getPositionY() + Sprite1.getHeight() / 2;
            Sp1Abajo = Sprite1.getPositionY() - Sprite1.getHeight() / 2;
            Sp1Derecha = Sprite1.getPositionX() + Sprite1.getWidth() / 2;
            Sp1Izquierda = Sprite1.getPositionX() - Sprite1.getWidth() / 2;
            Sp2Arriba = Sprite2.getPositionY() + Sprite2.getHeight() / 2;
            Sp2Abajo = Sprite2.getPositionY() - Sprite2.getHeight() / 2;
            Sp2Derecha = Sprite2.getPositionX() + Sprite2.getWidth() / 2;
            Sp2Izquierda = Sprite2.getPositionX() - Sprite2.getWidth() / 2;

            //V sup der de foto1 está dentro de foto2
            if (Sp1Arriba >= Sp2Abajo && Sp1Arriba <= Sp2Arriba && Sp1Derecha >= Sp2Izquierda && Sp1Derecha <= Sp2Derecha) {
                HayInterseccion = true;
            }

            //V sup izq de foto1 está dentro de foto2
            if (Sp1Arriba >= Sp2Abajo && Sp1Arriba <= Sp2Arriba && Sp1Izquierda >= Sp2Izquierda && Sp1Izquierda <= Sp2Derecha) {

                HayInterseccion = true;
            }

            //V inf der de foto1 está dentro de foto2
            if (Sp1Abajo >= Sp2Abajo && Sp1Abajo <= Sp2Arriba && Sp1Derecha >= Sp2Izquierda && Sp1Derecha <= Sp2Derecha) {
                HayInterseccion = true;
            }

            //V inf izq de foto1 está dentro de foto2
            if (Sp1Abajo >= Sp2Abajo && Sp1Abajo <= Sp2Arriba && Sp1Izquierda <= Sp2Izquierda && Sp1Izquierda <= Sp2Derecha) {
                HayInterseccion = true;
            }

            //V sup der de foto2 está dentro de foto1
            if (Sp2Arriba >= Sp1Abajo && Sp2Arriba <= Sp1Arriba && Sp2Derecha >= Sp1Izquierda && Sp2Derecha <= Sp1Derecha) {
                HayInterseccion = true;
            }

            Log.d("Hay colision", "" + HayInterseccion);
            return HayInterseccion;
        }
    }

}