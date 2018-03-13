package com.chef;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

class Main
{
    public static final xkcdClient xkcd = new xkcdClient();

    public static void main(String[] args) {
        xkcd.sync();
    }
}
