package com.ezteam.gamemanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.ezteam.sprites.Dumbird;
import com.ezteam.sprites.ScrollHandler;
import com.ezteam.utils.AssetLoader;
import com.ezteam.utils.Constants;

/**
 * Created by batty on 6/7/2016.
 */
public class GameUpdater {
    public enum GameState {
        MENU, READY, RUNNING, OVER, HIGHSCORE
    }

    private Dumbird dumbird;
    private ScrollHandler scroller;
    private Rectangle ground;

    private int score = 0;
    private int centerScreen;
    private float runTime = 0;

    private GameState currentState;
    private GameRenderer renderer;

    // Initialiser Dumbird
    public GameUpdater(int centerScreen) {
        menu();
        this.centerScreen = centerScreen;
        dumbird = new Dumbird(33, centerScreen - 5, AssetLoader.dumbirdMid.getRegionWidth(), AssetLoader.dumbirdMid.getRegionHeight());
        scroller = new ScrollHandler(this, centerScreen + 66);
        ground = new Rectangle(0, centerScreen + 66, AssetLoader.ground.getRegionWidth(), AssetLoader.ground.getRegionHeight());
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;

            default:
                break;
        }
    }

    public void updateReady(float delta) {
        dumbird.updateReady(runTime);
        scroller.updateReady(delta);
    }

    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        dumbird.update(delta);
        scroller.update(delta);

        if (scroller.collides(dumbird) && dumbird.isAlive()) {
            scroller.stop();
            dumbird.die();
            AssetLoader.dead.play(Constants.VOLUME);
            renderer.prepareTransition(255, 255, 255, .3f);
            AssetLoader.fall.play(Constants.VOLUME);
        }

        if (Intersector.overlaps(dumbird.getBound(), ground)) {
            if (dumbird.isAlive()) {
                AssetLoader.dead.play(Constants.VOLUME);
                renderer.prepareTransition(255, 255, 255, .3f);
                dumbird.die();
            }

            scroller.stop();
            dumbird.decelerate();
            over();

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                highscore();
            }
        }
    }

    public boolean isHighScore() {
        Gdx.app.log("Updater", "highscore");
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isReady() {
        Gdx.app.log("Updater", "ready");
        return currentState == GameState.READY;
    }

    public boolean isMenu() {;
        Gdx.app.log("Updater", "menu");
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        Gdx.app.log("Updater", "running");
        return currentState == GameState.RUNNING;
    }

    public boolean isOver() {
        Gdx.app.log("Updater", "over");
        return currentState == GameState.OVER;
    }

    public void ready() {
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void restart() {
        score = 0;
        dumbird.restart(centerScreen - 5);
        scroller.restart();
        ready();
    }

    public void over() {
        currentState = GameState.OVER;
    }

    public void highscore() {
        currentState = GameState.HIGHSCORE;
    }

    public void menu() {
        currentState = GameState.MENU;
    }

    public Dumbird getDumbird() {
        return dumbird;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }


    public void addScore(int increment) {
        score += increment;
    }

    public int getCenterScreen() {
        return centerScreen;
    }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }
}
