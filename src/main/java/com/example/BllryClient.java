package com.example;

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
	private KeyBinding guiKeyBinding;

	// Module states (can be expanded later)
	private boolean speedModuleEnabled = false;
	private boolean flyModuleEnabled = false;

	// Initialize the client
	public void init() {
		System.out.println("Setting up Bllry Client components...");

		// Register the key binding for GUI
		guiKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.bllryclient.open_gui",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_G, // G key by default
				"category.bllryclient"
		));

		// Register client tick event to check for key presses
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			// Check if GUI key is pressed
			if (guiKeyBinding.wasPressed()) {
				MinecraftClient.getInstance().setScreen(new BllryClientGui());
			}

			// Handle module logic when enabled
			if (speedModuleEnabled) {
				handleSpeedModule();
			}

			if (flyModuleEnabled) {
				handleFlyModule();
			}
		});
	}

	// Toggle a module by name
	public void toggleModule(String moduleName) {
		switch (moduleName.toLowerCase()) {
			case "speed":
				speedModuleEnabled = !speedModuleEnabled;
				System.out.println("Speed module: " + (speedModuleEnabled ? "ENABLED" : "DISABLED"));
				break;
			case "fly":
				flyModuleEnabled = !flyModuleEnabled;
				System.out.println("Fly module: " + (flyModuleEnabled ? "ENABLED" : "DISABLED"));
				break;
			default:
				System.out.println("Unknown module: " + moduleName);
				break;
		}
	}

	// Get module state
	public boolean isModuleEnabled(String moduleName) {
		switch (moduleName.toLowerCase()) {
			case "speed":
				return speedModuleEnabled;
			case "fly":
				return flyModuleEnabled;
			default:
				return false;
		}
	}

	// Module implementations (placeholder)
	private void handleSpeedModule() {
		// Example implementation - increase player movement speed
		// This is just a placeholder - real implementation would modify player movement
		if (MinecraftClient.getInstance().player != null) {
			// Implementation would go here
		}
	}

	private void handleFlyModule() {
		// Example implementation - enable player flight
		// This is just a placeholder - real implementation would modify player abilities
		if (MinecraftClient.getInstance().player != null) {
			// Implementation would go here
		}
	}
}