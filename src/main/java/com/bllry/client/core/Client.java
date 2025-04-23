package com.bllry.client;

import com.bllry.client.core.events.EventManager;
import com.bllry.client.core.modules.ModuleManager;
import com.bllry.client.gui.clickgui.ClickGUI;

/**
 * Main client class that serves as the core of the Minecraft client
 */
public class Client {
    // Singleton instance
    private static Client instance;

    // Client info
    private final String name = "BllryClient";
    private final String version = "0.1.0";

    // Client managers
    private EventManager eventManager;
    private ModuleManager moduleManager;
    private ClickGUI clickGUI;

    /**
     * Private constructor - use getInstance() to get the instance
     */
    private Client() {
        // Initialize managers
        this.eventManager = new EventManager();
        this.moduleManager = new ModuleManager();
        this.clickGUI = new ClickGUI();

        System.out.println("Created " + name + " v" + version);
    }

    /**
     * Get the client instance (singleton)
     * @return Client instance
     */
    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    /**
     * Called when the client starts up
     */
    public void startClient() {
        // Initialize modules
        moduleManager.initialize();

        // Register managers to receive events
        eventManager.register(moduleManager);

        // Initialize the ClickGUI
        clickGUI.initialize();

        System.out.println(name + " started successfully!");
    }

    /**
     * Called when the client is shut down
     */
    public void shutdown() {
        // Clean up resources, save configs, etc.
        System.out.println(name + " shutting down...");
    }

    /**
     * Toggle the ClickGUI visibility
     */
    public void toggleClickGUI() {
        clickGUI.toggle();
    }

    // Getters

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ClickGUI getClickGUI() {
        return clickGUI;
    }
}