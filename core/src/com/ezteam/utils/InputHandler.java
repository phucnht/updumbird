package com.ezteam.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.ezteam.gamemanager.GameUpdater;
import com.ezteam.sprites.Dumbird;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by batty on 6/8/2016.
 */
public class InputHandler implements InputProcessor {
    private Dumbird dumbird;
    private GameUpdater updater;

    private List<Button> buttons;

    private Button playButton, musicButton, soundButton, retryButton, homeButton;

    private float scaleFactorX;
    private float scaleFactorY;

    // Initialiser Dumbird
    public InputHandler(GameUpdater updater, float scaleFactorX, float scaleFactorY) {
        this.updater = updater;
        dumbird = updater.getDumbird();

        int centerScreen = updater.getCenterScreen();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        buttons = new ArrayList<Button>();

        playButton = new Button(
                Constants.PORTVIEW_WIDTH / 4 - (AssetLoader.playButton.getRegionWidth() / 2), centerScreen + 25,
                AssetLoader.playButton.getRegionWidth(), AssetLoader.playButton.getRegionHeight(),
                AssetLoader.playButton, AssetLoader.playButtonPressed
        );
        musicButton = new Button(
                Constants.PORTVIEW_WIDTH / 2 - (AssetLoader.musicButton.getRegionWidth() / 2) - 24, centerScreen - 96,
                AssetLoader.musicButton.getRegionWidth() / 2.0f, AssetLoader.musicButton.getRegionHeight() / 2.0f,
                AssetLoader.musicButton, AssetLoader.musicButtonPressed
        );
        soundButton = new Button(
                Constants.PORTVIEW_WIDTH / 2 - (AssetLoader.soundButton.getRegionWidth() / 2) - 5, centerScreen - 96,
                AssetLoader.soundButton.getRegionWidth() / 2.0f, AssetLoader.soundButton.getRegionHeight() / 2.0f,
                AssetLoader.soundButton, AssetLoader.soundButtonPressed
        );
        retryButton = new Button(
                30, centerScreen + 20,
                AssetLoader.retryButton.getRegionWidth(), AssetLoader.retryButton.getRegionHeight(),
                AssetLoader.retryButton, AssetLoader.retryButtonPressed
        );
        homeButton = new Button(
                78, centerScreen + 20,
                AssetLoader.homeButton.getRegionWidth(), AssetLoader.homeButton.getRegionHeight(),
                AssetLoader.homeButton, AssetLoader.homeButtonPressed
        );
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (updater.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
            musicButton.isTouchDown(screenX, screenY);
            soundButton.isTouchDown(screenX, screenY);
        } else if (updater.isReady()) {
            updater.start();
            dumbird.onTouch();
        } else if (updater.isRunning()) {
            dumbird.onTouch();
        } else if (updater.isOver()) {
            retryButton.isTouchDown(screenX, screenY);
            homeButton.isTouchDown(screenX, screenY);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (updater.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                updater.ready();
                AssetLoader.flap.play(Constants.VOLUME);
                return true;
            }
            if (musicButton.isTouchUp(screenX, screenY)) {
                if (musicButton.isPressed()) {
                    AssetLoader.music.setVolume(0);
                } else {
                    AssetLoader.music.setVolume(1.0f);
                }
                return true;
            }
            if (soundButton.isTouchUp(screenX, screenY)) {
                if (soundButton.isPressed()) {
                    Constants.VOLUME = 0;
                } else {
                    Constants.VOLUME = 1.0f;
                }
                return true;
            }
        } else if (updater.isHighScore() || updater.isOver()) {
            if (retryButton.isTouchUp(screenX, screenY)) {
                updater.restart();
                return true;
            }
            if (homeButton.isTouchUp(screenX, screenY)) {
                updater.restart();
                updater.menu();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public Button getRetryButton() {
        return retryButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getMusicButton() {
        return musicButton;
    }

    public Button getSoundButton() {
        return soundButton;
    }
}
