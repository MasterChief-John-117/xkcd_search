package com.chef;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;

public class xkcdClient {
    public final String latestUrl = "https://xkcd.com/info.0.json";
    private final String cachePath = "comics.json";
    private List<Comic> fullCache = new ArrayList<>();
    public String getApiUrlFromNumber(int num)
    {
        return "https://xkcd.com/" + num + "/info.0.json";
    }

    public void sync() {
        System.out.println("Starting sync at " + new Date().toString());
        //Create the cache file if not exist, else just load it
        File cacheFile = new File(cachePath);
        try {
            cacheFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Read the current json from the file
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(cacheFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String line = null;
        try
        {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch(Exception ex)
        {ex.printStackTrace();}
        String rawCache = sb.toString();

        //If it's emtpy
        if (rawCache.length() < 2) {
            fullCache = new ArrayList<Comic>();
        }
        else
        {
            fullCache = gson.fromJson(rawCache, new TypeToken<ArrayList<Comic>>(){}.getType());
        }

        int next;
        if (fullCache.isEmpty())
        {
            next = 1;
        }
        else
        {
            next = fullCache.size() + 1;
        }

        int addedCount = 0;
        Comic lastComic = new Comic(latestUrl);
        System.out.println("Latest comic is " + lastComic.num + ", last stored is " + (fullCache.size()));
        for(int i = next; i <= lastComic.num; i++)
        {
            if(i == 404) continue;
            System.out.println("Adding comic " + i);
            fullCache.add(new Comic(getApiUrlFromNumber(i)));
            addedCount++;
        }

        try (PrintWriter out = new PrintWriter(cacheFile.getName())) {
            out.println(gson.toJson(fullCache));
            System.out.println("Sync finished at " + new Date() + "\nAdded " + addedCount + " comics");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void saveDb()
    {
        try (PrintWriter out = new PrintWriter(cachePath)) {
            out.println(new GsonBuilder().setPrettyPrinting().create().toJson(fullCache));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public List<Comic> searchByTitle(String title)
    {
        List<Comic> cont = new ArrayList<>();
        for (Comic c : fullCache)
        {
            if(c.title != null && c.title.toLowerCase().contains(title.toLowerCase()))
            {
                cont.add(c);
            }
        }
        return cont;
    }
}
