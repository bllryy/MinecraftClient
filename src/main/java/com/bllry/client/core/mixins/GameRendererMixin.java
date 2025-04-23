package com.bllry.client.mixins;

import com.bllry.client.Client;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to hook into Minecraft's rendering
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    /**
     * Inject at the return point of the render method
     * This allows us to render our GUI on top of everything else
     */
    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        // Check if the ClickGUI is visible
        if (Client.getInstance().getClickGUI().isVisible()) {
            // Render the ClickGUI
            Client.getInstance().getClickGUI().render();
        }
    }
}