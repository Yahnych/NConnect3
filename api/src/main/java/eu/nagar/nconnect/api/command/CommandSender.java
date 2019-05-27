/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.command;

import java.awt.*;

public interface CommandSender {
    void sendMessage(String message);
    void sendMessage(String message, Color color);
    boolean hasPermission(String permission);
}
