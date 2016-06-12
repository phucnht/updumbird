package com.ezteam.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by batty on 6/8/2016.
 */
public class AssetLoader {
    public static Texture texture, logoTexture;
    public static TextureRegion boss, teamLogo, dumbirdLogo, bg, ground,
            dumbirdMid, dumbirdBot, dumbirdTop, obstacleUp, obstacleDown, barre,
            playButton, playButtonPressed, retryButton, retryButtonPressed,
            homeButton, homeButtonPressed, pauseButton, pauseButtonPressed,
            musicButton, musicButtonPressed, soundButton, soundButtonPressed,
            ready, over, highscore, scoreBoard;
    public static Animation dumbirdAnimation;
    public static Sound dead, flap, coin, fall;
    public static Music music;
    public static BitmapFont font, shadow;
    private static Preferences prefs;

    public static void load() {
        loadTexture();
        flipTexture();
        loadSound();
        loadFont();
        loadPreferences();
    }

    public static void loadTexture() {
        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        teamLogo = new TextureRegion(logoTexture, 0, 0, 364, 312);

        texture = new Texture(Gdx.files.internal("texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        dumbirdLogo = new TextureRegion(texture, 0, 54, 136, 22);
        bg = new TextureRegion(texture, 0, 0, 136, 43);
        ground = new TextureRegion(texture, 0, 43, 156, 11);

        dumbirdTop = new TextureRegion(texture, 136, 0, 17, 12);
        dumbirdMid = new TextureRegion(texture, 153, 0, 17, 12);
        dumbirdBot = new TextureRegion(texture, 170, 0, 17, 12);

        boss = new TextureRegion(texture, 187, 0, 46, 54);

        TextureRegion[] dumbirds = {dumbirdBot, dumbirdMid, dumbirdTop};
        dumbirdAnimation = new Animation(0.06f, dumbirds);
        dumbirdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        obstacleUp = new TextureRegion(texture, 136, 12, 24, 14);
        obstacleDown = new TextureRegion(obstacleUp);

        barre = new TextureRegion(texture, 136, 26, 24, 3);

        playButton          = new TextureRegion(texture, 0, 76, 29, 16);
        retryButton         = new TextureRegion(texture, 0, 92, 29, 16);
        homeButton          = new TextureRegion(texture, 0, 108, 29, 16);
        pauseButton         = new TextureRegion(texture, 58, 108, 29, 16);
        musicButton         = new TextureRegion(texture, 136, 60, 29, 16);
        soundButton         = new TextureRegion(texture, 194, 60, 29, 16);
        playButtonPressed   = new TextureRegion(texture, 29, 76, 29, 16);
        retryButtonPressed  = new TextureRegion(texture, 29, 92, 29, 16);
        homeButtonPressed   = new TextureRegion(texture, 29, 108, 29, 16);
        pauseButtonPressed  = new TextureRegion(texture, 87, 108, 29, 16);
        musicButtonPressed  = new TextureRegion(texture, 165, 60, 29, 16);
        soundButtonPressed  = new TextureRegion(texture, 223, 60, 29, 16);

        ready = new TextureRegion(texture, 58, 76, 34, 7);
        over = new TextureRegion(texture, 58, 83, 46, 7);
        highscore = new TextureRegion(texture, 58, 90, 48, 7);

        scoreBoard = new TextureRegion(texture, 136, 76, 97, 37);
    }

    public static void loadSound() {
        // Sounds
        dead = Gdx.audio.newSound(Gdx.files.internal("sfx_dead.ogg"));
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_flap.ogg"));
        coin = Gdx.audio.newSound(Gdx.files.internal("sfx_score.ogg"));
        fall = Gdx.audio.newSound(Gdx.files.internal("sfx_fall.ogg"));

        music = Gdx.audio.newMusic(Gdx.files.internal("musicmain.mp3"));
        music.setLooping(true);
        music.setVolume(1.0f);
    }

    public static void loadFont() {
        // Fonts
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.getData().setScale(.5f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(.5f, -.25f);
    }

    public static void loadPreferences() {
        // Préférences
        prefs = Gdx.app.getPreferences("dumbird");

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

        prefs.clear();
    }

    public static void flipTexture() {
        ground.flip(false, true);
        bg.flip(false, true);
        dumbirdLogo.flip(false, true);
        dumbirdBot.flip(false, true);
        dumbirdMid.flip(false, true);
        dumbirdTop.flip(false, true);
        obstacleDown.flip(false, true);
        barre.flip(false, true);
        highscore.flip(false, true);
        scoreBoard.flip(false, true);
        ready.flip(false, true);
        over.flip(false, true);
        playButton.flip(false, true);
        playButtonPressed.flip(false, true);
        retryButton.flip(false, true);
        retryButtonPressed.flip(false, true);
        homeButton.flip(false, true);
        homeButtonPressed.flip(false, true);
        pauseButton.flip(false, true);
        pauseButtonPressed.flip(false, true);
        musicButton.flip(false, true);
        musicButtonPressed.flip(false, true);
        soundButton.flip(false, true);
        soundButtonPressed.flip(false, true);
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        texture.dispose();

        dead.dispose();
        flap.dispose();
        coin.dispose();
        fall.dispose();
        music.dispose();

        font.dispose();
        shadow.dispose();
    }
}
