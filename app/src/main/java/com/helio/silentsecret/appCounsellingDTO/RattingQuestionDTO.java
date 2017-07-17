package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class RattingQuestionDTO {



     String counsellorid = "";
    String appointmentid = "";
    String  questionid = "";
    String userid = "";
    String rated_by = "";
    String questiontext ="";

    public String getUserid() {
        return userid;
    }

    public String getCounsellorid() {
        return counsellorid;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public String getQuestionid() {
        return questionid;
    }

    public String getRated_by() {
        return rated_by;
    }

    public String getQuestiontext() {
        return questiontext;
    }

    public int getRattingcount() {
        return rattingcount;
    }


    public void  SetRattingcount(int rating) {
         this.rattingcount = rating;
    }


    public String getComment() {
        return comment;
    }

    int rattingcount = 0;
    String comment = "";

    public RattingQuestionDTO(String counsellorid, String appointmentid, String questionid, String userid, String rated_by
    , String questiontext, int rattingcount, String comment)
    {
        super();

        this.counsellorid = counsellorid;
        this.appointmentid = appointmentid;
        this.questionid =questionid;
        this.userid = userid;
        this.rated_by = rated_by;
        this.questiontext = questiontext;
        this.rattingcount = rattingcount;
        this.comment = comment;


    }
}
