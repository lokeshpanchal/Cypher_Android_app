package com.helio.silentsecret.models;

import java.util.List;

public class RiskState {
    private List<String> riskWords;
    private String state;

    public List<String> getRiskWords() {
        return riskWords;
    }

    public void setRiskWords(List<String> riskWords) {
        this.riskWords = riskWords;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
