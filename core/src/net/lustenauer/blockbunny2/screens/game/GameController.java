package net.lustenauer.blockbunny2.screens.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import net.lustenauer.blockbunny2.assets.AssetPaths;
import net.lustenauer.blockbunny2.box2d.FixtureNames;
import net.lustenauer.blockbunny2.common.GameManager;
import net.lustenauer.blockbunny2.common.GameInput;
import net.lustenauer.blockbunny2.config.GameConfig;
import net.lustenauer.blockbunny2.entitys.Crystal;
import net.lustenauer.blockbunny2.entitys.Player;
import net.lustenauer.blockbunny2.tiled.LayerNames;

import static net.lustenauer.blockbunny2.box2d.Box2DVariables.*;

public class GameController implements Disposable {
    /* CONSTANTS */

    /* ATTRIBUTES */
    private World world;

    private TiledMap tiledMap;
    private float tileSize;

    private Player player;
    private Array<Crystal> crystals;
    private GameContactListener contactListener;


    /* CONSTRUCTORS */
    public GameController() {
        init();
    }

    /* INIT */
    private void init() {
        contactListener = new GameContactListener();

        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(contactListener);

        tiledMap = new TmxMapLoader().load(AssetPaths.MAP_LEVEL1);
        createTiles();

        createPlayer();

        createCrystals();
    }

    /* PUBLIC METHODS */
    public void update(float delta) {
        handleInput();

        GameManager.INSTANCE.updateDisplayScore(delta);

        world.step(GameConfig.STEP, 1, 1);

        // remove crystals important to to it after world update!
        for (Body body : contactListener.getBodiesToRemove()) {
            crystals.removeValue((Crystal) body.getUserData(), true);
            world.destroyBody(body);
            GameManager.INSTANCE.addScore(1);
        }

        contactListener.getBodiesToRemove().clear();


        player.update(delta);

        for (Crystal crystal : crystals) {
            crystal.update(delta);
        }
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Crystal> getCrystals() {
        return crystals;
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    /* PRIVATE METHODS */
    public void handleInput() {

        // player jump
        if (GameInput.isPressed(GameInput.BUTTON1)) {
            if (contactListener.isPlayerOnGround()) {
                player.getBody().applyForceToCenter(0, 250, true);
            }
        }

        // switch block color
        if (GameInput.isPressed(GameInput.BUTTON2)) {
            player.switchFixtureFilter();
        }
    }

    private void createTiles() {
        tileSize = tiledMap.getProperties().get("tilewidth", Integer.class);

        TiledMapTileLayer layer;

        layer = (TiledMapTileLayer) tiledMap.getLayers().get(LayerNames.RED);
        createTiledLayer(layer, BIT_RED);

        layer = (TiledMapTileLayer) tiledMap.getLayers().get(LayerNames.GREEN);
        createTiledLayer(layer, BIT_GREEN);

        layer = (TiledMapTileLayer) tiledMap.getLayers().get(LayerNames.BLUE);
        createTiledLayer(layer, BIT_BLUE);


    }

    private void createTiledLayer(TiledMapTileLayer layer, short bits) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                Cell cell = layer.getCell(col, row);

                if (cell == null) continue;
                if (cell.getTile() == null) continue;

                bodyDef.position.set(
                        (col + 0.5f) * tileSize / PPM,
                        (row + 0.5f) * tileSize / PPM)
                ;
                bodyDef.type = BodyDef.BodyType.StaticBody;
                Body body = world.createBody(bodyDef);


                ChainShape shape = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
                v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
                v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);
                shape.createChain(v);

                fixtureDef.shape = shape;
                fixtureDef.friction = 0.0f;
                fixtureDef.filter.categoryBits = bits;
                fixtureDef.filter.maskBits = BIT_PLAYER;

                Fixture fixture = body.createFixture(fixtureDef);
                fixture.setUserData(FixtureNames.GROUND);

                shape.dispose();

            }
        }
    }

    private void createPlayer() {
        player = new Player(
                world,
                GameConfig.PLAYER_START_X, GameConfig.PLAYER_START_Y,
                GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT
        );

    }

    private void createCrystals() {
        crystals = new Array<Crystal>();

        MapLayer layer = tiledMap.getLayers().get(LayerNames.CRYSTALS);

        for (MapObject mapObject : layer.getObjects()) {

            float x = (float) mapObject.getProperties().get("x", Float.class);
            float y = (float) mapObject.getProperties().get("y", Float.class);

            crystals.add(new Crystal(world, x, y, 16f, 16f));
        }

    }

}
