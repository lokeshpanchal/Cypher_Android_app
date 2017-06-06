package com.helio.cypher.WebserviceDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class CommentConditionDTO {






    String secret_id = "";
    List tag_ifriend ;
    boolean flagged = false;

    String risk_word = "";
    List<String> seen_by = null;
    boolean is_reply = false;
    String cltxt01 = "";
    String reply_to = "";
    String clun01 = "";
    String pet_name = "";
    String comment_id = "";
    String risk_state = "";
    int comment_count = 0;
String total_scratch_count = "";
String reply_to_commentid = "";
    String clcbuser01 = "";
    String  m2_water = "";
    String pn_timing = "";
    public CommentConditionDTO(String secret_id , int comment_count ,String clun01,  List tag_friend, boolean flags
   , String  reply_to , boolean is_reply, String cltxt01, String risk_state , String comment_id ,String risk_word,String m2_water
            , String pet_name, List<String> seen_by, String reply_to_commentid, String clcbuser01, String total_scratch_count, String pn_timing)
    {
        super();
        this.secret_id = secret_id;
        this.clun01 = clun01;
        this.cltxt01 = cltxt01;
        this.tag_ifriend = tag_friend;
        this.is_reply = is_reply;
        this.reply_to = reply_to;
        this.comment_id = comment_id;
        this.flagged = flags;
        this.risk_state = risk_state;
        this.risk_word = risk_word;
        this.comment_count = comment_count;
        this.m2_water = m2_water;
        this.pet_name = pet_name;
        this.seen_by = seen_by;
        this.reply_to_commentid = reply_to_commentid;
        this.clcbuser01 = clcbuser01;
        this.total_scratch_count = total_scratch_count;
        this.pn_timing = pn_timing;




    }
}
