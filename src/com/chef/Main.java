package com.chef;

import java.util.*;

class Main
{
    public static final xkcdClient xkcd = new xkcdClient();

    public static void main(String[] args) {
        Timer autoSync = new Timer();
        autoSync.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        xkcd.sync();
                    }
                }, new Date(), 30 * 60 * 1000);
    }
}
