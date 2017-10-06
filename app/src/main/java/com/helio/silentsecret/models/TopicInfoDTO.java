package com.helio.silentsecret.models;

import android.graphics.Bitmap;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class TopicInfoDTO {






    public String getTopic_title() {
        return topic_title;
    }



    public String getRoom_id() {
        return room_id;
    }


    public Bitmap getTopic_image() {
        return topic_image;
    }

    Bitmap topic_image ;
    String room_id = "";
    String topic_id  = "";

    String topic_title = "";

    public String getTopic_description() {
        return topic_description;
    }


    public String getTopic_id() {
        return topic_id;
    }

    String topic_description = "";

    public String getFirst_name() {
        return first_name;
    }

    String first_name = "";

    public String getTopic_format() {
        return topic_format;
    }

    String topic_format = "";

    public String getTopic_image_url() {
        return topic_image_url;
    }

    String topic_image_url = "";

    public TopicInfoDTO(String topic_title,String topic_image_url , Bitmap topic_image , String room_id , String topic_description , String topic_id , String first_name , String topic_format)
    {
        super();

        this.topic_title = topic_title;
        this.topic_image = topic_image;
        this.room_id = room_id;
        this.topic_description = topic_description;
        this.topic_id = topic_id;
        this.first_name = first_name;
        this.topic_format  = topic_format;
        this.topic_image_url = topic_image_url;

    }
}
