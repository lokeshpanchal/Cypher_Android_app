package com.helio.silentsecret.callbacks;

import com.helio.silentsecret.models.Secret;

import java.util.List;

public interface SearchUpdateCallback {

    void onUpdate(List<Secret> list, int skip);
}
