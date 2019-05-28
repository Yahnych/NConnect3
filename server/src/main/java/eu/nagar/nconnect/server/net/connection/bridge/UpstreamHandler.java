/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.net.connection.bridge;

import eu.nagar.nconnect.api.command.CommandResult;
import eu.nagar.nconnect.api.event.player.game.PlayerChatEvent;
import eu.nagar.nconnect.api.event.player.game.PlayerMouseEvent;
import eu.nagar.nconnect.api.event.player.game.PlayerSetNickEvent;
import eu.nagar.nconnect.api.util.Position;
import eu.nagar.nconnect.server.net.protocol.Packet;
import eu.nagar.nconnect.server.net.protocol.PacketReceiver;
import eu.nagar.nconnect.server.net.protocol.packet.PacketInChat;
import eu.nagar.nconnect.server.net.protocol.packet.PacketInMouse;
import eu.nagar.nconnect.server.net.protocol.packet.PacketInSetNick;
import eu.nagar.nconnect.server.player.NConnectPlayer;

public class UpstreamHandler extends PacketReceiver {
    private NConnectPlayer player;

    public UpstreamHandler(NConnectPlayer player) {
        this.player = player;
    }

    @Override
    public void handle(Packet packet) {
        packet.handle(this);
    }

    @Override
    public void handle(PacketInChat packet) {
        PlayerChatEvent playerChatEvent = new PlayerChatEvent(player, packet.getMessage());
        player.getNConnectServer().getEventManager().callEvent(playerChatEvent);

        if (packet.getMessage().startsWith("/")) {
            CommandResult result = player.getNConnectServer().getCommandManager().dispatchCommand(packet.getMessage().substring(1), player);
            if (result != CommandResult.NOT_FOUND) {
                return;
            }
        }

        if (!playerChatEvent.isCancelled()) {
            packet.setMessage(playerChatEvent.getMessage());
            player.getServerConnection().sendPacket(packet);
        }
    }

    @Override
    public void handle(PacketInMouse packet) {
        PlayerMouseEvent playerMouseEvent = new PlayerMouseEvent(player, new Position((int) packet.getMouseX(), (int) packet.getMouseY()));
        player.getNConnectServer().getEventManager().callEvent(playerMouseEvent);

        if (!playerMouseEvent.isCancelled()) {
            packet.setMouseX(playerMouseEvent.getMousePosition().getX());
            packet.setMouseY(playerMouseEvent.getMousePosition().getY());

            player.setMousePosition(playerMouseEvent.getMousePosition());
            player.getServerConnection().sendPacket(packet);
        }
    }

    @Override
    public void handle(PacketInSetNick packet) {
        PlayerSetNickEvent playerSetNickEvent = new PlayerSetNickEvent(player, packet.getName());
        player.getNConnectServer().getEventManager().callEvent(playerSetNickEvent);

        if (!playerSetNickEvent.isCancelled()) {
            packet.setName(playerSetNickEvent.getNickname());
            player.setName(playerSetNickEvent.getNickname());
            player.getServerConnection().sendPacket(packet);
        }
    }
}
