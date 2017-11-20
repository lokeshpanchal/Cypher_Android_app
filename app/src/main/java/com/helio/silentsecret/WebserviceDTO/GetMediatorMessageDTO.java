package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetMediatorMessageDTO {



    String clun01 = "";
    String send_by = "";
    String clmediatorun01 = "";

    public String getDatetime() {
        return lastUpdateAt;
    }

    public void setDatetime(String datetime) {
        this.lastUpdateAt = datetime;
    }

    String lastUpdateAt = "";

    public GetMediatorMessageDTO(String clun01 , String clmediatorun01  , String datetime)
    {
        super();

        this.clun01 = clun01;
        this.clmediatorun01 = clmediatorun01;
        this.lastUpdateAt = datetime;
        this.send_by = "mediator";

    }
}
