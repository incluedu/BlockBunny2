package net.lustenauer.blockbunny2.screens.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import net.lustenauer.blockbunny2.assets.AssetDescriptors;
import net.lustenauer.blockbunny2.config.GameConfig;
import net.lustenauer.blockbunny2.entitys.Crystal;
import net.lustenauer.blockbunny2.entitys.Player;

import static net.lustenauer.blockbunny2.box2d.Box2DVariables.PPM;

public class GameRenderer implements Disposable {
    /* CONSTANTS */

    /* ATTRIBUTES */
    private final GameController controller;
    private final SpriteBatch batch;
    private final AssetManager assetManager;

    private boolean debug = false;

    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private OrthographicCamera b2dCamera;

    private Box2DDebugRenderer debugRenderer;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private TextureRegion[] blocks;


    /* CONSTRUCTORS */
    public GameRenderer(GameController controller, SpriteBatch batch, AssetManager assetManager) {
        this.controller = controller;
        this.batch = batch;
        this.assetManager = assetManager;
        init();
    }

    /* INIT */
    private void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConfig.VIEW_WIDTH, GameConfig.VIEW_HEIGHT);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, GameConfig.VIEW_WIDTH, GameConfig.VIEW_HEIGHT);

        b2dCamera = new OrthographicCamera();
        b2dCamera.setToOrtho(
                false,
                GameConfig.VIEW_WIDTH / PPM,
                GameConfig.VIEW_HEIGHT / PPM
        );

        debugRenderer = new Box2DDebugRenderer();

        tiledMapRenderer = new OrthogonalTiledMapRenderer(controller.getTiledMap());

        initPlayer();
        initCrystals();
        initHud();
    }


    /* PUBLIC METHODS */
    public void render() {
        // set camera to follow player
        camera.position.set(
                controller.getPlayer().getX() + GameConfig.VIEW_WIDTH / 4f,
                GameConfig.VIEW_HEIGHT / 2f,
                0
        );
        camera.update();

        renderGame();
        renderHud();
        if (debug) renderDebug();
    }

    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        debugRenderer.dispose();
    }

    /* PRIVATE METHODS */
    private void drawTiledMap() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    private void initPlayer() {
        TextureRegion[] playerTextures = TextureRegion.split(
                assetManager.get(AssetDescriptors.BUNNY),
                GameConfig.BLOCK_SIZE, GameConfig.BLOCK_SIZE
        )[0];
        controller.getPlayer().initAnimation(playerTextures, 1f / 16f);
    }

    private void initCrystals() {
        TextureRegion[] crystalTextures = TextureRegion.split(
                assetManager.get(AssetDescriptors.CRYSTAL),
                16, 16
        )[0];

        Array<Crystal> crystals = controller.getCrystals();
        for (Crystal crystal : crystals) {
            crystal.initAnimation(crystalTextures, 1f / 16F);
        }
    }

    private void initHud() {
        Texture texture = assetManager.get(AssetDescriptors.HUD);

        blocks = new TextureRegion[3];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new TextureRegion(texture, 32 + i * 16, 0, 16, 16);
        }
    }

    private void renderHud() {
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        drawHud();
        batch.end();
    }

    private void drawHud() {
        Player player = controller.getPlayer();

        // active blocks
        if (player.isBitRed()) {
            batch.draw(blocks[0], 40, 200);
        } else if (player.isBitGreen()) {
            batch.draw(blocks[1], 40, 200);
        } else if (player.isBitBlue()) {
            batch.draw(blocks[2], 40, 200);
        }
    }

    private void renderGame() {
        // draw map
        drawTiledMap();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // draw player
        controller.getPlayer().draw(batch);

        // draw crystals
        for (Crystal crystal : controller.getCrystals()) {
            crystal.draw(batch);
        }

        batch.end();
    }

    private void renderDebug() {
        debugRenderer.render(controller.getWorld(), b2dCamera.combined);
    }

}
