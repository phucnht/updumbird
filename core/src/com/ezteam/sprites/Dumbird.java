package com.ezteam.sprites;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.ezteam.utils.AssetLoader;
import com.ezteam.utils.Constants;

/**
 * Created by batty on 6/8/2016.
 */
public class Dumbird {
    // Physical attributes
    private Vector2 pos;
    private Vector2 accel;
    private Vector2 velocity;

    // Shape & rotation
    private int width;
    private int height;
    private float rotation;

    private float originalY;

    private boolean isAlive;

    private Circle bound;

    public Dumbird(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.originalY = y;
        pos = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        accel = new Vector2(0, Constants.GRAVITY);
        bound = new Circle();
        isAlive = true;
    }

    // On doit multiplier le résultat avec delta pour un mouvement indépendent avec FPS ( si le jeu devient lent )
    public void update(float delta) {
        velocity.add(accel.cpy().scl(delta));

        // Pour le Dumbird ne tombe pas trop vite
        if (velocity.y > 200) {
            velocity.y = 200;
        }

        // CEILING CHECK
        if (pos.y < -13) {
            pos.y = -13;
            velocity.y = 0;
        }

        // Dumbird volant
        if (velocity.y < 0) {
            rotation -= 600 * delta;
            if (rotation < -20) {
                rotation = -20;
            }
        }

        // Dumbird tombant
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90;
            }
        }

        pos.add(velocity.cpy().scl(delta));
        bound.set(pos.x + 9, pos.y + 6, 6.5f);
    }

    public void updateReady(float runTime) {
        pos.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }

    public void onTouch() {
        if (isAlive) {
            AssetLoader.flap.play(Constants.VOLUME);
            velocity.y = -140;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public float getX() {
        return pos.x;
    }

    public float getY() {
        return pos.y;
    }

    public Circle getBound() {
        return bound;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void die() {
        isAlive = false;
        velocity.y = 0;
    }

    public void restart(int y) {
        rotation = 0;
        pos.y = y;
        velocity.x = 0;
        velocity.y = 0;
        accel.x = 0;
        accel.y = 460;
        isAlive = true;
    }

    public void decelerate() {
        accel.y = 0;
    }
    // Etats
    public boolean isNotFlap() {
        return velocity.y > 70 || !isAlive;
    }

    public boolean isFalling() {
        return velocity.y > 110;
    }
}
