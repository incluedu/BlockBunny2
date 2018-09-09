package net.lustenauer.blockbunny2.screens.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.lustenauer.blockbunny2.assets.AssetDescriptors;
import net.lustenauer.blockbunny2.config.GameConfig;
import net.lustenauer.blockbunny2.screens.game.GameScreen;
import net.lustenauer.gdx.util.GdxUtils;
import net.lustenauer.gdx.util.game.GameBase;

public class LoadingScreen extends ScreenAdapter {
    /* CONSTANTS */
    public static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2;
    public static final float PROGRESS_BAR_HEIGHT = 60f;

    /* ATTRIBUTES */
    private final GameBase game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;

    private boolean changeScreen;

    /* CONSTRUCTORS */
    public LoadingScreen(GameBase game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }

    /* PUBLIC METHODS */

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.BUNNY);
        assetManager.load(AssetDescriptors.CRYSTAL);
        assetManager.load(AssetDescriptors.HUD);
    }

    @Override
    public void render(float delta) {
        update(delta);


        GdxUtils.clearScreen();
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if (changeScreen) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }

    /* PRIVATE METHODS */
    private void update(float delta) {
        progress = assetManager.getProgress();

        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        float progressBarX = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2;

        renderer.rect(
                progressBarX, progressBarY,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT
        );
    }

}
