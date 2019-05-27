/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.plugin;

import eu.nagar.nconnect.api.ProxyServer;
import org.simpleyaml.configuration.file.YamlFile;
import org.slf4j.Logger;

import java.io.File;

public abstract class Plugin {
    private ProxyServer server;
    private Logger logger;
    private PluginDescription description;
    private YamlFile yamlFile;
    private File dataFolder;

    protected Plugin() {

    }

    public void init(ProxyServer server, Logger logger, PluginDescription description, YamlFile yamlFile, File dataFolder) {
        this.server = server;
        this.logger = logger;
        this.description = description;
        this.yamlFile = yamlFile;
        this.dataFolder = dataFolder;
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }

    public PluginDescription getDescription() {
        return description;
    }

    public YamlFile getConfig() {
        return yamlFile;
    }

    public File getFolder() {
        return dataFolder;
    }
}
