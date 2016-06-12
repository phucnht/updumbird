package com.ezteam.updumbird;

import com.badlogic.gdx.Game;
import com.ezteam.screens.SplashScreen;
import com.ezteam.utils.AssetLoader;

public class UpDumbirdMain extends Game {
	@Override
	public void create () {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
