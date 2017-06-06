package com.helio.cypher.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetSecretNotificationReadDataDTO {

boolean read_notification ;

    String secret_id = "";
    public SetSecretNotificationReadDataDTO( String secret_id) {
        super();
        this.read_notification = true;
        this.secret_id = secret_id;
    }
}
