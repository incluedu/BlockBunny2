package net.lustenauer.blockbunny2.entitys;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import net.lustenauer.blockbunny2.box2d.FixtureNames;
import net.lustenauer.gdx.util.entity.Box2DEntity;

import static net.lustenauer.blockbunny2.box2d.Box2DVariables.*;

public class Crystal extends Box2DEntity {

    /* CONSTANTS */

    /* ATTRIBUTES */

    /* CONSTRUCTORS */
    public Crystal(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height);
    }

    /* PUBLIC METHODS */

    /* PRIVATE METHODS */
    @Override
    protected void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX() / ppm, getY() / ppm);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6.5f / PPM, 6.5f / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = BIT_CRYSTAL;
        fixtureDef.filter.maskBits = BIT_PLAYER;

        body.createFixture(fixtureDef).setUserData(FixtureNames.CRYSTAL);

    }
}
