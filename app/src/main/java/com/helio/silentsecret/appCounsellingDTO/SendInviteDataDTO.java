package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendInviteDataDTO
{
    String clun01 = "";
String unique_id = "";

String name = "";
    String phone_number = "";

    public SendInviteDataDTO(String clun01, String phone_number , String name , String unique_id)
    {
        super();
        this.clun01 = clun01;
        this.unique_id = unique_id;
        this.phone_number = phone_number;
        this.name = name;
    }
}
