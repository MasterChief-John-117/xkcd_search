package com.chef;

import java.util.Date;

public class Tag
{
    public String text;
    public String clientIp;
    public double puid;
    public Date AddedAt;

    public Tag(String t, String ip)
    {
        this.text = t;
        this.clientIp = ip;
        this.AddedAt = new Date();
        this.puid = Math.random() * Double.MAX_VALUE;
    }
}
