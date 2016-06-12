package com.ezteam.sprites;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by batty on 6/9/2016.
 */
public class Scollable {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isDissapeared; /* Si l'objet disparait */

    public Scollable (float x, float y, int width, int height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.height = height;
        isDissapeared = false;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        if (position.x + width < 0) {
            isDissapeared = true;
        }
    }

    public void reset(float newX) {
        position.x = newX;
        isDissapeared = false;
    }

    public void stop() {
        velocity.x = 0;
    }

    public boolean isDissapeared() {
        return isDissapeared;
    }

    public float getTailX() {
        return position.x + width;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
