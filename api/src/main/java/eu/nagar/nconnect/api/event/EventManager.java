/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.event;

import eu.nagar.nconnect.api.plugin.Plugin;

public interface EventManager {
    /**
     * Post an event to the event bus.
     * @param event event
     */
    void callEvent(Event event);

    /**
     * Register events from a class
     * @param plugin plugin instance
     * @param listener class
     */
    void registerEvents(Plugin plugin, EventListener listener);

    /**
     * Unregister all events from a plugin.
     */
    void unregisterEvents(Plugin plugin);

    /**
     * Unregister all events.
     */
    void unregisterEvents();
}
