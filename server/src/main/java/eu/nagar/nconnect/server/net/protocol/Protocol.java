/*
 * Copyright (c) 2016 - 2019  Nagar Group
 */

package eu.nagar.nconnect.server.net.protocol;

public enum Protocol {
    L_4(4),
    L_5(5),
    L_6(6);

    private int key;

    Protocol(int key) {
        this.key = key;
    }

    public int getNumber() {
        return key;
    }
}
