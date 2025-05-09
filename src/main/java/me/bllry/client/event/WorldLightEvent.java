package me.bllry.client.event;

import me.bllry.client.event.Event;
import net.minecraft.world.LightType;

public class WorldLightEvent extends Event {
    private LightType type;
    private int lightLevel;
    private boolean modified = false;

    public WorldLightEvent(LightType type, int originalLevel) {
        this.type = type;
        this.lightLevel = originalLevel;
    }

    public LightType getType() {
        return type;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(int level) {
        this.lightLevel = level;
        this.modified = true;
    }

    public boolean isModified() {
        return modified;
    }
}
