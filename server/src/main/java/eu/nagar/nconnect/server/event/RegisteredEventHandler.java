/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.event;

import eu.nagar.nconnect.api.event.EventHandler;
import eu.nagar.nconnect.api.event.EventListener;
import eu.nagar.nconnect.api.event.EventPriority;

import java.lang.reflect.Method;

public class RegisteredEventHandler {
    private EventPriority priority;
    private EventListener instance;
    private Method method;
    private Class<?> event;

    RegisteredEventHandler(EventHandler listener, EventListener instance, Method method, Class<?> event) {
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

    public Class<?> getEvent() {
        return event;
    }
}