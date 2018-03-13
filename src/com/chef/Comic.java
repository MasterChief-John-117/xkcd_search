package com.chef;

import java.io.IOException;
import java.net.*;
import java.util.*;
import com.google.gson.*;

public class Comic
{
    public int num;
    public String title;
    public String safe_title;
    public String alt;
    public String img;
    public String year;
    public String month;
    public String day;

    public Comic(String Url)
    {
        try
        {
            URL url = new URL(Url);
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder sb = new StringBuilder();
            while(scanner.hasNextLine())
            {
                sb.append(scanner.nextLine());
            }
            String jsonValue = sb.toString();
            
            Gson gson = new Gson();
            Comic newC = gson.fromJson(jsonValue, Comic.class);

            this.num = newC.num;
            this.title = newC.title;
            this.safe_title = newC.safe_title;
            this.alt = newC.alt;
            this.img = newC.img;
            this.year = newC.year;
            this.month = newC.month;
            this.day = newC.day;
        }
        catch(IOException IOEx)
        {IOEx.printStackTrace();}
    }
}
