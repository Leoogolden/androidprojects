package com.example.tp9;

import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class clsJuego {
    CCSize _Pantalla;
    CCGLSurfaceView _VistaDelJuego;
    Sprite _Jugador;
    Sprite _Enemigo;
    ArrayList _listaEnemigos;
    boolean _estaTocandoAlJugador;
    Label _lblPuntos;
    int puntos = 0;
    Label _lblJugar;
    Label _lblTituloJuego;


    public clsJuego(CCGLSurfaceView vistausa) {

        _VistaDelJuego = vistausa;
    }

    public void ComenzarJuego() {
        Director.sharedDirector().attachInView(_VistaDelJuego);
        _Pantalla = Director.sharedDirector().displaySize();
        Scene escenaausar;
        escenaausar = escenaComienzo();

        Director.sharedDirector().runWithScene(escenaausar);
    }

    Scene escenaComienzo() {
        Scene escenadevuelve;
        escenadevuelve = Scene.node();
        capaJuego capa1;
        capa1 = new capaJuego();
        escenadevuelve.addChild(capa1);
        return escenadevuelve;

    }

    class capaMenu extends Layer {

        public capaMenu() {

            _lblTituloJuego = Label.label("Gallardo Recopado!", "Verdana", 300);
            _lblTituloJuego.setPosition(_Pantalla.getWidth() / 2, _Pantalla.getHeight() / 2);

            _lblJugar = Label.label("Jugar", "Verdana", 70);
            _lblJugar.setPosition(_Pantalla.getWidth() / 2, (_Pantalla.getHeight() / 2 - _lblTituloJuego.getHeight() * 2));
            CCColor3B colorPuntos;
            colorPuntos = new CCColor3B(84, -72, 50);
            _lblTituloJuego.setColor(colorPuntos);
            _lblJugar.setColor(colorPuntos);

            super.addChild(_lblTituloJuego, -2);
            super.addChild(_lblJugar);
        }

        void PonerMenu() {


        }

    }


    class capaJuego extends Layer {

        public capaJuego() {
            _listaEnemigos = new ArrayList();
            ponerImagenFondo();
            PonerPunto();
            ponerJugador();

            super.schedule("detectarColisiones", 0.25f);
            super.schedule("TocaPiso", 0.25f);
            super.schedule("ponerObjeto", 2.5f);

            setIsTouchEnabled(true);

        }

        void ponerJugador() {
            _Jugador = Sprite.sprite("gallardo.png");
            _Jugador.setPosition(100, 300);
            float posicionX, posicionY;
            posicionX = _Pantalla.getWidth() / 2;
            posicionY = _Jugador.getWidth() / 2;
            _Jugador.setPosition(posicionX, posicionY);

            super.addChild(_Jugador);
        }

        void PonerPunto() {
            _lblPuntos = Label.label("fdsaf" + puntos, "", 100);
            _lblPuntos.setPosition(_Pantalla.getWidth() / 2, _Pantalla.getHeight() - _lblPuntos.getHeight() / 2);
            CCColor3B colorPuntos;
            colorPuntos = new CCColor3B(84, -72, 50);
            Log.d("puntos", "van " + puntos + " puntos y " + _lblPuntos.toString());
            _lblPuntos.setColor(colorPuntos);
            super.addChild(_lblPuntos, -2);
        }

        void ponerImagenFondo() {

            Sprite imagenfondo;
            imagenfondo = Sprite.sprite("bernabeu.jpg");
            imagenfondo.setPosition(_Pantalla.getWidth() / 2, _Pantalla.getHeight() / 2);

            float factorancho, factoralto;
            factorancho = _Pantalla.getWidth() / imagenfondo.getWidth();
            factoralto = _Pantalla.getHeight() / imagenfondo.getHeight();
            imagenfondo.runAction(ScaleBy.action(0.01f, factorancho, factoralto));
            super.addChild(imagenfondo);
        }


        public void ponerObjeto(float diferenciatiempo) {
            if (_listaEnemigos.size() == 30) {
                super.unschedule("ponerObjeto");
                return;
            }
            Sprite Objeto = Sprite.sprite("copaArgentina.png");


            float cuantox = 0;
            float cuantoy = 0;
            int a = (int) (Math.random() * (15 - 1) + 1);
            switch (a) {

                case (1):
                case (2):
                case (3):
                case (4):
                case (5):
                case (6):

                    Log.d("objetos", "es copaArg");
                    Objeto = Sprite.sprite("copaArgentina.png");
                    cuantox = Objeto.getWidth();
                    cuantoy = Objeto.getHeight();
                    break;

                case (7):
                case (8):
                case (9):
                case (10):
                    Log.d("objetos", "es copaSud");
                    Objeto = Sprite.sprite("CopaSudamericana.png");
                    cuantox = Objeto.getWidth();
                    cuantoy = Objeto.getHeight();
                    break;
                case (11):
                    Log.d("objetos", "es copaLib");
                    Objeto = Sprite.sprite("CopaLibertadores.png");
                    cuantox = Objeto.getWidth();
                    cuantoy = Objeto.getHeight();
                    break;
                case (12):
                case (13):
                case (14):
                case (15):

                    Log.d("objetos", "es enemigo");
                    Objeto = Sprite.sprite(Math.random() < 0.5 ? "barcelona.png" : "bayern.png");
                    cuantox = Objeto.getWidth();
                    cuantoy = Objeto.getHeight();
                    break;


            }


            Log.d("comoo", "x es " + cuantox + " e y " + cuantoy);
            Log.d("comoo", "ancho pantalla " + _Pantalla.getWidth());
            CCPoint posinicial;
            posinicial = new

                    CCPoint();

            Random posXRandom;
            posXRandom = new

                    Random();

            posinicial.x = posXRandom.nextInt((int) (_Pantalla.getWidth() - cuantox));
            posinicial.x += cuantox / 2;


            posinicial.y = _Pantalla.getHeight() + cuantoy / 2;

            Objeto.setPosition(posinicial.x, posinicial.y);

            CCPoint posfinal;
            posfinal = new

                    CCPoint();

            posfinal.x = posinicial.x;
            posfinal.y = 0 + _Jugador.getHeight() / 4;
            MoveTo mueve;
            mueve = MoveTo.action(3, posfinal.x, posfinal.y);
            Objeto.runAction(mueve);


            _listaEnemigos.add(Objeto);
            Log.d("coomo", "van " + _listaEnemigos.size() + " enemigos");

            super.

                    addChild(Objeto, 10);

        }

        public boolean InterseccionEntreSprites(Sprite Sprite1, Sprite Sprite2) {
            boolean HayInterseccion = false;
            float Sp1Arriba, Sp1Abajo, Sp1Derecha, Sp1Izquierda, Sp2Arriba, Sp2Abajo, Sp2Derecha, Sp2Izquierda;

            Sp1Arriba = Sprite1.getPositionY() + Sprite1.getHeight() / 2;
            Sp1Abajo = Sprite1.getPositionY() - Sprite1.getHeight() / 2;
            Sp1Derecha = Sprite1.getPositionX() + Sprite1.getWidth() / 2;
            Sp1Izquierda = Sprite1.getPositionX() - Sprite1.getWidth() / 2;
            Sp2Arriba = Sprite2.getPositionY() + Sprite2.getHeight() / 2;
            Sp2Abajo = Sprite2.getPositionY() - Sprite2.getHeight() / 2;
            Sp2Derecha = Sprite2.getPositionX() + Sprite2.getWidth() / 2;
            Sp2Izquierda = Sprite2.getPositionX() - Sprite2.getWidth() / 2;


            Log.d("IntEntSprites", "Arr: " + Sp1Arriba + " - Ab:" + Sp1Abajo + "- Der:" + Sp1Derecha + " -Izq:" + "Sp1Izquierda");
            Log.d(" IntEntSprites  ", "Sp2 Arr:" + Sp2Arriba + " -Ab: " + Sp2Abajo + "-Der:" + "Sp2Derecha" + "-Izq: " + Sp2Izquierda);


            //Me fijo si el vértice superior derecho de Sp1 está dentro de Sp2
            if (Sp1Arriba >= Sp2Abajo && Sp1Arriba <= Sp2Arriba &&
                    Sp1Derecha >= Sp2Izquierda && Sp1Derecha <= Sp2Derecha) {
                HayInterseccion = true;
                Log.d("IntEntSprites", "Intersección caso 1");
            }
            //Me fijo si el vértice superior izquierdo de Sp1 está dentro de Sp2
            if (Sp1Arriba >= Sp2Abajo && Sp1Arriba <= Sp2Arriba &&
                    Sp1Izquierda >= Sp2Izquierda && Sp1Izquierda <= Sp2Derecha) {
                HayInterseccion = true;
                Log.d("IntEntSprites", "Intersección caso 2");
            }
            //Me fijo si el vértice inferior derecho de Sp1 está dentro de Sp2
            if (Sp1Abajo >= Sp2Abajo && Sp1Abajo <= Sp2Arriba &&
                    Sp1Derecha >= Sp2Izquierda && Sp1Derecha <= Sp2Derecha) {
                HayInterseccion = true;
                Log.d("IntEntSprites", "Intersección caso 3");
            }
            //Me fijo si el vértice inferior izquierdo de Sp1 está dentro de Sp2
            if (Sp1Abajo >= Sp2Abajo && Sp1Abajo <= Sp2Arriba &&
                    Sp1Izquierda >= Sp2Izquierda && Sp1Izquierda <= Sp2Derecha) {
                HayInterseccion = true;
                Log.d("IntEntSprites", "Intersección caso 4");
            }
            //Me fijo si el vértice superior derecho de Sp2 está dentro de Sp1
            if (Sp2Arriba >= Sp1Abajo && Sp2Arriba <= Sp1Arriba &&
                    Sp2Derecha >= Sp1Izquierda && Sp2Derecha <= Sp1Derecha) {
                HayInterseccion = true;
                Log.d("IntEntSprites", "Intersección caso 5");
            }
            //Me fijo si el vértice superior izquierdo de Sp1 está dentro de Sp2
            if (Sp2Arriba >= Sp1Abajo && Sp2Arriba <= Sp1Arriba &&
                    Sp2Izquierda >= Sp1Izquierda && Sp2Izquierda <= Sp1Derecha) {
                HayInterseccion = true;
                Log.d("IntEntSprites", "Intersección caso 6");
            }
            //Me fijo si el vértice inferior derecho de Sp1 está dentro de Sp2
            if (Sp2Abajo >= Sp1Abajo && Sp2Abajo <= Sp1Arriba &&
                    Sp2Derecha >= Sp1Izquierda && Sp2Derecha <= Sp1Derecha) {
                HayInterseccion = true;
                Log.d("IntEntSprites", "Intersección caso 7");
            }
            //Me fijo si el vértice inferior izquierdo de Sp1 está dentro de Sp2
            if (Sp2Abajo >= Sp1Abajo && Sp2Abajo <= Sp1Arriba &&
                    Sp2Izquierda >= Sp1Izquierda && Sp2Izquierda <= Sp1Derecha) {
                HayInterseccion = true;
                Log.d("IntEntSprites", "Intersección caso 8");
            }
            Log.d("IntEntSprites", "Hay intersección: " + HayInterseccion);
            return HayInterseccion;
        }

        public void detectarColisiones(float diferenciatiempo) {


            for (int puntero = 0; puntero < _listaEnemigos.size(); puntero++) {
                boolean escopa;
                escopa = true;
                Sprite unEnemigoaVerificar;
                Sprite barca = Sprite.sprite("barcelona.png");
                Sprite bayern = Sprite.sprite("bayern.png");
                Sprite copaArg = Sprite.sprite("copaArgentina.png");
                Sprite copaSud = Sprite.sprite("CopaSudamericana.png");
                Sprite copaLib = Sprite.sprite("CopaLibertadores.png");
                unEnemigoaVerificar = (Sprite) _listaEnemigos.get(puntero);
                if (unEnemigoaVerificar.displayFrame() == barca.displayFrame() || unEnemigoaVerificar.displayFrame() == bayern.displayFrame()) {
                    Log.d("choca", "es enemigo");
                    escopa = false;

                }

                if (InterseccionEntreSprites((_Jugador), unEnemigoaVerificar)) {
                    Log.d("choca", "booom");
                    if (escopa) {
                        if (unEnemigoaVerificar.displayFrame() == copaArg.displayFrame()) {
                            Log.d("puntos", "agarro una copa arg");
                            puntos++;
                            _lblPuntos.setString("" + puntos);
                        } else if (unEnemigoaVerificar.displayFrame() == copaSud.displayFrame()) {
                            Log.d("puntos", "agarro una copa sud");
                            puntos = +3;
                            _lblPuntos.setString("" + puntos);
                        } else if (unEnemigoaVerificar.displayFrame() == copaLib.displayFrame()) {
                            Log.d("puntos", "agarro una copa lib");
                            puntos = +5;
                            _lblPuntos.setString("" + puntos);
                        }
                        super.removeChild(unEnemigoaVerificar, true);
                    } else {
                        Log.d("choca", "choco un enemigo");
                        super.unschedule("ponerObjeto");
                        super.unschedule("detectarColisiones");
                        super.removeChild(unEnemigoaVerificar, true);
                        _listaEnemigos.remove(puntero);
                        Log.d("elimino2", "eliminando enemigo ya toco piso agarro " + _listaEnemigos.size() + " copas");


                    }
                } else {

                    Log.d("choca", "Nadie toco");
                    finTrayecto(unEnemigoaVerificar, escopa);

                }


            }

        }

        public void finTrayecto(Sprite objetollamador, boolean a) {
            Log.d("elimino", "entra");

            if (objetollamador.getPositionY() == _Jugador.getHeight() / 4) {
                Log.d("elimino2", "eliminando enemigo ya toco piso agarro " + _listaEnemigos.size() + " copas");
                if (a) {
                    Log.d("choca", "PERDISTE TOCO EL PISO LA COPA");
                    super.unschedule("ponerObjeto");
                    super.unschedule("detectarColisiones");

                }
                super.removeChild(objetollamador, true);

            }


        }

        @Override
        public boolean ccTouchesBegan(MotionEvent evento) {
            float xtouched, ytouched;
            xtouched = evento.getX();
            ytouched = _Pantalla.getHeight() - evento.getY();
            Log.d("Muevetoque", "arranca toque en " + xtouched + ", " + ytouched);

            if (InterseccionEntrePuntoySprite(_Jugador, xtouched, ytouched)) {
                moverNaveJugador(xtouched, ytouched);
                _estaTocandoAlJugador = true;
            } else {
                _estaTocandoAlJugador = false;
            }

            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent evento) {
            float xtouched, ytouched;
            xtouched = evento.getX();
            ytouched = _Pantalla.getHeight() - evento.getY();
            Log.d("Muevetoque", "mueve toque a " + xtouched + ", " + ytouched);
            if (_estaTocandoAlJugador) {
                moverNaveJugador(xtouched, ytouched);
            }
            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent evento) {
            float xtouched, ytouched;
            xtouched = evento.getX();
            ytouched = _Pantalla.getHeight() - evento.getY();
            Log.d("Muevetoque", "termina toque en " + xtouched + ", " + ytouched);
            _estaTocandoAlJugador = false;

            return true;
        }

        void moverNaveJugador(float x, float y) {
            y = 0 + _Jugador.getHeight() / 2;
            _Jugador.setPosition(x, y);
        }

        public boolean InterseccionEntrePuntoySprite(Sprite SpriteAVerificar, Float
                puntoXAVerificar, Float puntoYAVerificar) {
            Boolean HayInterseccion = false;
//Determino los bordes de cada Sprite
            Float SpArriba, SpAbajo, SpDerecha, SpIzquierda;
            SpArriba = SpriteAVerificar.getPositionY() + SpriteAVerificar.getHeight() / 2;
            SpAbajo = SpriteAVerificar.getPositionY() - SpriteAVerificar.getHeight() / 2;
            SpDerecha = SpriteAVerificar.getPositionX() + SpriteAVerificar.getWidth() / 2;
            SpIzquierda = SpriteAVerificar.getPositionX() - SpriteAVerificar.getWidth() / 2;

            Log.d("IntEntSpriteYPunto", "Sp Arr: " + SpArriba + " - Ab: " + SpAbajo + " - Der:" + SpDerecha + " - Izq: " + SpIzquierda);
            Log.d("IntEntSpriteYPunto", "X: " + puntoXAVerificar + "- Y:" + puntoYAVerificar);

            if (puntoXAVerificar >= SpIzquierda && puntoXAVerificar <= SpDerecha &&
                    puntoYAVerificar >= SpAbajo && puntoYAVerificar <= SpArriba) {
                HayInterseccion = true;
            }
            Log.d("IntEntSpriteYPunto", "Hay intersección: " + HayInterseccion);
            return HayInterseccion;
        }


    }
}
