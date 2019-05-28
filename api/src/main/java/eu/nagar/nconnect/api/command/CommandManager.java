/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.command;

import java.util.Set;

public interface CommandManager {
    /**
     * Register a command.
     * @param command command to register
     */
    void registerCommand(Command command);

    /**
     * Get all registered commands
     * @return collection of commands.
     */
    Set<Command> getCommands();

    /**
     * Get a specified command by it's name.
     * @param name command name (not alias)
     * @return command
     */
    Command getCommand(String name);

    /**
     * Dispatch a command from input string.
     * @param string command input string
     * @param sender command sender
     * @return command result.
     */
    CommandResult dispatchCommand(String string, CommandSender sender);
}
