package com.helio.silentsecret.appCounsellingDTO;

import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class SetDaywiseAvailabilityDataDTO {

    public List<SetAvailabilityDataDTO> getMonday() {
        return Monday;
    }

    public List<SetAvailabilityDataDTO> getTuesday() {
        return Tuesday;
    }

    public List<SetAvailabilityDataDTO> getWednesday() {
        return Wednesday;
    }

    public List<SetAvailabilityDataDTO> getThursday() {
        return Thursday;
    }

    public List<SetAvailabilityDataDTO> getFriday() {
        return Friday;
    }

    public List<SetAvailabilityDataDTO> getSaturday() {
        return Saturday;
    }

    List< SetAvailabilityDataDTO>  Monday ;
    List< SetAvailabilityDataDTO>  Tuesday ;
    List< SetAvailabilityDataDTO>  Wednesday ;
    List< SetAvailabilityDataDTO>  Thursday ;
    List< SetAvailabilityDataDTO>  Friday ;
    List< SetAvailabilityDataDTO>  Saturday ;

    public List<SetAvailabilityDataDTO> getSunday() {
        return Sunday;
    }

    public void setSunday(List<SetAvailabilityDataDTO> sunday) {
        Sunday = sunday;
    }

    List< SetAvailabilityDataDTO>  Sunday ;



    public SetDaywiseAvailabilityDataDTO( List< SetAvailabilityDataDTO>  Monday,  List< SetAvailabilityDataDTO>  Tuesday, List< SetAvailabilityDataDTO>  Wednesday, List< SetAvailabilityDataDTO>  Thursday
    , List< SetAvailabilityDataDTO>  Friday, List< SetAvailabilityDataDTO>  Saturday , List< SetAvailabilityDataDTO>  Sunday)
    {
        super();

        this.Monday = Monday;
        this.Tuesday = Tuesday;
        this.Wednesday = Wednesday;
        this.Thursday = Thursday;
        this.Friday = Friday;
        this.Saturday = Saturday;
        this.Sunday = Sunday;

    }
}
