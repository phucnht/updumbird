package com.ezteam.sprites;

import com.ezteam.gamemanager.GameUpdater;
import com.ezteam.utils.AssetLoader;
import com.ezteam.utils.Constants;

/**
 * Created by batty on 6/9/2016.
 */
public class ScrollHandler {
    private Ground currentGround, pastGround;
    private Obstacle obstacle1, obstacle2, obstacle3;

    private GameUpdater updater;

    public ScrollHandler(GameUpdater updater, float yPos) {
        this.updater = updater;
        currentGround = new Ground(0, yPos, 143, 11, Constants.SCROLL_SPEED);
        pastGround = new Ground(currentGround.getTailX(), yPos, 143, 11, Constants.SCROLL_SPEED);

        obstacle1 = new Obstacle(210, 0, 22, 60, Constants.SCROLL_SPEED, yPos);
        obstacle2 = new Obstacle(obstacle1.getTailX() + Constants.OBSTACLE_ECART_X, 0, 22, 70, Constants.SCROLL_SPEED, yPos);
        obstacle3 = new Obstacle(obstacle2.getTailX() + Constants.OBSTACLE_ECART_X, 0, 22, 60, Constants.SCROLL_SPEED, yPos);
    }

    public void update(float delta) {
        currentGround.update(delta);
        pastGround.update(delta);
        obstacle1.update(delta);
        obstacle2.update(delta);
        obstacle3.update(delta);

        if (obstacle1.isDissapeared()) {
            obstacle1.reset(obstacle3.getTailX() + Constants.OBSTACLE_ECART_X);
        } else if (obstacle2.isDissapeared()) {
            obstacle2.reset(obstacle1.getTailX() + Constants.OBSTACLE_ECART_X);
        } else if (obstacle3.isDissapeared()) {
            obstacle3.reset(obstacle2.getTailX() + Constants.OBSTACLE_ECART_X);
        }

        if (currentGround.isDissapeared()) {
            currentGround.reset(pastGround.getTailX());
        } else if (pastGround.isDissapeared()) {
            pastGround.reset(currentGround.getTailX());
        }
    }

    public void updateReady(float delta) {
        currentGround.update(delta);
        pastGround.update(delta);

        // Same with grass
        if (currentGround.isDissapeared()) {
            currentGround.reset(pastGround.getTailX());
        } else if (pastGround.isDissapeared()) {
            pastGround.reset(currentGround.getTailX());
        }
    }

    public void stop() {
        currentGround.stop();
        pastGround.stop();
        obstacle1.stop();
        obstacle2.stop();
        obstacle3.stop();
    }

    public boolean collides(Dumbird dumbird) {
        if (!obstacle1.isScored()
                && obstacle1.getX() + (obstacle1.getWidth() / 2) < dumbird.getX()
                + dumbird.getWidth()) {
            addScore(1);
            obstacle1.setScored(true);
            AssetLoader.coin.play(Constants.VOLUME);
        } else if (!obstacle2.isScored()
                && obstacle2.getX() + (obstacle2.getWidth() / 2) < dumbird.getX()
                + dumbird.getWidth()) {
            addScore(1);
            obstacle2.setScored(true);
            AssetLoader.coin.play(Constants.VOLUME);

        } else if (!obstacle3.isScored()
                && obstacle3.getX() + (obstacle3.getWidth() / 2) < dumbird.getX()
                + dumbird.getWidth()) {
            addScore(1);
            obstacle3.setScored(true);
            AssetLoader.coin.play(Constants.VOLUME);

        }

        return (obstacle1.collides(dumbird) || obstacle2.collides(dumbird) || obstacle3
                .collides(dumbird));
    }

    public void restart() {
        currentGround.restart(0, Constants.SCROLL_SPEED);
        pastGround.restart(currentGround.getTailX(), Constants.SCROLL_SPEED);
        obstacle1.restart(210, Constants.SCROLL_SPEED);
        obstacle2.restart(obstacle1.getTailX() + Constants.OBSTACLE_ECART_X, Constants.SCROLL_SPEED);
        obstacle3.restart(obstacle2.getTailX() + Constants.OBSTACLE_ECART_X, Constants.SCROLL_SPEED);
    }

    private void addScore(int increment) {
        updater.addScore(increment);
    }

    public Ground getCurrentGround() {
        return currentGround;
    }

    public Ground getPastGround() {
        return pastGround;
    }

    public Obstacle getObstacle1() {
        return obstacle1;
    }

    public Obstacle getObstacle2() {
        return obstacle2;
    }

    public Obstacle getObstacle3() {
        return obstacle3;
    }
}
