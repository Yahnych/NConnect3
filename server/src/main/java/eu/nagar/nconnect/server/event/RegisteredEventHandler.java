/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.event;

import eu.nagar.nconnect.api.event.EventHandler;
import eu.nagar.nconnect.api.event.EventListener;
import eu.nagar.nconnect.api.event.EventPriority;
import eu.nagar.nconnect.api.plugin.Plugin;

import java.lang.reflect.Method;

public class RegisteredEventHandler {
    private Plugin plugin;
    private EventPriority priority;
    private EventListener instance;
    private Method method;
    private Class<?> event;

    RegisteredEventHandler(Plugin plugin, EventHandler listener, EventListener instance, Method method, Class<?> event) {
        this.plugin = plugin;
        this.priority = listener.priority();
        this.instance = instance;
        this.method = method;
        this.event = event;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public EventListener getObject() {
        return instance;
    }

    public Method getMethod() {
        return method;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Class<?> getEvent() {
        return event;
    }
}