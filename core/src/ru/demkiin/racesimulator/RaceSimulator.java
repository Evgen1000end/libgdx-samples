package ru.demkiin.racesimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Предполагается не перегружать этот класс логикой приложения.
//А использовать его для инициализации и начальных настроек
public class RaceSimulator extends Game {

    public static final boolean DEBUG = false;
    public static final boolean RENDER_3D = true;
    public static final int WIDTH = 480;
    public static final int HEIGHT = 320;
    public GameScreen gameScreen;


	@Override
	public void create () {
        Gdx.app.log("Main", "Точка входа в приложение");

        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
	}
}
