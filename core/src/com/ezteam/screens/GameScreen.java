package com.ezteam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.ezteam.gamemanager.GameRenderer;
import com.ezteam.gamemanager.GameUpdater;
import com.ezteam.utils.Constants;
import com.ezteam.utils.InputHandler;

/**
 * Created by batty on 6/7/2016.
 */
public class GameScreen implements Screen {
    private GameUpdater updater;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = Constants.PORTVIEW_WIDTH / 2;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        int centerScreen = (int) (gameHeight / 2);

        updater = new GameUpdater(centerScreen);
        Gdx.input.setInputProcessor(new InputHandler(updater, screenWidth / gameWidth, screenHeight / gameHeight));
        renderer = new GameRenderer(updater, centerScreen, (int) gameHeight);
        updater.setRenderer(renderer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        updater.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
