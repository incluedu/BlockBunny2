package net.lustenauer.blockbunny2.common;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {
    /* CONSTANTS */

    /* ATTRIBUTES */

    /* CONSTRUCTORS */
    public GameInputProcessor() {

        init();
    }

    /* INIT */
    private void init() {
    }

    /* PUBLIC METHODS */
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Keys.Y){
            GameInput.setKey(GameInput.BUTTON1,true);
        }
        if (keycode == Keys.X){
            GameInput.setKey(GameInput.BUTTON2,true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Keys.Y){
            GameInput.setKey(GameInput.BUTTON1,false);
        }
        if (keycode == Keys.X){
            GameInput.setKey(GameInput.BUTTON2,false);
        }
        return true;
    }

    /* PRIVATE METHODS */

}
