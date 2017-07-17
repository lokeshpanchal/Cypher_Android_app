package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendMessagetofriedDataDTO {


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRisk_word() {
        return risk_word;
    }

    public void setRisk_word(String risk_word) {
        this.risk_word = risk_word;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getFlag_risk() {
        return flag_risk;
    }

    public void setFlag_risk(String flag_risk) {
        this.flag_risk = flag_risk;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    String receiver = "";
    String sender = "";
    String risk_word;
    String message = "";
    String flags = "";
    String flag_risk = "";
    String unique_id = "";

    boolean read;


    public SendMessagetofriedDataDTO( String sender ,String receiver, String risk_word, String message, String flags, String flag_risk, boolean read, String unique_id)
    {
        super();


        this.sender = sender;
        this.receiver = receiver;
        this.risk_word = risk_word;
        this.message = message;
        this.flags = flags;
        this.flag_risk = flag_risk;
        this.read = read;
        this.unique_id = unique_id;

    }
}
