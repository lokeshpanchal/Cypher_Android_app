package com.helio.silentsecret.callbacks;

import com.helio.silentsecret.models.Secret;

public interface AccessSecretCallback {

    void onReceive(Secret item);
}
