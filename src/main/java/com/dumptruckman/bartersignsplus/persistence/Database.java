package com.dumptruckman.bartersignsplus.persistence;

import com.dumptruckman.bartersignsplus.sign.BarterSign;

import java.io.IOException;

/**
 * @author dumptruckman
 */
public interface Database {

    public void load() throws IOException;

    public void save(boolean isReload);

    public void initializeSign(BarterSign sign);

    public void removeSign(BarterSign sign);
}
