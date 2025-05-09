
import me.bllry.client.event.events.Render3DEvent;
import me.bllry.client.event.events.UpdateEvent;
import me.bllry.client.features.modules.Module;
import me.bllry.client.features.settings.Setting;
import me.bllry.client.util.ColorUtil;
import me.bllry.client.util.RenderUtil;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Breadcrumbs extends Module { // TODO: add to the module list
    // Settings
    public Setting<Integer> red = register(new Setting<>("Red", 225, 0, 255));
    public Setting<Integer> green = register(new Setting<>("Green", 25, 0, 255));
    public Setting<Integer> blue = register(new Setting<>("Blue", 25, 0, 255));
    public Setting<Integer> alpha = register(new Setting<>("Alpha", 255, 0, 255));
    public Setting<Integer> maxSections = register(new Setting<>("MaxSections", 1000, 1, 5000));
    public Setting<Float> sectionLength = register(new Setting<>("SectionLength", 0.5f, 0.1f, 5.0f));

    // Trail data
    private final List<Section> sections = new ArrayList<>();
    private Section currentSection;
    private Object lastDimension;

    public Breadcrumbs() {
        super(Categories.Render, "Breadcrumbs", "Creates a trail that follows behind you", true, false, false);
    }

    @Override
    public void onEnable() {
        currentSection = new Section();
        currentSection.set1();

        // Assuming your client has a way to get dimension info - adjust as needed
        // If your client doesn't use dimensions, you can remove this logic
        try {
            lastDimension = mc.player.world.getDimension(); // Adjust according to your client's API
        } catch (Exception e) {
            lastDimension = null;
        }
    }

    @Override
    public void onDisable() {
        sections.clear();
        currentSection = null;
    }

    // Assume this is called every tick - adjust event handler as needed for your client
    public void onUpdate(UpdateEvent event) {
        if (mc.player == null || currentSection == null) return;

        // Check dimension change - remove if not applicable
        try {
            Object currentDimension = mc.player.world.getDimension(); // Adjust to your client
            if (lastDimension != null && !lastDimension.equals(currentDimension)) {
                sections.clear();
                currentSection = new Section();
                currentSection.set1();
            }
            lastDimension = currentDimension;
        } catch (Exception e) {
            // Dimension checking not available or failed
        }

        // Check if player moved far enough
        if (isFarEnough(currentSection.x1, currentSection.y1, currentSection.z1)) {
            currentSection.set2();

            sections.add(currentSection);

            // Remove oldest sections if exceeding limit
            while (sections.size() > maxSections.getValue()) {
                sections.remove(0);
            }

            currentSection = new Section();
            currentSection.set1();
        }
    }

    // Assume this is your render event - adjust as needed
    public void onRender3D(Render3DEvent event) {
        if (sections.isEmpty() || mc.player == null) return;

        Color color = new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());

        // Get your render utility or implementation
        Section previous = null;

        for (Section section : sections) {
            if (previous != null) {
                RenderUtil.drawLine3D(
                        previous.x2, previous.y2, previous.z2,
                        section.x1, section.y1, section.z1,
                        color
                );
            }

            RenderUtil.drawLine3D(
                    section.x1, section.y1, section.z1,
                    section.x2, section.y2, section.z2,
                    color
            );

            previous = section;
        }

        // Connect to current section if it exists
        if (previous != null && currentSection != null) {
            RenderUtil.drawLine3D(
                    previous.x2, previous.y2, previous.z2,
                    currentSection.x1, currentSection.y1, currentSection.z1,
                    color
            );
        }
    }

    private boolean isFarEnough(float x, float y, float z) {
        return Math.abs(mc.player.posX - x) >= sectionLength.getValue() ||
                Math.abs(mc.player.posY - y) >= sectionLength.getValue() ||
                Math.abs(mc.player.posZ - z) >= sectionLength.getValue();
    }

    private class Section {
        public float x1, y1, z1;
        public float x2, y2, z2;

        public void set1() {
            x1 = (float) mc.player.posX;
            y1 = (float) mc.player.posY;
            z1 = (float) mc.player.posZ;
        }

        public void set2() {
            x2 = (float) mc.player.posX;
            y2 = (float) mc.player.posY;
            z2 = (float) mc.player.posZ;
        }
    }
}
