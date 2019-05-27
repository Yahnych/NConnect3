/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.net;

import eu.nagar.nconnect.server.config.ConfigOption;
import org.java_websocket.WebSocket;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class WebSocketSecurity {
    private Map<InetSocketAddress, Long> lastConnection = new HashMap<>();

    public boolean isConnectionThrottled(WebSocket socket) {
        long lastConnectionMs = lastConnection.getOrDefault(socket.getRemoteSocketAddress(), 0L);
        lastConnection.put(socket.getRemoteSocketAddress(), System.currentTimeMillis());

        return (System.currentTimeMillis() - lastConnectionMs) <= ConfigOption.SERVER_CONNECTION_THROTTLE.getValueInt();
    }
}
