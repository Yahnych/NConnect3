/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.event;

public interface EventManager {
    void callEvent(Event event);
    void registerEvents(EventListener listener);
    void unregisterEvents();
}
