/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.server;

import java.util.Collection;

public interface ServerManager {
    void registerServer(GameServer server);
    Collection<GameServer> getServers();
    GameServer getServer(String name);
}
