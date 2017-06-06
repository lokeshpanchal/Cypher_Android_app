package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class RegistrationDataDTO {


     String security_question = "";
     String security_answer = "";
String  age = "";
    String gender = "";
    String latlong = "";

    String user_location = "";
    String clun01 = "";
    String pin = "";
    String imei_number = "";
    String is_verified = "";
    String is_flaged = "";
    boolean disclaimer = true;
    String safeguarding = "";
     int secrets  = 0;


    public RegistrationDataDTO(String username , String pin,String security_question,String security_answer, String gender,
                               String cladd01, String latLon,String age, String imei_number , String safeguarding)
    {
        super();


        this.clun01 = username;
        this.pin = pin;
        this.security_question = security_question;
        this.security_answer = security_answer;
        this.gender = gender;
        this.user_location = cladd01;
        this.latlong = latLon;
        this.age = age;
        this.is_verified = "false";
        this.is_flaged = "false";
        this.secrets = 0;
        this.imei_number = imei_number;
        this.disclaimer = true;
        this.safeguarding = safeguarding;

    }
}
