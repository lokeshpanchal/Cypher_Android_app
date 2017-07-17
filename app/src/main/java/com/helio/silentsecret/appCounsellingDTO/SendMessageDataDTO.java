package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendMessageDataDTO {


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


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    String receiver = "";
    String sender = "";
    String message = "";
    String appointmentid = "";

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    String unique_id = "";
    boolean read;
    String user_type = "counsellor";

    public SendMessageDataDTO(String sender , String receiver, String message, String appointmentid, String unique_id)
    {
        super();

        this.appointmentid = appointmentid;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.unique_id = unique_id;
        this.read = false;

    }
}
