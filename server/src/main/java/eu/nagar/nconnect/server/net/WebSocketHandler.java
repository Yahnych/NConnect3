/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.net;

import eu.nagar.nconnect.api.event.player.PlayerConnectionEvent;
import eu.nagar.nconnect.api.event.player.PlayerDisconnectEvent;
import eu.nagar.nconnect.api.player.Player;
import eu.nagar.nconnect.server.NConnectServer;
import eu.nagar.nconnect.server.config.ConfigOption;
import eu.nagar.nconnect.server.net.protocol.Packet;
import eu.nagar.nconnect.server.net.protocol.PacketCodec;
import eu.nagar.nconnect.server.net.protocol.PacketRegister;
import eu.nagar.nconnect.server.net.protocol.Protocol;
import eu.nagar.nconnect.server.player.NConnectPlayer;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;

import java.awt.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WebSocketHandler extends WebSocketServer {
    private NConnectServer server;
    private WebSocketSecurity security = new WebSocketSecurity();
    private Map<WebSocket, NConnectPlayer> socketPlayerMap = new HashMap<>();

    public WebSocketHandler(NConnectServer server) {
        super(new InetSocketAddress(ConfigOption.SERVER_PORT.getValueInt()));
        setReuseAddr(true);
        setTcpNoDelay(true);
        this.server = server;
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
        // Connection limit
        if (socketPlayerMap.size() > ConfigOption.SERVER_MAX_CONNECTIONS.getValueInt()) {
            server.getLogger().warn("Rejected handshake from " + conn.getRemoteSocketAddress().toString() + ". Reason: Server is full!");
            throw new InvalidDataException(1);
        }

        if (security.isConnectionThrottled(conn)) {
            server.getLogger().warn("Rejected handshake from " + conn.getRemoteSocketAddress().toString() + ". Reason: Connection throttled!");
            throw new InvalidDataException(1);
        }

        ServerHandshakeBuilder builder = super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
        builder.put("X-Forwarded-For", "*");
        builder.put("X-Real-IP", "*");
        return builder;
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        NConnectPlayer player = new NConnectPlayer(server, webSocket, clientHandshake);
        server.getLogger().info(webSocket.getRemoteSocketAddress().toString() + " Connected to the server.");

        PlayerConnectionEvent playerConnectionEvent = new PlayerConnectionEvent(player);
        server.getEventManager().callEvent(playerConnectionEvent);

        if (playerConnectionEvent.isCancelled()) {
            return;
        }

        socketPlayerMap.put(webSocket, player);
        server.getLogger().info(webSocket.getRemoteSocketAddress().toString() + " Connected to the server.");
        player.sendMessage("NConnect N3", Color.RED);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String reason, boolean b) {
        server.getLogger().info(webSocket.getRemoteSocketAddress().toString() + " Disconnected.");
        Player player = socketPlayerMap.get(webSocket);

        PlayerDisconnectEvent playerDisconnectEvent = new PlayerDisconnectEvent(player, i, reason);
        server.getEventManager().callEvent(playerDisconnectEvent);
        socketPlayerMap.remove(webSocket);
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteBuffer buffer) {
        NConnectPlayer player = socketPlayerMap.get(webSocket);
        if (player == null) {
            webSocket.close();
            return;
        }

        if (buffer.limit() < 1) {
            return;
        }

        if (buffer.limit() > 255) {
            return;
        }

        buffer.order(ByteOrder.LITTLE_ENDIAN);

        PacketCodec packetCodec = PacketRegister.SERVERBOUND.getCodec(buffer.get(0));
        if (packetCodec == null) {
            if (player.getServerConnection().isOpen()) {
                player.getServerConnection().send(buffer);
            }

            return;
        }

        Packet packet = packetCodec.decode(buffer, Protocol.L_6);

        if (packet != null) {
            player.getUpstreamHandler().handle(packet);
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        socketPlayerMap.remove(webSocket);
        e.printStackTrace();
    }

    @Override
    public void onStart() {
        server.getLogger().info("Server accepting connections on port " + getPort() + ".");
    }

    public Collection<NConnectPlayer> getConnectedPlayers() {
        return socketPlayerMap.values();
    }
}
