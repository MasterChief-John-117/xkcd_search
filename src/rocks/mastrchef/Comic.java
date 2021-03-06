package rocks.mastrchef;

import java.net.URL;
import java.util.*;
import com.google.gson.Gson;

public class Comic
{
    public int num;
    public String title;
    public String safe_title;
    public String alt;
    public String img;
    public String transcript;
    public String year;
    public String month;
    public String day;
    public HashSet<Tag> tags;
    public Comic(String Url)
    {
        try
        {
            //Download json from provided URL
            URL url = new URL(Url);
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder sb = new StringBuilder();
            while(scanner.hasNextLine())
            {
                sb.append(scanner.nextLine());
            }
            String jsonValue = sb.toString();

            //Build the comic from downloaded string
            Gson gson = new Gson();
            Comic newC = gson.fromJson(jsonValue, Comic.class);

            this.num = newC.num;
            this.title = newC.title;
            this.safe_title = newC.safe_title;
            this.alt = newC.alt;
            this.transcript = newC.transcript;
            this.img = newC.img;
            this.year = newC.year;
            this.month = newC.month;
            this.day = newC.day;
            this.tags = new HashSet<Tag>(){};
        }
        catch(Exception IOEx)
        {IOEx.printStackTrace();}
    }
}
