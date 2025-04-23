package com.bllry.client.core.modules;

import com.bllry.client.Client;

/**
 * Base class for all client modules
 */
public abstract class Module {
    private String name;
    private String description;
    private int keyBind;
    private boolean enabled;
    private Category category;

    /**
     * Create a new module
     * @param name Module name
     * @param description Module description
     * @param category Module category
     */
    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.keyBind = 0;
        this.enabled = false;
    }

    /**
     * Toggle the module on or off
     */
    public void toggle() {
        this.enabled = !this.enabled;

        if (this.enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /**
     * Called when the module is enabled
     */
    public void onEnable() {
        // Register event listeners
        Client.getInstance().getEventManager().register(this);
    }

    /**
     * Called when the module is disabled
     */
    public void onDisable() {
        // Unregister event listeners
        Client.getInstance().getEventManager().unregister(this);
    }

    /**
     * Get the module name
     * @return Module name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the module description
     * @return Module description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the module key bind
     * @return Key code
     */
    public int getKeyBind() {
        return keyBind;
    }

    /**
     * Set the module key bind
     * @param keyBind Key code
     */
    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    /**
     * Check if the module is enabled
     * @return True if enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set whether the module is enabled
     * @param enabled True to enable
     */
    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            toggle();
        }
    }

    /**
     * Get the module category
     * @return Module category
     */
    public Category getCategory() {
        return category;
    }
}