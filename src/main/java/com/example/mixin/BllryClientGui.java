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

        // Add example module buttons
        this.addDrawableChild(new ButtonWidget.Builder(
                Text.literal("Speed Module"),
                button -> toggleModule("Speed"),
                ButtonWidget.DEFAULT_NARRATION)
                .dimensions(centerX - BUTTON_WIDTH - PADDING, startY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());

        this.addDrawableChild(new ButtonWidget.Builder(
                Text.literal("Fly Module"),
                button -> toggleModule("Fly"),
                ButtonWidget.DEFAULT_NARRATION)
                .dimensions(centerX + PADDING, startY, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
    }

    private void toggleModule(String moduleName) {
        // Call the client's toggle method
        if (BllryClient.INSTANCE != null) {
            BllryClient.INSTANCE.toggleModule(moduleName);
        } else {
            System.out.println("Client instance is null!");
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        // Draw title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        // Setting to false allows the game to continue running when the GUI is open
        return false;
    }
}