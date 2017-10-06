package com.helio.silentsecret.models;

/**
 * Created by gturedi on 7.02.2017.
 */
public class DisplaySizes  extends BaseModel {


    public void setUri(String uri) {
        this.uri = uri;
    }

    public String uri;



    public String getImageUrl() {
        return uri;
    }

}