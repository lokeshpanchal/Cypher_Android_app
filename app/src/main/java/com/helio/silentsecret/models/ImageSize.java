package com.helio.silentsecret.models;

/**
 * Created by gturedi on 10.02.2017.
 */
public enum ImageSize {
    SMALL,
    MEDIUM,
    LARGE;

    public String getValue()
    {
        if (this == MEDIUM)
            return "z";
        else if (this == SMALL)
        return "s";

        return "h";
    }

}