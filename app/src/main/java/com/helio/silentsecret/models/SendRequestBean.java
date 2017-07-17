package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 8/10/2016.
 */
public class SendRequestBean {


    public String getSendIFriend() {
        return sendIFriend;
    }

    public void setSendIFriend(String sendIFriend) {
        this.sendIFriend = sendIFriend;
    }

    public String sendIFriend = "";

    public String getTarget() {

        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String target = "";
    public String sender = "";
}
