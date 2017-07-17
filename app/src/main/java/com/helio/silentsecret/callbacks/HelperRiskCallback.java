package com.helio.silentsecret.callbacks;

import com.helio.silentsecret.models.RiskState;

import java.util.List;

public interface HelperRiskCallback {

    void onUpdate(List<RiskState> data);
}
