package com.helio.silentsecret.models;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class RoomsInfoDTO {


     Bitmap room_image ;

    public void setMe2_users(List<String> me2_users) {
        this.me2_users = me2_users;
    }

    public void setHug_users(List<String> hug_users) {
        this.hug_users = hug_users;
    }

    public void setHeart_users(List<String> heart_users) {
        this.heart_users = heart_users;
    }

    List<String> me2_users ;
    List<String> hug_users ;
    List<String> heart_users ;


    public void setMe2_count(int me2_count) {
        this.me2_count = me2_count;
    }

    public void setHug_count(int hug_count) {
        this.hug_count = hug_count;
    }

    public void setHeart_count(int heart_count) {
        this.heart_count = heart_count;
    }

    int me2_count , hug_count, heart_count;

    public String getRoom_name() {
        return room_name;
    }

    public Bitmap getRoom_image() {
        return room_image;
    }

    public List<String> getMe2_users() {
        return me2_users;
    }

    public List<String> getHug_users() {
        return hug_users;
    }

    public List<String> getHeart_users() {
        return heart_users;
    }

    public int getMe2_count() {
        return me2_count;
    }

    public int getHug_count() {
        return hug_count;
    }

    public int getHeart_count() {
        return heart_count;
    }

    public String getRoom_id() {
        return room_id;
    }

    public String getCreated_user() {
        return created_user;
    }

    public String getSearch_text() {
        return search_text;
    }

    public List<String> getComment_users() {
        return comment_users;
    }

    public List<String> getJoin_users() {
        return join_users;
    }

    String room_name = "";
    String room_id = "";
    String created_user = "";
    String search_text = "";
    String first_name = "";
    List<String> comment_users ;
    List<String> join_users ;

    public String getRoom_code() {
        return room_code;
    }

    String room_code = "";
    public String getRoom_image_url() {
        return room_image_url;
    }

    String room_image_url = "";
    public String getFirst_name() {
        return first_name;
    }

    public RoomsInfoDTO(String room_name,String room_image_url, Bitmap room_image, List<String> me2_users, List<String> hug_users, List<String> heart_users, int me2_count,
                        int hug_count, int heart_count , String room_id , String created_user , String search_text , List<String> comment_users , List<String> join_users , String first_name , String room_code)
    {
        super();
        this.room_image = room_image;
        this.room_name = room_name;
        this.me2_users = me2_users;
        this.hug_users = hug_users;
        this.heart_users = heart_users;
        this.me2_count = me2_count;
        this.hug_count = hug_count;
        this.heart_count = heart_count;
        this.room_id = room_id;
        this.created_user =created_user;
        this.search_text = search_text;
        this.comment_users = comment_users;
        this.join_users = join_users;
        this.first_name = first_name;
        this.room_image_url = room_image_url;
        this.room_code = room_code;
    }
}
