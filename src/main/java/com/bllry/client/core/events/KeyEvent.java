package com.bllry.client.core.events;

/**
 * Event for keyboard input
 */
public class KeyEvent extends Event {
    private final int keyCode;
    private final boolean pressed;

    /**
     * Create a new key event
     * @param keyCode The key code
     * @param pressed True if the key was pressed, false if released
     */
    public KeyEvent(int keyCode, boolean pressed) {
        super(Type.PRE);
        this.keyCode = keyCode;
        this.pressed = pressed;
    }

    /**
     * Get the key code
     * @return Key code
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * Check if the key was pressed
     * @return True if pressed, false if released
     */
    public boolean isPressed() {
        return pressed;
    }
}