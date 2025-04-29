package com.example.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BllryClientGui extends Screen {

    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 20;
    private static final int PADDING = 10;

    public BllryClientGui() {
        super(Text.literal("Bllry Client"));
    }

    @Override
    protected void init() {
        super.init();

        // Calculate positions for centered layout
        int startY = 50;
        int centerX = this.width / 2;

        // Add Speed module button
        this.addDrawableChild(new ButtonWidget.Builder(
                Text.literal(getModuleButtonText("Speed")),
                button -> {
                    toggleModule("Speed");
                    // Update button text after toggle
                    button.setMessage(Text.literal(getModuleButtonText("Speed")));
                },
                ButtonWidget.DEFAULT_NARRATION)
                .dimensions(centerX - BUTTON_WIDTH - PADDING, startY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());

        // Add Fly module button
        this.addDrawableChild(new ButtonWidget.Builder(
                Text.literal(getModuleButtonText("Fly")),
                button -> {
                    toggleModule("Fly");
                    // Update button text after toggle
                    button.setMessage(Text.literal(getModuleButtonText("Fly")));
                },
                ButtonWidget.DEFAULT_NARRATION)
                .dimensions(centerX + PADDING, startY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());

        // Add Close button at the bottom
        this.addDrawableChild(new ButtonWidget.Builder(
                Text.literal("Close"),
                button -> this.close(),
                ButtonWidget.DEFAULT_NARRATION)
                .dimensions(centerX - 60, this.height - 40, 120, BUTTON_HEIGHT)
                .build());
    }

    private String getModuleButtonText(String moduleName) {
        boolean enabled = BllryClient.INSTANCE.isModuleEnabled(moduleName);
        return moduleName + ": " + (enabled ? "ON" : "OFF");
    }

    private void toggleModule(String moduleName) {
        if (BllryClient.INSTANCE != null) {
            BllryClient.INSTANCE.toggleModule(moduleName);
        } else {
            System.out.println("Error: Client instance is null!");
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        // Draw title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);

        // Draw client version
        context.drawTextWithShadow(this.textRenderer, Text.literal("v1.0.0"), 5, 5, 0xAAAAAA);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        // Setting to false allows the game to continue running when the GUI is open
        return false;
    }
}