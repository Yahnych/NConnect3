/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.net.protocol.packet;

import eu.nagar.nconnect.server.net.protocol.Packet;
import eu.nagar.nconnect.server.net.protocol.PacketReceiver;

public class PacketInMouse extends Packet {
    private int mouseX, mouseY;

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getMouseX() {
        return mouseX;
    }

    @Override
    public void handle(PacketReceiver receiver) {
        receiver.handle(this);
    }
}
