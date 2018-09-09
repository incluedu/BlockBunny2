package net.lustenauer.blockbunny2.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

public final class AssetDescriptors {
    /* CONSTANTS */
    public static final AssetDescriptor<Texture> BGS =
            new AssetDescriptor<Texture>(AssetPaths.BGS, Texture.class);

    public static final AssetDescriptor<Texture> BUNNY =
            new AssetDescriptor<Texture>(AssetPaths.BUNNY, Texture.class);

    public static final AssetDescriptor<Texture> CRYSTAL =
            new AssetDescriptor<Texture>(AssetPaths.CRYSTAL, Texture.class);

    public static final AssetDescriptor<Texture> HUD =
            new AssetDescriptor<Texture>(AssetPaths.HUD, Texture.class);

    public static final AssetDescriptor<Texture> MENU =
            new AssetDescriptor<Texture>(AssetPaths.MENU, Texture.class);

    public static final AssetDescriptor<Texture> SPIKES =
            new AssetDescriptor<Texture>(AssetPaths.SPIKES, Texture.class);


    /* PRIVATE CONSTRUCTOR */
    private AssetDescriptors() {
    }
}
