package com.helio.silentsecret.callbacks;

import com.helio.silentsecret.models.Notification;

import java.util.List;

public interface NotificationsCallback {

    void onReceive(List<Notification> data);
}
