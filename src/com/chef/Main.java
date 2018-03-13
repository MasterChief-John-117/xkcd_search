package com.chef;

import java.util.*;
import static spark.Spark.*;

class Main
{
    public static final xkcdClient xkcd = new xkcdClient();

    public static void main(String[] args) {
        //Start by configuring automatic syncing of comics to run every 30 minutes
        Timer autoSync = new Timer();
        autoSync.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        xkcd.sync();
                    }
                }, new Date(), 30 * 60 * 1000);
    }
}
