package com.helio.silentsecret.models;



import java.util.Date;

public class RoomComment {


    private boolean flagged;
    private String room_id;
    private String text;
    private String created_by ;
    private String objectId;
    private Date createdAt;
    private boolean reply;
    private String comment_id;
    private String replyTo;

    public String getReply_cmnt_unq_id() {
        return reply_cmnt_unq_id;
    }

    public void setReply_cmnt_unq_id(String reply_cmnt_unq_id) {
        this.reply_cmnt_unq_id = reply_cmnt_unq_id;
    }

    private  String reply_cmnt_unq_id;
    public String getRoom_user() {
        return room_user;
    }

    public void setRoom_user(String room_user) {
        this.room_user = room_user;
    }

    private String room_user;



/*    public ParseUser getFlagUser() {
        return flagUser;
    }

    public void setFlagUser(ParseUser flagUser) {
        this.flagUser = flagUser;
    }*/

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public String getRoomid() {
        return room_id;
    }

    public void setSecretid(String secret) {
        this.room_id = secret;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return created_by;
    }

    public void setUser(String created_by) {
        this.created_by = created_by;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public String getCommentid() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String secret_id) {
        this.room_id = secret_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getComment_id() {
        return comment_id;
    }

    String pet_name = "";
}
