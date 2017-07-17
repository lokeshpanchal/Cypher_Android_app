package com.helio.silentsecret.callbacks;

public interface RiskCheckerCallback {
    void onDone(boolean result, String riskWord, String state);
}
