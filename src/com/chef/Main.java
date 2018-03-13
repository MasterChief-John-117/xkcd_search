package com.chef;

import com.google.gson.*;
import java.util.*;
import static spark.Spark.*;

class Main
{
    public static final xkcdClient xkcd = new xkcdClient();
    public static final int port = 80;
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        //Start by configuring automatic syncing of comics to run every 30 minutes
        Timer autoSync = new Timer();
        autoSync.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        xkcd.sync();
                    }
                }, new Date(), 30 * 60 * 1000);

        System.out.println("Setting port to " + port);
        port(port);

        get("/title/:title", ((request, response) -> {
            String title = request.params(":title").replaceAll("%20", " ");
            response.type("application/json");
            return gson.toJson(xkcd.searchByTitle(title));
        }));
    }
}
