package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetNotificationReadDataDTO
{
    String notification_id = "";
    public SetNotificationReadDataDTO(String username)
    {
        super();
        this.notification_id = username;
    }
}
