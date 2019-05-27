/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.plugin;

import eu.nagar.nconnect.api.command.Command;
import eu.nagar.nconnect.api.event.EventListener;

import java.util.Collection;
import java.util.Set;

public interface PluginManager {
    Plugin getPlugin(String name);
    Collection<Plugin> getPlugins();
    void registerCommand(Plugin plugin, Command command);
    void registerEvents(Plugin plugin, EventListener listener);
    void loadPlugins();
    void enablePlugins();
    void disablePlugins();
}
