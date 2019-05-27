/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.command;

import eu.nagar.nconnect.api.command.CommandBuilder;
import eu.nagar.nconnect.api.command.Command;
import eu.nagar.nconnect.server.NConnectServer;
import eu.nagar.nconnect.server.command.vanilla.*;

public class VanillaCommands {
    public void register(NConnectServer server) {
        Command help = new CommandBuilder()
                .setName("help")
                .setDescription("Show command help")
                .setPermission("nconnect.help")
                .setExecutor(new HelpExecutor(server.getCommandManager()))
                .build();

        Command connect = new CommandBuilder()
                .setName("server")
                .setDescription("Connect to a different upstream")
                .setPermission("nconnect.server")
                .setExecutor(new ServerExecutor(server))
                .build();

        Command plugins = new CommandBuilder()
                .setName("plugins")
                .setDescription("Display enabled plugins")
                .setPermission("nconnect.plugins")
                .setExecutor(new PluginsExecutor(server))
                .build();

        Command exit = new CommandBuilder()
                .setName("exit")
                .setDescription("Exit the server")
                .setPermission("nconnect.exit")
                .setExecutor(new ExitExecutor(server))
                .build();

        Command say = new CommandBuilder()
                .setName("broadcast")
                .setDescription("Broadcast message")
                .setPermission("nconnect.broadcast")
                .setExecutor(new BroadcastExecutor(server))
                .build();

        Command reload = new CommandBuilder()
                .setName("reload")
                .setDescription("Reload plugins")
                .setPermission("nconnect.reload")
                .setExecutor(new ReloadExecutor(server))
                .build();

        server.getCommandManager().registerCommand(help);
        server.getCommandManager().registerCommand(connect);
        server.getCommandManager().registerCommand(plugins);
        server.getCommandManager().registerCommand(exit);
        server.getCommandManager().registerCommand(say);
        server.getCommandManager().registerCommand(reload);
    }
}
