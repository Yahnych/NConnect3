/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.net.connection.bridge;

import eu.nagar.nconnect.api.event.packet.PacketReceivedEvent;
import eu.nagar.nconnect.server.net.protocol.*;
import eu.nagar.nconnect.server.net.protocol.packet.PacketOutChat;
import eu.nagar.nconnect.server.net.protocol.packet.PacketOutStats;
import eu.nagar.nconnect.server.player.NConnectPlayer;

import java.nio.ByteBuffer;

public class DownstreamHandler extends PacketReceiver {
    private NConnectPlayer player;

    public DownstreamHandler(NConnectPlayer player) {
        this.player = player;
    }

    @Override
    public void handle(ByteBuffer buffer) {
        PacketReceivedEvent packetReceivedEvent = new PacketReceivedEvent(player, buffer);
        player.getNConnectServer().getEventManager().callEvent(packetReceivedEvent);

        if (packetReceivedEvent.isCancelled()) {
            return;
        }

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
    public void handle(Packet packet) {
        super.handle(packet);
    }

    @Override
    public void handle(PacketOutChat packet) {
        player.sendPacket(packet);
    }

    @Override
    public void handle(PacketOutStats packet) {
        /*ServerStatsEvent serverStatsEvent = new ServerStatsEvent();
        player.getNConnectServer().getEventManager().callEvent(serverStatsEvent);
         //TODO TODO
        packet.setStats(serverStatsEvent.getStats());**/
        player.sendPacket(packet);
    }
}
