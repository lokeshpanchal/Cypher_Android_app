package com.helio.silentsecret.WebserviceDTO;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetPetConditionDTO {


    String clun01 = "";
    String myvirtual_pet = "";


    public SetPetConditionDTO(String username, String petname) {
        super();

        this.clun01 = username;
        this.myvirtual_pet = petname;

    }
}
