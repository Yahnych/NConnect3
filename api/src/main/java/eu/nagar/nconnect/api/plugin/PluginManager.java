/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.plugin;

import eu.nagar.nconnect.api.command.Command;
import eu.nagar.nconnect.api.event.EventListener;

import java.util.Collection;
import java.util.Set;

public interface PluginManager {
    /**
     * Get specified plugin by it's name
     * @param name plugin name.
     * @return plugin instance.
     */
    Plugin getPlugin(String name);

    /**
     * Get all loaded plugins
     * @return collection of loaded plugins
     */
    Collection<Plugin> getPlugins();

    /**
     * Register a command.
     * @param plugin plugin instance.
     * @param command command.
     */
    void registerCommand(Plugin plugin, Command command);

    /**
     * Register events from class
     * @param plugin plugin instance.
     * @param listener event listener instance.
     */
    void registerEvents(Plugin plugin, EventListener listener);

    /**
     * Load plugins.
     */
    void loadPlugins();

    /**
     * Enable all loaded plugins.
     */
    void enablePlugins();

    /**
     * Disable all loaded plugins.
     */
    void disablePlugins();
}
