package com.helio.silentsecret.models;

import android.graphics.Bitmap;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class FlickerImagload {


    public String getFliker_image_url() {
        return fliker_image_url;
    }

    public void setFliker_image_url(String fliker_image_url) {
        this.fliker_image_url = fliker_image_url;
    }

    public Bitmap getFliker_image_bitmap() {
        return fliker_image_bitmap;
    }

    public void setFliker_image_bitmap(Bitmap fliker_image_bitmap) {
        this.fliker_image_bitmap = fliker_image_bitmap;
    }

    String fliker_image_url = "";
     Bitmap fliker_image_bitmap = null;




    public FlickerImagload(String fliker_image_url , Bitmap fliker_image_bitmap)
    {
        super();

        this.fliker_image_url = fliker_image_url;
        this.fliker_image_bitmap = fliker_image_bitmap;



    }
}
