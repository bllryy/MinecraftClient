package me.bllry.client.features.modules.render;

import com.google.common.eventbus.Subscribe;
import me.bllry.client.event.WorldLightEvent;
import me.bllry.client.features.modules.Module;
import me.bllry.client.features.settings.Setting;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.LightType;

public class Fullbright extends Module {
    // Original gamma value to restore when disabling
    private double originalGamma;

    // Settings
    private final Setting<Mode> mode = register(new Setting<>("Mode", Mode.Gamma));
    private final Setting<LightType> lightType = register(new Setting<>("LightType", LightType.BLOCK, v -> mode.getValue() == Mode.Luminance));
    private final Setting<Integer> minimumLightLevel = register(new Setting<>("MinimumLight", 8, 0, 15, v -> mode.getValue() == Mode.Luminance));

    // For potion mode tracking
    private boolean hadNightVision = false;
    private int originalDuration = 0;

    public Fullbright() {
        super("Fullbright", "Makes everything bright by maximizing gamma and light levels", Category.RENDER, true, false, false);
    }

    @Override
    public void onEnable() {
        originalGamma = mc.options.getGamma().getValue();

        switch (mode.getValue()) {
            case Gamma:
                mc.options.getGamma().setValue(1000.0);
                break;
            case Potion:
                if (mc.player != null) {
                    // Save night vision state before applying our own
                    StatusEffectInstance nightVision = mc.player.getStatusEffect(StatusEffects.NIGHT_VISION);
                    if (nightVision != null) {
                        hadNightVision = true;
                        originalDuration = nightVision.getDuration();
                    }
                }
                break;
            case Luminance:
                // Reload renderer if available
                if (mc.worldRenderer != null) {
                    mc.worldRenderer.reload();
                }
                break;
        }
    }

    @Override
    public void onDisable() {
        switch (mode.getValue()) {
            case Gamma:
                mc.options.getGamma().setValue(originalGamma);
                break;
            case Potion:
                disableNightVision();
                break;
            case Luminance:
                // Reload renderer to reset lighting
                if (mc.worldRenderer != null) {
                    mc.worldRenderer.reload();
                }
                break;
        }
    }

    @Override
    public void onUpdate() {
        if (mode.getValue() == Mode.Potion && mc.player != null) {
            // Apply night vision effect continuously
            StatusEffectInstance nightVision = mc.player.getStatusEffect(StatusEffects.NIGHT_VISION);
            if (nightVision == null || nightVision.getDuration() < 210) {
                mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 420, 0, false, false));
            }
        }
    }

    private void disableNightVision() {
        if (mc.player == null) return;

        if (hadNightVision && originalDuration > 0) {
            // Restore original night vision if player had it before
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, originalDuration, 0, false, false));
            hadNightVision = false;
            originalDuration = 0;
        } else {
            // Remove the effect added
            mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }

    @Subscribe
    public void onWorldLight(WorldLightEvent event) { // need the worldlightevent event or roll own
        if (!this.isEnabled()) return;

        // If using Luminance mode and event light type matches our setting
        if (mode.getValue() == Mode.Luminance && event.getLightType() == lightType.getValue()) {
            int currentLevel = event.getLightLevel();
            int minLevel = minimumLightLevel.getValue();

            // Only increase light level if it's below our minimum
            if (currentLevel < minLevel) {
                event.setLightLevel(minLevel);
            }
        } else if (mode.getValue() == Mode.Gamma) {
            // For gamma mode, maximize light level
            event.setLightLevel(15);
        }
    }

    // Add this method for compatibility with rendering systems
    public int getLuminance(LightType type) {
        if (!isEnabled() || mode.getValue() != Mode.Luminance || type != lightType.getValue()) {
            return 0;
        }
        return minimumLightLevel.getValue();
    }

    // Add this method for compatibility with gamma systems
    public boolean shouldUseGamma() {
        return isEnabled() && mode.getValue() == Mode.Gamma;
    }

    public enum Mode {
        Gamma,
        Potion,
        Luminance
    }
}