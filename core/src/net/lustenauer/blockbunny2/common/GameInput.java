package net.lustenauer.blockbunny2.common;

public class GameInput {

    public static boolean[] keys;
    public static boolean[] prevKeys;

    public static final int NUM_KEYS = 2;
    public static final int BUTTON1 = 0;
    public static final int BUTTON2 = 1;

    static {
        keys = new boolean[NUM_KEYS];
        prevKeys = new boolean[NUM_KEYS];
    }

    public static void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            prevKeys[i] = keys[i];
        }
    }

    public static void setKey(int index, boolean value) {
        keys[index] = value;
    }

    public static boolean isDown(int index) {
        return keys[index];
    }

    public static boolean isPressed(int index) {
        return keys[index] && !prevKeys[index];
    }
}
