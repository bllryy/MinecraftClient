package me.bllry.client.features.modules.player;

import com.google.common.eventbus.Subscribe;
import me.bllry.client.features.modules.Module;
import net.minecraft.world.LightType;

public class Fullbright extends Module {

    private float originalGamma;

    public Fullbright {
        super("Fullbright", "setting the gamma to 0", Module.Category.PLAYER, true, false, false);
    }
    @Override
    public void onEnable() {
        originalGamma = minecraft.getInstance().options.gamma.get();
        minecraft.getInstance().options.gamma.set(1000.0f);
    }

    @Override
    public void onDisable() { // restore the oldervlaue
        Minecraft.getInstance().options.gamma.set(originalGamma);
    }
    public int getLuminance(LightType type) {
        return 15;
    }
}
