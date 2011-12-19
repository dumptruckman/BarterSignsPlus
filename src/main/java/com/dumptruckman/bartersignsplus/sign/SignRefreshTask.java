package com.dumptruckman.bartersignsplus.sign;

/**
 * @author dumptruckman
 */
public class SignRefreshTask implements Runnable {

    MenuSign menuSign;

    public SignRefreshTask(MenuSign menuSign) {
        this.menuSign = menuSign;
    }

    public void run() {
        menuSign.goMainMenu();
    }
}
