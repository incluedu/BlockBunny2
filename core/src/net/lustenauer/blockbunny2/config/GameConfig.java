package net.lustenauer.blockbunny2.config;

public final class GameConfig {

    /* CONSTANTS */
    public static final float STEP = 1 / 60f;

    public static final String GAME_TITLE = "BLOCK BUNNY 2 Version 0.0.0";
    public static final int GAME_WIDTH = 720;
    public static final int GAME_HEIGHT = 480;

    public static final float VIEW_WIDTH = 360;
    public static final float VIEW_HEIGHT = 240;

    public static final float HUD_WIDTH = 720;
    public static final float HUD_HEIGHT = 480;

    public static final int BLOCK_SIZE = 32; // pixels

    public static final float PLAYER_WIDTH = 32;
    public static final float PLAYER_HEIGHT = 32;
    public static final float PLAYER_START_X = 100;
    public static final float PLAYER_START_Y = 100;

    /* PRIVATE CONSTRUCTOR */
    private GameConfig() {
    }
}
