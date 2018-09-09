package net.lustenauer.blockbunny2.screens.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import net.lustenauer.blockbunny2.box2d.FixtureNames;

public class GameContactListener implements ContactListener {

    /* CONSTANTS */

    /* ATTRIBUTES */
    private int numFootContacts;
    private Array<Body> bodiesToRemove;

    /* CONSTRUCTORS */
    public GameContactListener() {

        init();
    }

    /* INIT */
    private void init() {
        bodiesToRemove = new Array<Body>();
    }

    /* PUBLIC METHODS */
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        if (fa.getUserData() != null && fa.getUserData() == FixtureNames.PLAYER_FOOT) {
            numFootContacts++;
        }
        if (fb.getUserData() != null && fb.getUserData() == FixtureNames.PLAYER_FOOT) {
            numFootContacts++;
        }

        if (fa.getUserData() != null && fa.getUserData() == FixtureNames.CRYSTAL) {
            bodiesToRemove.add(fa.getBody());
        }
        if (fb.getUserData() != null && fb.getUserData() == FixtureNames.CRYSTAL) {
            bodiesToRemove.add(fb.getBody());
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;

        if (fa.getUserData() != null && fa.getUserData() == FixtureNames.PLAYER_FOOT) {
            numFootContacts--;
        }
        if (fb.getUserData() != null && fb.getUserData() == FixtureNames.PLAYER_FOOT) {
            numFootContacts--;
        }


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerOnGround() {
        return numFootContacts > 0;
    }

    public Array<Body> getBodiesToRemove() {
        return bodiesToRemove;
    }

    /* PRIVATE METHODS */


}
