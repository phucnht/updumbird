package com.ezteam.sprites;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.ezteam.utils.Constants;

import java.util.Random;

/**
 * Created by batty on 6/9/2016.
 */
public class Obstacle extends Scollable {
    private Random r;
    private Rectangle obstacleUp, obstacleDown, barreUp, barreDown;

    private float groundY;

    private boolean isScored = false;

    public Obstacle(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        this.groundY = groundY;
        r = new Random();

        obstacleUp = new Rectangle();
        obstacleDown = new Rectangle();

        barreUp = new Rectangle();
        barreDown = new Rectangle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        barreUp.set(position.x, position.y, width, height);
        barreDown.set(position.x, position.y + height + Constants.OBSTACLE_ECART_Y, width, groundY - (position.y + height + Constants.OBSTACLE_ECART_Y));

        obstacleUp.set(position.x - (Constants.OBSTACLE_WIDTH - width) / 2, position.y + height - Constants.OBSTACLE_HEIGHT, Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT);
        obstacleDown.set(position.x - (Constants.OBSTACLE_WIDTH - width) / 2, barreDown.y, Constants.OBSTACLE_WIDTH, Constants.OBSTACLE_HEIGHT);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        height = r.nextInt(90) + 15;
        isScored = false;
    }

    public boolean collides(Dumbird dumbird) {
        if (position.x < dumbird.getX() + dumbird.getWidth()) {
            return (Intersector.overlaps(dumbird.getBound(), barreUp)
                    || Intersector.overlaps(dumbird.getBound(), barreDown)
                    || Intersector.overlaps(dumbird.getBound(), obstacleUp) || Intersector
                    .overlaps(dumbird.getBound(), obstacleDown));
        }
        return false;
    }

    public void restart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }
}
