package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SendImpactRatingDTO {


    String appointmentid = "";
    String counsellorid = "";
    String username = "";
    String question = "";
    String questionid = "";
    String rating = "";

    public String getQuestion (){
        return question;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }

    public void setCounsellorid(String counsellorid) {
        this.counsellorid = counsellorid;
    }

    public void setUserid(String userid) {
        this.username = userid;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestion_id(String question_id) {
        this.questionid = question_id;
    }

    public void setRating(String rating) {
        this.rating =  rating;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public String getCounsellorid() {
        return counsellorid;
    }

    public String getUserid() {
        return username;
    }



    public String getQuestion_id() {
        return questionid;
    }



    public SendImpactRatingDTO(String appointmentid, String counsellorid, String userid, String question,
                               String question_id, String rating)
    {
        super();


        this.appointmentid = appointmentid;
        this.counsellorid = counsellorid;
        this.username = userid;
        this.question =question;
        this.questionid = question_id;
        this.rating = rating;
    }
}
