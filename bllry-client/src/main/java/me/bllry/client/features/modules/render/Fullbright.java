package me.bllry.client.features.modules.render;

import com.google.common.eventbus.Subscribe;
import me.bllry.client.features.modules.Module;
// import net.minecraft.world.LightType;
import net.minecraft.client.MinecraftClient;

public class Fullbright extends Module {

    private double originalGamma;

    public Fullbright() {
        super("Fullbright", "setting the gamma to 0", Category.RENDER, true, false, false);
    }


    @Override
    public void onEnable() {
        originalGamma = mc.getInstance().options.getGamma().getValue();
        //originalGamma = (float) mc.options.getGamma().getValue();
        mc.getInstance().options.getGamma().setValue(1000.0);    }

    @Override
    public void onDisable() { // restore the oldervlaue
        mc.getInstance().options.getGamma().setValue(originalGamma);
    }

    // optional
    //public int getLuminance(LightType type) {
    //    return 15;
    //}
}
