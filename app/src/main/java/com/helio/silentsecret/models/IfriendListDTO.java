package com.helio.silentsecret.models;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class IfriendListDTO {




     String username = "";
     String friend = "";
     String possition = "";

     String secretpn = "";
     String requestid = "";
     int profilepic = 0;

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getFriend() {
        return friend;
    }

    public String getPossition() {
        return possition;
    }

    public String getSecretpn() {
        return secretpn;
    }

    public String getRequestid() {
        return requestid;
    }

    public int getProfilepic() {
        return profilepic;
    }

    String status = "";

    public IfriendListDTO(String username,String friend,String possition,String secretpn, String requestid
    ,int profilepic, String status)
    {
        super();

         this.username = username;
         this.friend = friend;
         this.possition = possition;

         this.secretpn = secretpn;
         this.requestid = requestid;
         this.profilepic = profilepic;
         this.status = status;
    }
}
