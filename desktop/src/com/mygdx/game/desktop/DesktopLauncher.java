package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BubbleTrouble;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BubbleTrouble.WIDTH;
		config.height = BubbleTrouble.HEIGHT;
		config.title = BubbleTrouble.TITLE;
		new LwjglApplication(new BubbleTrouble(), config);
	}
}
