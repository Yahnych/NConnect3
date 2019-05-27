/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.player;

import eu.nagar.nconnect.api.player.Player;
import eu.nagar.nconnect.api.server.GameServer;
import eu.nagar.nconnect.api.util.Position;
import eu.nagar.nconnect.server.NConnectServer;
import eu.nagar.nconnect.server.net.connection.ServerConnection;
import eu.nagar.nconnect.server.net.connection.bridge.DownstreamHandler;
import eu.nagar.nconnect.server.net.connection.bridge.UpstreamHandler;
import eu.nagar.nconnect.server.net.protocol.Packet;
import eu.nagar.nconnect.server.net.protocol.PacketCodec;
import eu.nagar.nconnect.server.net.protocol.PacketRegister;
import eu.nagar.nconnect.server.net.protocol.Protocol;
import eu.nagar.nconnect.server.net.protocol.packet.PacketOutChat;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class NConnectPlayer implements Player {
    private NConnectServer server;
    private String name = "";
    private String address;
    private Position mousePosition;
    private WebSocket socket;
    private ServerConnection serverConnection;
    private DownstreamHandler downstreamHandler;
    private UpstreamHandler upstreamHandler;

    public NConnectPlayer(NConnectServer server, WebSocket socket, ClientHandshake handshake) {
        this.server = server;
        this.socket = socket;

        if (handshake.hasFieldValue("X-Real-IP")) {
            this.address = handshake.getFieldValue("X-Real-IP");
        } else if (handshake.hasFieldValue("X-Forwarded-For")) {
            this.address = handshake.getFieldValue("X-Forwarded-For");
        } else {
            this.address = socket.getRemoteSocketAddress().toString();
        }

        this.upstreamHandler = new UpstreamHandler(this);
        this.downstreamHandler = new DownstreamHandler(this);
        this.connectTo(server.getServerManager().getServers().iterator().next());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMousePosition(Position mousePosition) {
        this.mousePosition = mousePosition;
    }

    public DownstreamHandler getDownstreamHandler() {
        return downstreamHandler;
    }

    public UpstreamHandler getUpstreamHandler() {
        return upstreamHandler;
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public WebSocket getSocket() {
        return socket;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public Position getMousePosition() {
        return this.mousePosition;
    }

    @Override
    public void connectTo(GameServer server) {
        try {
            if (serverConnection != null && serverConnection.isOpen()) {
                try {
                    serverConnection.closeBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.serverConnection = new ServerConnection(this, new URI(server.getAddress()), new HashMap<String, String>() {{
                put("Cache-Control", "None");
                put("User-Agent", "Nagar (Niall Agar) Group Nconnect Server Upstream Connection N3 v1.3-A");
                put("X-Real-IP", address);
            }});
            this.serverConnection.connect();
            for (String motdLine : server.getMotd()) {
                this.sendMessage(motdLine);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Packet packet) {
        PacketCodec packetCodec = PacketRegister.CLIENTBOUND.getCodec(packet.getClass());
        if (socket.isOpen()) {
            socket.send(packetCodec.encode(packet, Protocol.L_6).array());
        }
    }

    @Override
    public void sendMessage(String message) {
        PacketOutChat chat = new PacketOutChat();
        chat.setName("SERVER");
        chat.setColor(Color.GRAY);
        chat.setMessage(message);
        sendPacket(chat);
    }

    @Override
    public void sendMessage(String message, Color color) {
        PacketOutChat chat = new PacketOutChat();
        chat.setName(message);
        chat.setColor(color);
        chat.setMessage("");
        sendPacket(chat);
    }

    @Override
    public boolean hasPermission(String permission) {
        return false;
    }

    public NConnectServer getNConnectServer() {
        return server;
    }
}