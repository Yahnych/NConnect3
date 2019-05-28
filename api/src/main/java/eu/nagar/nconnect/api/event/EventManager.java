/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.event;

public interface EventManager {
    /**
     * Post an event to the event bus.
     * @param event event
     */
    void callEvent(Event event);

    /**
     * Register events from a class
     * @param listener class
     */
    void registerEvents(EventListener listener);

    /**
     * Unregister all events.
     */
    void unregisterEvents();
}
