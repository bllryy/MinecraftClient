package me.bllry.client.features.modules.client;

import me.bllry.client.bllry;
import me.bllry.client.event.impl.Render2DEvent;
import me.bllry.client.features.modules.Module;

public class HudModule extends Module {
    public HudModule() {
        super("Hud", "hud", Category.CLIENT, true, false, false);
    }

    @Override public void onRender2D(Render2DEvent event) {
        event.getContext().drawTextWithShadow(
                mc.textRenderer,
                bllry.NAME + " " + bllry.VERSION,
                2, 2,
                -1
        );
    }
}
