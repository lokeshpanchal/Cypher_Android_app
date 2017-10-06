package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class RoomCommentConditionDTO {






    String rm_unq_id = "";
    boolean is_flagged = false;
    String cmnttext01 = "";
    String risk_word = "";
    boolean is_reply = false;
    String reply_cmnt_unq_id = "";
    String reply_clun01 = "";
    String clun01 = "";
    String room_clun01 = "";
    String petname = "";
    String comment_id = "";
    String risk_state = "";


    public RoomCommentConditionDTO(String rm_unq_id ,  String clun01, boolean flags
   , String  reply_to , boolean is_reply, String cmnttext01, String risk_state , String comment_id , String risk_word
            , String pet_name, String reply_to_commentid, String clcbuser01)
    {
        super();
        this.rm_unq_id = rm_unq_id;
        this.clun01 = clun01;
        this.cmnttext01 = cmnttext01;
        this.is_reply = is_reply;
        this.reply_clun01 = reply_to;
        this.comment_id = comment_id;
        this.is_flagged = flags;
        this.risk_state = risk_state;
        this.risk_word = risk_word;
        this.petname = pet_name;
        this.reply_cmnt_unq_id = reply_to_commentid;
        this.room_clun01 = clcbuser01;






    }
}
