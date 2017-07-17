package com.helio.silentsecret.appCounsellingDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class BookAppointmentDataDTO {


    String clun01 = "",device_type = "",apntmnt_notification = "", status = "", mode = "", request_state = "";

 String usr_come_at = "",cnslr_come_at = "",cnslr_ratting_status = "",mood_graph = "", same_cnslr = "",apntmnt_date = "";
          String qid = "", agnc_unq_id = "", session_left = "", apntmnt_id ="", lat = "",  lng = "", suggested_by = "", suggestion_noti = "";

String counc_unq_id = "", clcnslrun01 = "",agent_unq_id = "",user_firstname = "",appointment_hours = "", appointment_day;
    int session_duration = 0;
    boolean impact_question = false;
    String counsellor_firstname = "";









    public BookAppointmentDataDTO(String clun01, String mode, String same_cnslr, String apntmnt_date, String qid, String agnc_unq_id
    ,String session_left, String apntmnt_id, String lat, String lng, String suggested_by, String suggestion_noti, String counc_unq_id, String clcnslrun01,
                                  int session_duration,String agent_unq_id, String user_firstname , String appointment_hours , String counsellor_firstname , String appointment_day)
    {
        super();
        this.clun01 = clun01;
        this.device_type = "android";
        this.apntmnt_notification = "false";
        this.status = "Pending";
        this.mode = mode;
        this.request_state = "Processed";
        this.cnslr_ratting_status = "Pending";
        this.mood_graph = "false";
        this.same_cnslr = same_cnslr;
        this.apntmnt_date = apntmnt_date;
        this.qid = qid;
        this.agnc_unq_id = agnc_unq_id;
        this.session_left= session_left;
        this.apntmnt_id = apntmnt_id;
        this.lat = lat;
        this.lng = lng;
        this.suggested_by = suggested_by;
        this.suggestion_noti = suggestion_noti;
        this.counc_unq_id = counc_unq_id;
        this.clcnslrun01 = clcnslrun01;
        this.session_duration = session_duration;
        this.impact_question = false;
        this.agent_unq_id = agent_unq_id;
        this.user_firstname = user_firstname;
        this.appointment_hours = appointment_hours;
        this.counsellor_firstname = counsellor_firstname;
        this.appointment_day =appointment_day;
    }
}
