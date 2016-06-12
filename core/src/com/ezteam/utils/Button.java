package com.ezteam.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.ezteam.gamemanager.GameUpdater;

/**
 * Created by batty on 6/9/2016.
 */
public class Button {
    private float x, y, width, height;

    private TextureRegion button, buttonPressed;

    private Rectangle bounds;

    private boolean isPressed = false;

    public Button(float x, float y, float width, float height,
                  TextureRegion button, TextureRegion buttonPressed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.button = button;
        this.buttonPressed = buttonPressed;
        bounds = new Rectangle(x, y, width, height);
    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher) {
        if (isPressed) {
            batcher.draw(buttonPressed, x, y, width, height);
        } else {
            batcher.draw(button, x, y, width, height);
        }
    }

    public boolean isTouchDown(int screenX, int screenY) {
        if (isClicked(screenX, screenY)) {
            isPressed = !isPressed;
            return true;
        }

        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        if (isClicked(screenX, screenY)) {
            if (buttonPressed == AssetLoader.playButtonPressed || buttonPressed == AssetLoader.homeButtonPressed || buttonPressed == AssetLoader.retryButtonPressed) {
                isPressed = false;
            }
            return true;
        }

        if (buttonPressed == AssetLoader.playButtonPressed || buttonPressed == AssetLoader.homeButtonPressed || buttonPressed == AssetLoader.retryButtonPressed) {
            isPressed = false;
        }

        return false;
    }

    public boolean isPressed() {
        return isPressed;
    }
}
