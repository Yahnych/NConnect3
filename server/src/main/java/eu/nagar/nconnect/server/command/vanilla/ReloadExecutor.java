/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.command.vanilla;

import eu.nagar.nconnect.api.ProxyServer;
import eu.nagar.nconnect.api.command.Command;
import eu.nagar.nconnect.api.command.CommandExecutor;
import eu.nagar.nconnect.api.command.CommandSender;

import java.awt.*;

public class ReloadExecutor implements CommandExecutor {
    private ProxyServer server;

    public ReloadExecutor(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(Command command, CommandSender sender, String[] args) {
        long start = System.currentTimeMillis();

        sender.sendMessage("Reloading NConnect...", Color.RED);
        server.getPluginManager().disablePlugins();
        server.getEventManager().unregisterEvents();
        server.getPluginManager().loadPlugins();
        server.getPluginManager().enablePlugins();

        long end = System.currentTimeMillis();
        sender.sendMessage("Reloaded plugins in " + (end - start) + "ms.");
    }
}
