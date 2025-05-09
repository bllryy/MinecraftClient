package me.bllry.client.event.impl;

import me.bllry.client.event.Event;
import me.bllry.client.event.Stage;

public class UpdateWalkingPlayerEvent extends Event {
    private final Stage stage;

    public UpdateWalkingPlayerEvent(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
