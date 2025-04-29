package com.example.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;


public class BllryClientGui extends Screen {
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 20;
    private static final int PADDING = 10;


     // Main GUI screen for your client that will contain module buttons

    public BllryClientGui() {
        super(Text.literal("Bllry Client"));
    }

    protected void int() {
        super.init();

        // calcuaalte pos for the layout
        int startY = 50;
        int startX = this.width / 2;

        // temp module button before I can add other modules
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

    private void int toggleModule(String moduleName) {
        // Here you would toggle the state of the module
        System.out.println("Toggled " + moduleName + " module");

        // In a real implementation, you would modify the module state
        // For example: moduleManager.getModule(moduleName).toggle();

    }
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        
    }
}