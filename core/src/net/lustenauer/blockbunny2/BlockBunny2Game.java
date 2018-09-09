package net.lustenauer.blockbunny2;

import net.lustenauer.blockbunny2.screens.loading.LoadingScreen;
import net.lustenauer.gdx.util.game.GameBase;

public class BlockBunny2Game extends GameBase {


    @Override
    public void postCreate() {
        setScreen(new LoadingScreen(this));
    }
}
