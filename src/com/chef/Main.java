package com.chef;

import java.util.*;

class Main
{
    public static final xkcdClient xkcd = new xkcdClient();

    public static void main(String[] args) {
        xkcd.sync();
        Timer autoSync = new Timer();
        autoSync.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        xkcd.sync();
                    }
                }, 30 * 60 * 1000);
    }
}
