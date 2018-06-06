package com.chef;

import com.google.gson.*;

import java.io.*;
import java.util.*;
import static spark.Spark.*;

class Main
{
    public static final xkcdClient xkcd = new xkcdClient();
    public static final int port = 9000;
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

        before(((request, response) -> {
            String reqInfo = "DateTime: " + new Date() + "\n";
            reqInfo += "Client IP: " + request.ip() + "\n";
            reqInfo += "Headers: " + request.headers().toString() + "\n";
            reqInfo += "User-Agent: " + request.userAgent() + "\n";
            System.out.println(reqInfo);
        }));

        get("/xkcd/tags/:tag", ((request, response) ->{
            String params = request.params(":tag").replaceAll("%20", " ");
            response.type("application/json");
            return gson.toJson(xkcd.searchTags(params));
        }));

        get("/xkcd/tags/:num/add/:tag", ((request, response) ->{
            Comic sel = null;
            try{
                sel = xkcd.findByNum(Integer.parseInt(request.params(":num")));
            }
            catch(Exception ex)
            {
                response.status(404);
                return ("Comic not found");
            }
            String tag = request.params(":tag").replaceAll("%20", " ");
            sel.tags.add(new Tag(tag, request.ip()));
            xkcd.saveDb();
            return "Added tag \"" + tag + "\" to comic " + request.params(":num");
        }));

        get("/xkcd/title/:title", ((request, response) -> {
            String title = request.params(":title").replaceAll("%20", " ");
            response.type("application/json");
            return gson.toJson(xkcd.searchByTitle(title));
        }));
        get("/xkcd/search/:params", ((request, response) -> {
            String params = request.params(":params").replaceAll("%20", " ");
            response.type("application/json");
            return gson.toJson(xkcd.searchAll(params));
        }));
    }
}
