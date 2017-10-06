package com.helio.silentsecret.models;

import android.widget.ImageView;

/**
 * Created by Maroof Ahmed Siddique on 8/2/2016.
 */
public class IFriendsBean {

    public String getiFriendName() {
        return iFriendName;
    }

    public void setiFriendName(String iFriendName) {
        this.iFriendName = iFriendName;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public boolean isMe = false;

    public  String iFriendName  = "";

    public String getDummyName() {
        return dummyName;
    }

    public void setDummyName(String dummyName) {
        this.dummyName = dummyName;
    }

    public  String dummyName  = "";
    public String username = "";

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String friend = "";

    public ImageView getmFriendAvatar() {
        return mFriendAvatar;
    }

    public void setmFriendAvatar(ImageView mFriendAvatar) {
        this.mFriendAvatar = mFriendAvatar;
    }

    public ImageView mFriendAvatar;


    public int getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(int profilepic) {
        this.profilepic = profilepic;
    }

    public int profilepic = 0;

    public int getUnreadcount() {
        return unreadcount;
    }

    public void setUnreadcount(int unreadcount) {
        this.unreadcount = unreadcount;
    }

    public int unreadcount = 0;


    public String getMe2s() {
        return me2s;
    }

    public void setMe2s(String me2s) {
        this.me2s = me2s;
    }

    public String getHugs() {
        return hugs;
    }

    public void setHugs(String hugs) {
        this.hugs = hugs;
    }

    public String getHearts() {
        return hearts;
    }

    public void setHearts(String hearts) {
        this.hearts = hearts;
    }

    public String me2s = "";
    public String hugs = "";
    public String hearts = "";
}
