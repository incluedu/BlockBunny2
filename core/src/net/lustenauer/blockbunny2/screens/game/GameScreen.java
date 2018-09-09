package net.lustenauer.blockbunny2.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import net.lustenauer.blockbunny2.common.GameInput;
import net.lustenauer.blockbunny2.common.GameInputProcessor;
import net.lustenauer.gdx.util.GdxUtils;
import net.lustenauer.gdx.util.game.GameBase;

public class GameScreen extends ScreenAdapter {
    /* CONSTANTS */

    /* ATTRIBUTES */
    private final GameBase game;

    private GameController controller;
    private GameRenderer renderer;

    /* CONSTRUCTORS */
    public GameScreen(GameBase game) {
        this.game = game;
    }


    /* PUBLIC METHODS */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GameInputProcessor());

        controller = new GameController();
        renderer = new GameRenderer(controller, game.getBatch(), game.getAssetManager());
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen(Color.TEAL);

        controller.update(delta);
        renderer.render();

        GameInput.update();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        controller.dispose();
        renderer.dispose();
    }

    /* PRIVATE METHODS */

}
