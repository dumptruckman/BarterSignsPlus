package com.dumptruckman.bartersignsplus.sign;

/**
 * @author dumptruckman
 */
public enum SignPhase {
    READY;

    public boolean equalTo(Object o) {
        return this.toString().equals(o.toString());
    }
}
