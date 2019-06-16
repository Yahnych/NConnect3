/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.plugin;

import eu.nagar.nconnect.api.plugin.Plugin;
import eu.nagar.nconnect.api.plugin.PluginDescription;
import eu.nagar.nconnect.api.plugin.PluginManager;
import eu.nagar.nconnect.server.NConnectServer;
import org.simpleyaml.configuration.file.YamlFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class StandardPluginManager implements PluginManager {
    private NConnectServer server;
    private File pluginDirectory;
    private static final Logger LOGGER = LoggerFactory.getLogger("Plugin Manager");
    private Map<String, Plugin> pluginsByName = new HashMap<>();

    public StandardPluginManager(NConnectServer server) {
        this.server = server;
        this.pluginDirectory = new File(Paths.get("").toAbsolutePath().toString(), "plugins");
    }

    @Override
    public Plugin getPlugin(String name) {
        return pluginsByName.get(name);
    }

    @Override
    public Collection<Plugin> getPlugins() {
        return pluginsByName.values();
    }

    @Override
    public void loadPlugins() {
        if (!pluginDirectory.exists()) {
            if(!pluginDirectory.mkdirs()) {
                LOGGER.error("Failed to create plugins directory.");
            }
            return;
        }

        File[] jarFiles = pluginDirectory.listFiles();
        if (jarFiles == null || jarFiles.length == 0) {
            LOGGER.info("No plugins to load.");
            return;
        }

        for (File jarFile : jarFiles) {
            if (!jarFile.getName().endsWith(".jar")) {
                continue;
            }
            try {
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
                JarInputStream inputStream = new JarInputStream(new FileInputStream(jarFile));
                while (true) {
                    JarEntry jarEntry = inputStream.getNextJarEntry();
                    if (jarEntry == null) {
                        break;
                    }
                    if (jarEntry.getName() == null || !jarEntry.getName().endsWith(".class")) {
                        continue;
                    }
                    String className = jarEntry.getName();
                    className = className
                            .replace("/", ".")
                            .substring(0, className.lastIndexOf(".class"));
                    Class<?> clazz = classLoader.loadClass(className);

                    if (Plugin.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(PluginDescription.class)) {
                        this.loadPlugin((Plugin) clazz.newInstance());
                    }
                }
            } catch (FileNotFoundException ignored) {

            } catch (IOException | ClassNotFoundException | NoClassDefFoundError | IllegalAccessException | InstantiationException e) {
                LOGGER.info("Error occurred whilst loading plugin file " + jarFile.getName());
                e.printStackTrace();
            }
        }
    }

    private void loadPlugin(Plugin plugin) {
        PluginDescription pluginDescription = plugin.getClass().getAnnotation(PluginDescription.class);
        Logger logger = LoggerFactory.getLogger(pluginDescription.name());
        File pluginFolder = new File(pluginDirectory, pluginDescription.name());
        YamlFile yamlFile = new YamlFile(new File(pluginFolder, "config.yml"));

        plugin.init(server, logger, pluginDescription, yamlFile, pluginFolder);
        pluginsByName.put(pluginDescription.name(), plugin);
    }

    @Override
    public void enablePlugins() {
        for (Plugin plugin : pluginsByName.values()) {
            LOGGER.info("Enabling " + plugin.getDescription().name() + " " + plugin.getDescription().version() + ".");
            try {
                plugin.onEnable();
            } catch (Exception error) {
                LOGGER.error("Error occurred whilst enabling " + plugin.getDescription().name());
                error.printStackTrace();
            }
        }
    }

    @Override
    public void disablePlugins() {
        for (Plugin plugin : pluginsByName.values()) {
            LOGGER.info("Disabling " + plugin.getDescription().name() + " " + plugin.getDescription().version() + ".");
            try {
                plugin.onDisable();
                server.getEventManager().unregisterEvents(plugin);
            } catch (Exception error) {
                LOGGER.error("Error occurred whilst disabling " + plugin.getDescription().name());
            }
        }
    }
}
