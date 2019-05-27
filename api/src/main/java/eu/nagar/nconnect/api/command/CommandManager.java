/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.command;

import java.util.Set;

public interface CommandManager {
    void registerCommand(Command command);
    Set<Command> getCommands();
    Command getCommand(String name);
    CommandResult dispatchCommand(String string, CommandSender sender);
}
