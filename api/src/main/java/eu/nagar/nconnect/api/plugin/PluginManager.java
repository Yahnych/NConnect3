/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.plugin;

import java.util.Collection;

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
