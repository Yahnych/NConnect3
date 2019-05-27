/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.command;

import eu.nagar.nconnect.api.ProxyServer;
import eu.nagar.nconnect.api.command.*;
import eu.nagar.nconnect.server.NConnectServer;

import java.awt.*;
import java.util.*;

public class StandardCommandManager implements CommandManager {
    private NConnectServer server;
    private Set<Command> registeredCommands = new HashSet<>();

    public StandardCommandManager(NConnectServer server) {
        this.server = server;
    }

    @Override
    public Set<Command> getCommands() {
        return registeredCommands;
    }

    @Override
    public Command getCommand(String name) {
        for (Command command : getCommands()) {
            if (command.getName().equals(name)) return command;
        }

        return null;
    }

    @Override
    public void registerCommand(Command command) {
        registeredCommands.add(command);
    }

    public CommandResult dispatchCommand(String commandString, CommandSender sender) {
        String[] commandParts = commandString.split(" ");
        Command command = getCommand(commandParts[0]);

        if (command == null) {
            return CommandResult.NOT_FOUND;
        }

        try {
            String[] args = Arrays.copyOfRange(commandParts, 1, commandParts.length);
            if (!sender.hasPermission(command.getPermission())) {
                sender.sendMessage("You do not have permission to use this command.", Color.RED);
                return CommandResult.SUCCESS;
            }
            command.getExecutor().execute(command, sender, args);
        } catch (Exception error) {
            return CommandResult.ERROR;
        }

        return CommandResult.SUCCESS;
    }

    public ProxyServer getServer() {
        return server;
    }
}