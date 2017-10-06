package com.helio.silentsecret.WebserviceDTO;

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
    String safeguarding = "" , guest_clun01 = "";
     int secrets  = 0;

String daddress = "",dlatlong = "", code = "",codetype = "";
    public RegistrationDataDTO(String username , String pin,String security_question,String security_answer, String gender,
                               String cladd01, String latLon,String age, String imei_number , String safeguarding,
                               String daddress,String dlatlong ,String code,String codetype , String guest_clun01 )
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
        this.daddress = daddress;
        this.dlatlong = dlatlong;
        this.code = code;
        this.codetype = codetype;
        this.guest_clun01 = guest_clun01;



    }
}
