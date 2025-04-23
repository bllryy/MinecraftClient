package com.bllry.client.gui.clickgui;

import com.bllry.client.Client;
import com.bllry.client.core.events.EventTarget;
import com.bllry.client.core.events.KeyEvent;
import com.bllry.client.core.modules.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI for selecting and configuring modules
 * This is a simplified version - you'll need to implement actual rendering
 */
public class ClickGUI {
    private boolean visible = false;

    // Panels (one for each category)
    private final List<Panel> panels = new ArrayList<>();

    /**
     * Initialize the ClickGUI
     */
    public void initialize() {
        // Register for events
        Client.getInstance().getEventManager().register(this);

        // Create panels for each category
        int x = 10;
        for (Category category : Category.values()) {
            panels.add(new Panel(category, x, 10, 120, 15));
            x += 130; // Add spacing between panels
        }
    }

    /**
     * Toggle the GUI visibility
     */
    public void toggle() {
        visible = !visible;
        System.out.println("ClickGUI " + (visible ? "opened" : "closed"));
    }

    /**
     * Check if the GUI is visible
     * @return True if visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Handle key events for the GUI
     * @param event Key event
     */
    @EventTarget
    public void onKey(KeyEvent event) {
        // Toggle GUI with the Right Shift key (default)
        if (event.getKeyCode() == 54 && event.isPressed()) { // 54 is right shift in LWJGL
            toggle();
        }

        // Handle Escape key to close GUI
        if (visible && event.getKeyCode() == 1 && event.isPressed()) { // 1 is escape in LWJGL
            toggle();
        }
    }

    /**
     * Handle mouse clicks
     * @param mouseX Mouse X coordinate
     * @param mouseY Mouse Y coordinate
     * @param button Mouse button (0 = left, 1 = right)
     * @return True if the click was handled
     */
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!visible) {
            return false;
        }

        boolean handled = false;

        // Handle clicks in each panel
        for (Panel panel : panels) {
            if (panel.mouseClicked(mouseX, mouseY, button)) {
                handled = true;
            }
        }

        return handled;
    }

    /**
     * Handle mouse releases
     * @param mouseX Mouse X coordinate
     * @param mouseY Mouse Y coordinate
     * @param button Mouse button (0 = left, 1 = right)
     */
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (!visible) {
            return false;
        }

        boolean handled = false;

        // Handle releases in each panel
        for (Panel panel : panels) {
            if (panel.mouseReleased(mouseX, mouseY, button)) {
                handled = true;
            }
        }

        return handled;
    }

    /**
     * Render the GUI
     * This is a placeholder method - you'll need to implement actual rendering
     */
    public void render() {
        if (!visible) {
            return;
        }

        // Render each panel
        for (Panel panel : panels) {
            panel.render();
        }
    }
}