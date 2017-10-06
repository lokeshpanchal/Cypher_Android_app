package com.helio.silentsecret.models;

import java.util.List;

/**
 * Created by gturedi on 7.02.2017.
 */
public class PhotoModel
        extends BaseModel {

    // https://www.flickr.com/services/api/misc.urls.html
    // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
    private static final String IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";

    public long id;
    public String secret;
    public String server;
    public int farm;

    public List<DisplaySizes> getDisplay_sizes() {
        return display_sizes;
    }

    List<DisplaySizes> display_sizes = null;
    //public String owner;
    //public String title;

    public String getImageUrl(ImageSize size) {
        return String.format(IMAGE_URL, farm, server, id, secret, size.getValue());
    }

}