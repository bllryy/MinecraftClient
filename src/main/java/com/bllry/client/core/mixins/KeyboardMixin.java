package com.bllry.client.mixins;

import com.bllry.client.Client;
import com.bllry.client.core.events.KeyEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to hook into Minecraft's keyboard handling
 */
@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Shadow @Final private MinecraftClient client;

    /**
     * Inject at the head of the onKey method
     * This captures all keyboard input
     */
    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        // Make sure this is the right window
        if (window == this.client.getWindow().getHandle()) {
            // Create and post key events
            if (action == 1) { // GLFW_PRESS
                // Key pressed
                KeyEvent event = new KeyEvent(key, true);
                Client.getInstance().getEventManager().post(event);
            } else if (action == 0) { // GLFW_RELEASE
                // Key released
                KeyEvent event = new KeyEvent(key, false);
                Client.getInstance().getEventManager().post(event);
            }
        }
    }
}