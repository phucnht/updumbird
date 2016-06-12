package com.ezteam.gamemanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.ezteam.sprites.Dumbird;
import com.ezteam.sprites.Ground;
import com.ezteam.sprites.Obstacle;
import com.ezteam.sprites.ScrollHandler;
import com.ezteam.tweenaccessors.Value;
import com.ezteam.tweenaccessors.ValueAccessor;
import com.ezteam.utils.AssetLoader;
import com.ezteam.utils.Button;
import com.ezteam.utils.Constants;
import com.ezteam.utils.InputHandler;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by batty on 6/7/2016.
 */
public class GameRenderer {
    // Objets
    private Dumbird dumbird;
    private ScrollHandler scroller;
    private Ground currentGround, pastGround;
    private Obstacle obstacle1, obstacle2, obstacle3;

    // Assets
    private Animation dumbirdAnimation;
    private TextureRegion bg, ground, dumbirdMid, barre, obstacleUp, obstacleDown, ready, dumbirdLogo, over, highscore, scoreBoard;
    private BitmapFont shadow, font;
    private Music music;

    // Updater
    private GameUpdater updater;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    // Tweening
    private TweenManager manager;
    private Value alpha = new Value();

    // Buttons
    private List<Button> buttons;
    private Color transitionColor;

    // Attributes
    private int centerScreen;
    private int middleX = Constants.PORTVIEW_WIDTH;

