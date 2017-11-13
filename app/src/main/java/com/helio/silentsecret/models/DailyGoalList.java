package com.helio.silentsecret.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Maroof Ahmed Siddique on 8/7/2016.
 */
public class DailyGoalList {


    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    List<String> goals ;

    public Date getCreated_at() {
        return created_at;
    }


    Date created_at = null;




    public DailyGoalList(List<String>  goals, Date created_at)
    {
        super();

        this.goals = goals;
        this.created_at = created_at;


    }
}
