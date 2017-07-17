package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 10/10/2016.
 */
public class DoowappDTO {

    String title = "";
    String artist_name = "";
    String image_url = "";

    public String getPages() {
        return pages;
    }

    String pages = "";

    public String getPlay_id() {
        return play_id;
    }

    String play_id = "";

    public String getlyrics() {
        return lyrics;
    }

    String lyrics;

    public DoowappDTO(String play_id,String   title , String lyrics,String artist_name,String pages,String image_url)
    {
        super();


        this.title = title;
        this.play_id = play_id;
        this.image_url = image_url;
        this.artist_name = artist_name;
        this.lyrics = lyrics;
        this.pages = pages;


    }

    public String getTitle() {
        return title;
    }

    public String getartist_name() {
        return artist_name;
    }



    public String getimage_url() {
        return image_url;
    }

}
