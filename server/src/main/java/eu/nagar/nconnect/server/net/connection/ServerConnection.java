/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.net.connection;

import eu.nagar.nconnect.server.net.protocol.Packet;
import eu.nagar.nconnect.server.net.protocol.PacketCodec;
import eu.nagar.nconnect.server.net.protocol.PacketRegister;
import eu.nagar.nconnect.server.net.protocol.Protocol;
import eu.nagar.nconnect.server.player.NConnectPlayer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;

public class ServerConnection extends WebSocketClient {
    private NConnectPlayer player;

    public ServerConnection(NConnectPlayer player, URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
        this.player = player;

        setReuseAddr(true);
        setTcpNoDelay(true);
    }

    public void sendPacket(Packet packet) {
        PacketCodec packetCodec = PacketRegister.SERVERBOUND.getCodec(packet.getClass());
        if (this.isOpen()) {
            this.send(packetCodec.encode(packet, Protocol.L_6).array());
        }
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send(new byte[] {(byte) 254, 6, 0, 0, 0});
        send(new byte[] {(byte) 255, 1, 0, 0, 0});
    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void onMessage(ByteBuffer buffer) {
        PacketCodec packetCodec = PacketRegister.CLIENTBOUND.getCodec(buffer.get(0));
        if (packetCodec == null) {
            if (player.getSocket().isOpen()) {
                player.getSocket().send(buffer.array());
            }
            return;
        }

        Packet packet = packetCodec.decode(buffer, Protocol.L_6);

        if (packet != null) {
            player.getDownstreamHandler().handle(packet);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
