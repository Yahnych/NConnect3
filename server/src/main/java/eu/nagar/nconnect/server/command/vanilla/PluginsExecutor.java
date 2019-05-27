/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.command.vanilla;

import eu.nagar.nconnect.api.ProxyServer;
import eu.nagar.nconnect.api.command.Command;
import eu.nagar.nconnect.api.command.CommandExecutor;
import eu.nagar.nconnect.api.command.CommandSender;
import eu.nagar.nconnect.api.plugin.Plugin;

import java.util.Collection;

public class PluginsExecutor implements CommandExecutor {
    private ProxyServer server;

    public PluginsExecutor(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Command command, CommandSender sender, String[] args) {
        Collection<Plugin> plugins = server.getPluginManager().getPlugins();
        sender.sendMessage("Plugins (" + plugins.size() + "):");
        for (Plugin plugin : plugins) {
            sender.sendMessage(plugin.getDescription().name() + " v" + plugin.getDescription().version());
        }
    }
}
