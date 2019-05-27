/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.api.event;

public interface Cancellable {
    void setCancelled(boolean cancel);
    boolean isCancelled();
}
