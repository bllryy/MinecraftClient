package com.bllry.client.core.events;


/**
 *
 * Base Class for all the events
 *
 */

public class Event {
    /**
     *
     * Weather the event is cancled or not
     */
    private boolean cancelled;

    /**
     *
     * Event Types
     */
    public enum Type {
        PRE,    // before
        POST,   // after the action
        SEND    // when sending info about an action
    }

    private final Type;

    /**
     *
     * Making the new event
     * @param type event type
     */
    public Event(Type type){
        this.type = type;
        this.cancelled = false;
    }
    /**
     * Get the event type
     * @return Event type
     */
    public Type getType() {
        return type;
    }
    /**
     * Check if the event is cancelled
     * @return True if cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }
    /**
     * Set whether the event is cancelled
     * @param cancelled True to cancel the event
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
