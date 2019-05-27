/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.net.connection.bridge;

import eu.nagar.nconnect.server.net.protocol.Packet;
import eu.nagar.nconnect.server.net.protocol.PacketReceiver;
import eu.nagar.nconnect.server.net.protocol.packet.PacketOutChat;
import eu.nagar.nconnect.server.net.protocol.packet.PacketOutStats;
import eu.nagar.nconnect.server.player.NConnectPlayer;

public class DownstreamHandler extends PacketReceiver {
    private NConnectPlayer player;

    public DownstreamHandler(NConnectPlayer player) {
        this.player = player;
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
