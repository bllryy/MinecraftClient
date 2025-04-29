package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class BllryClient {

	// Singleton instance
	public static BllryClient INSTANCE;

	// Key binding for GUI
	public static KeyBinding GUI_KEY_BINDING;

	// Constructor
	public BllryClient() {
		INSTANCE = this;
	}

	// Initialization method
	public void init() {
		System.out.println("Initializing BllryClient...");

		// Register the key binding
		GUI_KEY_BINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.bllryclient.open_gui",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_G,
				"category.bllryclient"
		));

		// Register client tick event to check for key presses
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			// Check if GUI key is pressed
			if (GUI_KEY_BINDING.wasPressed()) {
				MinecraftClient.getInstance().setScreen(new BllryClientGui());
			}
		});
	}

	// Module toggle method (placeholder for now)
	public void toggleModule(String moduleName) {
		System.out.println("Toggled module: " + moduleName);
		// Module functionality will be implemented later
	}
}