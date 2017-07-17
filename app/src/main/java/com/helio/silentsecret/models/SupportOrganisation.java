package com.helio.silentsecret.models;

import java.util.List;

public class SupportOrganisation {
    private List<String> location;
    private String name;
    private String thumb;
    private List<String> triggerWords;
    private List<String> triggers;
    private String url;
    private String objectId;
    private String phoneNo;
    private int clicks;
    private List<String> usersClicked;
    private List<String> userRated;
    private String unique_id;

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public List<String> getTriggerWords() {
        return triggerWords;
    }

    public void setTriggerWords(List<String> triggerWords) {
        this.triggerWords = triggerWords;
    }

    public List<String> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<String> triggers) {
        this.triggers = triggers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public List<String> getUserRated() {
        return userRated;
    }

    public void setUserRated(List<String> userRated) {
        this.userRated = userRated;
    }

    public List<String> getUsersClicked() {
        return usersClicked;
    }

    public void setUsersClicked(List<String> usersClicked) {
        this.usersClicked = usersClicked;
    }

    public String getOrg() {
        return unique_id;
    }

    public void setOrgId(String unique_id) {
        this.unique_id = unique_id;
    }
}
