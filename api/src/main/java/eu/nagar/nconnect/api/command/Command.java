/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.command;

public class Command {
    private String name;
    private String permission;
    private String description;
    private String[] aliases;
    private CommandExecutor executor;

    public Command(String name, String permission, String description, String[] aliases, CommandExecutor executor) {
        this.name = name;
        this.permission = permission;
        this.description = description;
        this.aliases = aliases;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }
}
