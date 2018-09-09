package net.lustenauer.blockbunny2.entitys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.lustenauer.blockbunny2.box2d.FixtureNames;
import net.lustenauer.gdx.util.entity.Box2DEntity;

import static net.lustenauer.blockbunny2.box2d.Box2DVariables.*;

public class Player extends Box2DEntity {

    /* CONSTANTS */
    private Fixture playerFixture;
    private Fixture footFixture;

    /* ATTRIBUTES */

    /* CONSTRUCTORS */
    public Player(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height);
    }

    /* PUBLIC METHODS */
    public void switchFixtureFilter() {
        Filter filter = playerFixture.getFilterData();
        short bits = filter.maskBits;

        // switch to next color
        if (isBitRed()) {
            bits &= ~BIT_RED;  // <-- remove red bit
            bits |= BIT_GREEN;
        } else if (isBitGreen()) {
            bits &= ~BIT_GREEN;  // <-- remove green bit
            bits |= BIT_BLUE;
        } else if (isBitBlue()) {
            bits &= ~BIT_BLUE;  // <-- remove blue bit
            bits |= BIT_RED;
        }

        // set new mask bits
        filter.maskBits = bits;
        playerFixture.setFilterData(filter);

        // set new mask bits for foot
        filter = footFixture.getFilterData();
        bits &= ~BIT_CRYSTAL; // <-- remove crystal bit
        filter.maskBits = bits;
        footFixture.setFilterData(filter);
    }

    public short getPlayerMaskBits() {
        return playerFixture.getFilterData().maskBits;
    }

    public boolean isBitRed() {
        return (getPlayerMaskBits() & BIT_RED) != 0;
    }

    public boolean isBitGreen() {
        return (getPlayerMaskBits() & BIT_GREEN) != 0;
    }

    public boolean isBitBlue() {
        return (getPlayerMaskBits() & BIT_BLUE) != 0;
    }


    /* PRIVATE METHODS */
    @Override
    protected void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((getX()) / PPM, getY() / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearVelocity.set(1f, 0);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(13 / PPM, 13 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_RED | BIT_GREEN | BIT_BLUE | BIT_CRYSTAL;

        playerFixture = body.createFixture(fixtureDef);
        playerFixture.setUserData(FixtureNames.PLAYER);

        // create foot sensor
        shape.setAsBox(13f / PPM, 2f / PPM, new Vector2(0, -13 / PPM), 0);

        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_RED | BIT_GREEN | BIT_BLUE;
        fixtureDef.isSensor = true;

        footFixture = body.createFixture(fixtureDef);
        footFixture.setUserData(FixtureNames.PLAYER_FOOT);

        shape.dispose();
    }

}
