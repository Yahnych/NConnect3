/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.command;

public interface CommandExecutor {
    void execute(Command command, CommandSender sender, String[] args);
}
