package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ChatDetailBean {


    public String myMsg = "";
    public String yourMsg = "";
    public boolean isMine = false;
    public String sentTime = "";
    public String receiveTime = "";

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean seen = false;
    public String getDoowappmUrl() {
        return doowappmUrl;
    }

    public void setDoowappmUrl(String doowappmUrl) {
        this.doowappmUrl = doowappmUrl;
    }

    public  String doowappmUrl = "";



    public String getMyMsg() {
        return myMsg;
    }

    public void setMyMsg(String myMsg) {
        this.myMsg = myMsg;
    }

    public String getYourMsg() {
        return yourMsg;
    }

    public void setYourMsg(String yourMsg) {
        this.yourMsg = yourMsg;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }


}
