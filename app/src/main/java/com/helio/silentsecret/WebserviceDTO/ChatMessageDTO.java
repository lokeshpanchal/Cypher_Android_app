package com.helio.silentsecret.WebserviceDTO;

import java.util.Date;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ChatMessageDTO {



    String receiver = "";


    String sender = "";
    String risk_word;
    String message = "";
    String flags = "";
    String flag_risk = "";
    String read;

    public Date getCurrenttime() {
        return currenttime;
    }

    Date currenttime= null;

    public Date getCreated_at() {
        return created_at;
    }

    public String getSender() {
        return sender;
    }

    public String getRisk_word() {
        return risk_word;
    }

    public String getMessage() {
        return message;
    }

    public String getFlags() {
        return flags;
    }

    public String getFlag_risk() {
        return flag_risk;
    }

    public String getRead() {
        return read;
    }

    public String getReceiver() {
        return receiver;
    }

    Date created_at ;

    public ChatMessageDTO(String sender , String receiver, String risk_word, String message, String flags, String flag_risk, String read, Date created_at, Date currenttime)
    {
        super();


        this.sender = sender;
        this.receiver = receiver;
        this.risk_word = risk_word;
        this.message = message;
        this.flags = flags;
        this.flag_risk = flag_risk;
        this.read = read;
        this.created_at = created_at;
        this.currenttime = currenttime;


    }
}
