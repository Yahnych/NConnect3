/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.player;

import eu.nagar.nconnect.api.command.CommandSender;
import eu.nagar.nconnect.api.server.GameServer;
import eu.nagar.nconnect.api.util.Position;

public interface Player extends CommandSender {
    /**
     * Get the players name, if set
     * @return nickname
     */
    String getName();

    /**
     * Get the players remote address
     * @return address
     */
    String getAddress();

    /**
     * Get the players last mouse position
     * @return position
     */
    Position getMousePosition();

    /**
     * Get the game server the player is connected to.
     * @return game server
     */
    GameServer getGameServer();

    /**
     * Connect to a server
     * @param server game server to connect to.
     */
    void connectTo(GameServer server);

    /**
     * Kick the player
     */
    void kick();
}
