package com.helio.silentsecret.callbacks;

import com.helio.silentsecret.models.Invite;

import java.util.List;

public interface InviteCallback {

    void onUpdate(List<Invite> data);
}
