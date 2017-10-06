package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class Me2HugLikeRoomDTO {


    String clun01 = "";
     String rm_unq_id = "";
    String  action = "";
    String opposite_action = "";
    public Me2HugLikeRoomDTO(String username , String rm_unq_id, String action , String opposite_action)
    {
        super();

        this.clun01 = username;
        this.rm_unq_id = rm_unq_id;
        this.action = action;
        this.opposite_action = opposite_action;

    }
}
