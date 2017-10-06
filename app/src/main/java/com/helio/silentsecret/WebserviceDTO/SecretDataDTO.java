package com.helio.silentsecret.WebserviceDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SecretDataDTO {



String  age = "";
    String gender = "";
    String latlong = "";

    String user_location = "";
    String clcbuser01 = "";

    String secret_id = "";

    String hugs = "";
    String secret_notification  = "";
    String category = "";
    List tag_friend ;
    String flags = "";
    String feel = "";
    String hearts = "";
    String home = "";
    String risk_state = "";
    int font ;
    String wms = "";
    String bg_image_name = "";
    String cltxt01 = "";
    String tagfriend_notification = "";
    String cladd01 = "";
    String me2s = "";
    String comment_count ="";
    String risk_word = "";
    String flicker_image ="";
    int secrets  = 0;
    String school_id = "";
    String school_code = "";
    String clstxt01 = "";
    boolean is_updated = true;
    List<String> school_users = null;
    String skin_pattern = "";

    public SecretDataDTO(String secret_id ,String username , String gender , String age,String cladd01, String category, List tag_friend, String flags, String feel
   ,int font ,String bg_image_name, String cltxt01,String risk_state ,int secrets,String risk_word,List<String> users,
                         String school_id, String school_code, String clstxt01, String  flicker_image , String skin_pattern)
    {
        super();
        this.secret_id = secret_id;
        this.hugs = "0";
        this.secret_notification = "false";
        this.category = category;
        this.tag_friend = tag_friend;
        this.flags = flags;
        this.feel = feel;
        this.hearts = "0";
        this.home = "0";
        this.font = font;
        this.wms = "0";
        this.bg_image_name = bg_image_name;
        this.cltxt01 = cltxt01;
        this.tagfriend_notification = "false";
        this.age = age;
        this.gender = gender;
        this.clcbuser01 = username;
        this.cladd01 = cladd01;
        me2s = "0";
        this.risk_state = risk_state;
        this.secrets  = secrets;
        this.comment_count = "0";
        this.risk_word = risk_word;
        this.school_users = users;
        this.school_id = school_id;
        this.school_code = school_code;
        this.clstxt01 = clstxt01;
        this.is_updated = true;
        this.flicker_image = flicker_image;
        this.skin_pattern = skin_pattern;
    }
}
