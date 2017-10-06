package com.helio.silentsecret.WebserviceDTO;

import java.util.ArrayList;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class Me2HugLikeConditionalDTO {


    String clun01 = "";
     String secret_id = "";
    String  clcbuser01 = "";
    int virtual_comments_count = 0;
    String  hug_oxygen = "";
    String  heart_food = "";
    String scratch_count = "";
    String total_scratch_count = "";
    ArrayList<String> users_users;
    ArrayList<String> users_secrets ;

    int count_users ;

    int count_secrets ;
String is_send_push = "";
String short_sentence = "";
    public Me2HugLikeConditionalDTO(String username , String secret_id, String clcbuser01, ArrayList<String> users_users,
                                    ArrayList<String> users_secrets, int count_users, int count_secrets, String is_send_push,
                                    int virtual_comments_count, String ug_oxygen,String heart_food,String scratch_count, String total_scratch_count , String short_sentence)
    {
        super();

        this.clun01 = username;
        this.secret_id = secret_id;
        this.clcbuser01 = clcbuser01;
        this.users_users = users_users;
        this.users_secrets = users_secrets;
        this.count_users = count_users;
        this.count_secrets = count_secrets;
        this.is_send_push = is_send_push;
        this.virtual_comments_count = virtual_comments_count;

        this.hug_oxygen = ug_oxygen;
        this.heart_food = heart_food;
        this.scratch_count = scratch_count;
        this.total_scratch_count = total_scratch_count;
        this.short_sentence = short_sentence;

    }
}
