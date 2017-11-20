package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendMediatorChatDataDTO {




    String clmediatorun01 = "";
    String clun01 = "";
    String risk_word;
    String message = "";
    String flags = "";
    String flag_risk = "";
    String unique_id = "";
    String send_by = "";

    boolean read;


    public SendMediatorChatDataDTO(String sender , String receiver, String risk_word, String message, String flags, String flag_risk, boolean read,
                                   String unique_id , String send_by)
    {
        super();


        this.clun01 = sender;
        this.clmediatorun01 = receiver;
        this.risk_word = risk_word;
        this.message = message;
        this.flags = flags;
        this.flag_risk = flag_risk;
        this.read = read;
        this.unique_id = unique_id;
        this.send_by = send_by;

    }
}
