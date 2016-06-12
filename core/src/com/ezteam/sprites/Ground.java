package com.ezteam.sprites;

/**
 * Created by batty on 6/9/2016.
 */
public class Ground extends Scollable {
    public Ground(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

    public void restart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }
}
