/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.event.server;

import eu.nagar.nconnect.api.event.Cancellable;
import eu.nagar.nconnect.api.event.Event;

public class ServerStatsEvent extends Event implements Cancellable {

    public ServerStatsEvent() {
        //TODO
    }

    @Override
    public void setCancelled(boolean cancel) {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
