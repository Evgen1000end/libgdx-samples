package ru.demkiin.racesimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;

public class GameScreen implements Screen {

    private RaceSimulator game;
    private World world;
    private WorldRenderer3D renderer3D;
    private FPSLogger fpsLogger;

    public GameScreen(RaceSimulator game) {

        fpsLogger = new FPSLogger();

        this.game = game;
        world = new World(game);
        renderer3D = new WorldRenderer3D(world);
    }


    @Override
    public void render(float delta) {
        world.update(delta);
        renderer3D.render();
        fpsLogger.log();
    }

    public String GetFps(){
       return fpsLogger.toString();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        renderer3D.dispose();
    }
}
