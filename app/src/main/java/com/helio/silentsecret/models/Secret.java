package com.helio.silentsecret.models;


import java.util.ArrayList;
import java.util.Date;

public class Secret {

    private String objectId;
    private String createdAt;
    private String updatedAt;
    private String address;
    private String age;
    private String bgImageName;
    private String category;
    private String createdByUser;
    private Date date;
    private String feel;
    private ArrayList<String> flagUsers;
    private int flags = 0;
    private String gender;
    private boolean hasNotification;
    private String hashtags;
    private ArrayList<String> heartUsers;
    private int hearts = 0;
    private String home;
    private ArrayList<String> hugUsers;
    private int hugs = 0;

    public String getFlicker_image() {
        return flicker_image;
    }

    public void setFlicker_image(String flicker_image) {
        this.flicker_image = flicker_image;
    }

    private String flicker_image;
    private String latLon;
    private int likes = 0;
    private ArrayList<String> me2Users;
    private int me2s = 0;
    private int notificationSent = 0;
    private boolean readNotification;
    private String riskState;
    private String stressLevel;
    private ArrayList<String> supportOrganisations;
    private String text;

    public String getSkin_pattern() {
        return skin_pattern;
    }

    public void setSkin_pattern(String skin_pattern) {
        this.skin_pattern = skin_pattern;
    }

    private  String skin_pattern = "";
    private int wms = 0;
    private boolean isFlagged;

    private boolean isDeleted;
    private Date createdDate;
    private int font = 1;
    private boolean isMutual;
    private  int showcoupon = -1;


    public int getShowCoupon() {
        return showcoupon;
    }

    public void setShowCoupon(int showcoupon) {
         this.showcoupon = showcoupon;
    }


    public String getDoowapplink() {
        return doowapplink;
    }

    public void setDoowapplink(String doowapplink) {
        this.doowapplink = doowapplink;
    }

    private String doowapplink;

    public String getDoowapplyrics() {
        return doowapplyrics;
    }

    public void setDoowapplyrics(String doowapplyrics) {
        this.doowapplyrics = doowapplyrics;
    }

    private String doowapplyrics;

    public String getDoowappartistname() {
        return doowappartistname;
    }

    public void setDoowappartistname(String doowappartistname) {
        this.doowappartistname = doowappartistname;
    }

    private String doowappartistname;

    public String getDoowappmtrackname() {
        return doowappmtrackname;
    }

    public void setDoowappmtrackname(String doowappmtrackname) {
        this.doowappmtrackname = doowappmtrackname;
    }

    private String doowappmtrackname;

    public String getDoowappimageurl() {
        return doowappimageurl;
    }

    public void setDoowappimageurl(String doowappimageurl) {
        this.doowappimageurl = doowappimageurl;
    }

    private String doowappimageurl;

    public Secret() {
    }
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getComment_count() {
        return comment_count;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getShowcoupon() {
        return showcoupon;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setMutual(boolean mutual) {
        isMutual = mutual;
    }

    public void setShowcoupon(int showcoupon) {
        this.showcoupon = showcoupon;
    }

    String comment_count = "";
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBgImageName() {
        return bgImageName;
    }

    public void setBgImageName(String bgImageName) {
        this.bgImageName = bgImageName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public ArrayList<String> getFlagUsers() {
        return flagUsers;
    }

    public void setFlagUsers(ArrayList<String> flagUsers) {
        this.flagUsers = flagUsers;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isHasNotification() {
        return hasNotification;
    }

    public void setHasNotification(boolean hasNotification) {
        this.hasNotification = hasNotification;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    public ArrayList<String> getHeartUsers() {
        return heartUsers;
    }

    public void setHeartUsers(ArrayList<String> heartUsers) {
        this.heartUsers = heartUsers;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public ArrayList<String> getHugUsers() {
        return hugUsers;
    }

    public void setHugUsers(ArrayList<String> hugUsers) {
        this.hugUsers = hugUsers;
    }

    public int getHugs() {
        return hugs;
    }

    public void setHugs(int hugs) {
        this.hugs = hugs;
    }



    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<String> getMe2Users() {
        return me2Users;
    }

    public void setMe2Users(ArrayList<String> me2Users) {
        this.me2Users = me2Users;
    }

    public int getMe2s() {
        return me2s;
    }

    public void setMe2s(int me2s) {
        this.me2s = me2s;
    }

    public int getNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(int notificationSent) {
        this.notificationSent = notificationSent;
    }

    public boolean isReadNotification() {
        return readNotification;
    }

    public void setReadNotification(boolean readNotification) {
        this.readNotification = readNotification;
    }

    public String getRiskState() {
        return riskState;
    }

    public void setRiskState(String riskState) {
        this.riskState = riskState;
    }

    public String getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(String stressLevel) {
        this.stressLevel = stressLevel;
    }

    public ArrayList<String> getSupportOrganisations() {
        return supportOrganisations;
    }

    public void setSupportOrganisations(ArrayList<String> supportOrganisations) {
        this.supportOrganisations = supportOrganisations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public int getWms() {
        return wms;
    }

    public void setWms(int wms) {
        this.wms = wms;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }







    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public boolean isMutual() {
        return isMutual;
    }

    public void setIsMutual(boolean isMutual) {
        this.isMutual = isMutual;
    }
}