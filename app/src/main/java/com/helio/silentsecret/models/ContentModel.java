package com.helio.silentsecret.models;

/**
 * Created by gturedi on 8.02.2017.
 */
public class ContentModel
        extends BaseModel {

    @SuppressWarnings("membername")
    public String _content; // NOPMD

    @Override
    public String toString() {
        return _content;
    }

}