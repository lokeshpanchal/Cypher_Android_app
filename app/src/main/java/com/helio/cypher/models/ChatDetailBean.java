package com.helio.cypher.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class ChatDetailBean {


    public String myMsg = "";
    public String yourMsg = "";
    public boolean isMine = false;
    public String sentTime = "";
    public String receiveTime = "";


    public String getDoowappmUrl() {
        return doowappmUrl;
    }

    public void setDoowappmUrl(String doowappmUrl) {
        this.doowappmUrl = doowappmUrl;
    }

    public  String doowappmUrl = "";

    public String getDoowapplyrics() {
        return doowapplyrics;
    }

    public void setDoowapplyrics(String doowapplyrics) {
        this.doowapplyrics = doowapplyrics;
    }

    public  String doowapplyrics = "";

    public String getDoowappartistname() {
        return doowappartistname;
    }

    public void setDoowappartistname(String doowappartistname) {
        this.doowappartistname = doowappartistname;
    }

    public  String doowappartistname = "";

    public String getDoowappmtrackname() {
        return doowappmtrackname;
    }

    public void setDoowappmtrackname(String doowappmtrackname) {
        this.doowappmtrackname = doowappmtrackname;
    }

    public  String doowappmtrackname = "";

    public String getDoowappimageurl() {
        return doowappimageurl;
    }

    public void setDoowappimageurl(String doowappimageurl) {
        this.doowappimageurl = doowappimageurl;
    }

    public  String doowappimageurl = "";



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