    public GameRenderer(GameUpdater updater, int centerScreen, int gameHeight) {
        this.updater = updater;

        this.centerScreen = centerScreen;
        this.buttons = ((InputHandler) Gdx.input.getInputProcessor()).getButtons();

        transitionColor = new Color();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, middleX / 2, gameHeight);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        InitSprites();
        InitAssets();
        prepareTransition(255, 255, 255, .5f);
    }

    private void InitSprites() {
        dumbird = updater.getDumbird();
        scroller = updater.getScroller();
        currentGround = scroller.getCurrentGround();
        pastGround = scroller.getPastGround();
        obstacle1 = scroller.getObstacle1();
        obstacle2 = scroller.getObstacle2();
        obstacle3 = scroller.getObstacle3();
    }

    private void InitAssets() {
        bg = AssetLoader.bg;
        ground = AssetLoader.ground;
        dumbirdAnimation = AssetLoader.dumbirdAnimation;
        dumbirdMid = AssetLoader.dumbirdMid;
        obstacleUp = AssetLoader.obstacleUp;
        obstacleDown = AssetLoader.obstacleDown;
        barre = AssetLoader.barre;
        shadow = AssetLoader.shadow;
        font = AssetLoader.font;
        ready = AssetLoader.ready;
        dumbirdLogo = AssetLoader.dumbirdLogo;
        over = AssetLoader.over;
        highscore = AssetLoader.highscore;
        scoreBoard = AssetLoader.scoreBoard;
        music = AssetLoader.music;
    }

    public void prepareTransition(int R, int G, int B, float duration) {
        transitionColor.set(R / 255.0f, G / 255.0f, B / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0).ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void drawMenu() {
        batch.draw(
                dumbirdLogo, middleX / 4 - dumbirdLogo.getRegionWidth() / (2 * 1.2f), centerScreen - 50,
                dumbirdLogo.getRegionWidth() / 1.2f, dumbirdLogo.getRegionHeight() / 1.2f
        );

        for (Button button : buttons) {
            button.draw(batch);
        }
    }

    private void drawGround() {
        batch.draw(ground, currentGround.getX(), currentGround.getY(), currentGround.getWidth(), currentGround.getHeight());
        batch.draw(ground, pastGround.getX(), pastGround.getY(), pastGround.getWidth(), pastGround.getHeight());
    }

    private void drawObstacle() {
        batch.draw(obstacleUp, obstacle1.getX() - 1, obstacle1.getY() + obstacle1.getHeight() - 14, 24, 14);
        batch.draw(obstacleDown, obstacle1.getX() - 1, obstacle1.getY() + obstacle1.getHeight() + 45, 24, 14);

        batch.draw(obstacleUp, obstacle2.getX() - 1, obstacle2.getY() + obstacle2.getHeight() - 14, 24, 14);
        batch.draw(obstacleDown, obstacle2.getX() - 1, obstacle2.getY() + obstacle2.getHeight() + 45, 24, 14);

        batch.draw(obstacleUp, obstacle3.getX() - 1, obstacle3.getY() + obstacle3.getHeight() - 14, 24, 14);
        batch.draw(obstacleDown, obstacle3.getX() - 1, obstacle3.getY() + obstacle3.getHeight() + 45, 24, 14);
    }

    private void drawBarre() {
        batch.draw(barre, obstacle1.getX(), obstacle1.getY(), obstacle1.getWidth(), obstacle1.getHeight());
        batch.draw(barre, obstacle1.getX(), obstacle1.getY() + obstacle1.getHeight() + 45, obstacle1.getWidth(), centerScreen + 66 - (obstacle1.getHeight() + 45));

        batch.draw(barre, obstacle2.getX(), obstacle2.getY(), obstacle2.getWidth(), obstacle2.getHeight());
        batch.draw(barre, obstacle2.getX(), obstacle2.getY() + obstacle2.getHeight() + 45, obstacle2.getWidth(), centerScreen + 66 - (obstacle2.getHeight() + 45));

        batch.draw(barre, obstacle3.getX(), obstacle3.getY(), obstacle3.getWidth(), obstacle3.getHeight());
        batch.draw(barre, obstacle3.getX(), obstacle3.getY() + obstacle3.getHeight() + 45, obstacle3.getWidth(), centerScreen + 66 - (obstacle3.getHeight() + 45));
    }

    private void drawdumbirdMid(float runTime) {
        batch.draw(dumbirdAnimation.getKeyFrame(runTime), 59, dumbird.getY() - 15, dumbird.getWidth() / 2.0f, dumbird.getHeight() / 2.0f, dumbird.getWidth(), dumbird.getHeight(), 1, 1, dumbird.getRotation());
    }

    private void drawdumbird(float runTime) {
        if (dumbird.isNotFlap()) {
            batch.draw(dumbirdMid, dumbird.getX(), dumbird.getY(), dumbird.getWidth() / 2.0f, dumbird.getHeight() / 2.0f, dumbird.getWidth(), dumbird.getHeight(), 1, 1, dumbird.getRotation());
        } else {
            batch.draw(dumbirdAnimation.getKeyFrame(runTime), dumbird.getX(), dumbird.getY(), dumbird.getWidth() / 2.0f, dumbird.getHeight() / 2.0f, dumbird.getWidth(), dumbird.getHeight(), 1, 1, dumbird.getRotation());
        }
    }

    private void drawScore() {
        // Convert integer into String
        String score = updater.getScore() + "";

        AssetLoader.shadow.draw(batch, score, middleX / 4 - (3 * score.length()), centerScreen - 83);
        AssetLoader.font.draw(batch, score, middleX / 4 - (3 * score.length()), centerScreen - 83);
    }

    private void drawHighScore() {
        batch.draw(highscore, 22, centerScreen - 50, 96, 14);
    }

    private void drawReady() {
        batch.draw(ready, middleX / 4 - ready.getRegionWidth(), centerScreen / 2, ready.getRegionWidth() * 2, ready.getRegionHeight() * 2);
    }

    private void drawOver() {
        batch.draw(over, 24, centerScreen - 50, 92, 14);
    }

    private void drawScoreboard() {
        batch.draw(scoreBoard, 22, centerScreen - 30, 97, 37);

        String currentScoreString = "" + updater.getScore();
        shadow.draw(batch, currentScoreString, 41 - (2 * currentScoreString.length()), centerScreen - 13);
        font.draw(batch, currentScoreString, 41 - (2 * currentScoreString.length()), centerScreen - 13);

        String highScoreString = "" + AssetLoader.getHighScore();
        shadow.draw(batch, highScoreString, 87 - (2 * highScoreString.length()), centerScreen - 13);
        font.draw(batch, highScoreString, 87 - (2 * highScoreString.length()), centerScreen - 13);

        for (Button button : buttons) {
            button.draw(batch);
        }
    }

    private void drawBackgroundColor() {
        shapeRenderer.begin(ShapeType.Filled);

        // Dessiner le ciel
        shapeRenderer.setColor(73 / 255.0f, 186 / 255.0f, 234 / 255.0f, 1);
        shapeRenderer.rect(0, 0, middleX / 2, centerScreen + 66);

        // Dessiner la terre
        shapeRenderer.setColor(222 / 255.0f, 219 / 255.0f, 154 / 255.0f, 1);
        shapeRenderer.rect(0, centerScreen + 77, 136, 52);

        shapeRenderer.end();
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void render(float delta, float runTime) {
        // Clarifier la scène avec une couleur (noire)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        music.play();
        drawBackgroundColor();

        batch.begin();

        batch.draw(bg, 0, centerScreen + 23, bg.getRegionWidth(), bg.getRegionHeight());
        drawBarre();
        drawObstacle();

        if (updater.isMenu()) {
            buttons.clear();
            buttons.add(((InputHandler) Gdx.input.getInputProcessor()).getPlayButton());
            buttons.add(((InputHandler) Gdx.input.getInputProcessor()).getSoundButton());
            buttons.add(((InputHandler) Gdx.input.getInputProcessor()).getMusicButton());
            drawdumbirdMid(runTime);
            drawMenu();
        } else if (updater.isReady()) {
            drawdumbird(runTime);
            drawReady();
        } else if (updater.isRunning()) {
            drawdumbird(runTime);
            drawScore();
        } else if (updater.isOver()) {
            buttons.clear();
            buttons.add(((InputHandler) Gdx.input.getInputProcessor()).getRetryButton());
            buttons.add(((InputHandler) Gdx.input.getInputProcessor()).getHomeButton());
            drawScoreboard();
            drawdumbird(runTime);
            drawOver();
        } else if (updater.isHighScore()) {
            drawScoreboard();
            drawdumbird(runTime);
            drawHighScore();
        }

        drawGround();

        batch.end();
        drawTransition(delta);
    }
}
