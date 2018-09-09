package net.lustenauer.blockbunny2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.lustenauer.blockbunny2.BlockBunny2Game;
import net.lustenauer.blockbunny2.config.GameConfig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = GameConfig.GAME_TITLE;
		config.width = GameConfig.GAME_WIDTH;
		config.height = GameConfig.GAME_HEIGHT;

		new LwjglApplication(new BlockBunny2Game(), config);
	}
}
