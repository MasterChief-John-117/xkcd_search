package com.chef;

public class xkcdClient {
    public final String latestUrl = "https://xkcd.com/info.0.json";
    public String getApiUrlFromNumber(int num)
    {
        return "https://xkcd.com/" + num + "/info.0.json";
    }
}
