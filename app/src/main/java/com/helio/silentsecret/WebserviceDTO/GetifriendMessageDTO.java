package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class GetifriendMessageDTO {



    String sender = "";
    String receiver = "";

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    String datetime = "";

    public GetifriendMessageDTO(String sender , String receiver  , String datetime)
    {
        super();

        this.sender = sender;
        this.receiver = receiver;
        this.datetime = datetime;

    }
}
