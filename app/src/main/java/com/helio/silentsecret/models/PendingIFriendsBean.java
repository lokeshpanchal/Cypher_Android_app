package com.helio.silentsecret.models;

import java.util.Date;

/**
 * Created by Maroof Ahmed Siddique on 8/5/2016.
 */
public class PendingIFriendsBean {
    public boolean isRequestedByMe() {
        return isRequestedByMe;
    }

    public void setRequestedByMe(boolean requestedByMe) {
        isRequestedByMe = requestedByMe;
    }

    public boolean isRequestedByMe = false;

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }




    public String getPendingIFriendName() {
        return pendingIFriendName;
    }

    public void setPendingIFriendName(String pendingIFriendName) {
        this.pendingIFriendName = pendingIFriendName;
    }

    public double getLatitudeSend() {
        return latitudeSend;
    }

    public double setLatitudeSend(double latitudeSend) {
        this.latitudeSend = latitudeSend;
        return latitudeSend;
    }

    public double getLongitudeSend() {
        return longitudeSend;
    }

    public double setLongitudeSend(double longitudeSend) {
        this.longitudeSend = longitudeSend;
        return longitudeSend;
    }

    private double latitudeSend;
    private double longitudeSend;
    public  int rowType = 0;

    public String target = "";

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String requestid = "";


    public String sender = "";
    public String sendDate = "";
    public String pendingDate = "";

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String createdDate = "";

    public Date date ;
    public String pendingIFriendName = "";

    public String sendIFriendName = "";
    public String objectId = "";

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


    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }


    public String getPendingDate() {
        return pendingDate;
    }

    public void setPendingDate(String pendingDate) {
        this.pendingDate = pendingDate;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }




    public String getSendIFriendName() {
        return sendIFriendName;
    }

    public void setSendIFriendName(String sendIFriendName) {
        this.sendIFriendName = sendIFriendName;
    }



}
