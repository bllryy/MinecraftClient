package me.bllry.client.event.impl;

import me.bllry.client.event.Event;

public class ChatEvent extends Event {
    private final String content;

    public ChatEvent(String content) {
        this.content = content;
    }

    public String getMessage() {
        return content;
    }
}
