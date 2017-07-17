package com.helio.silentsecret.callbacks;

import com.helio.silentsecret.models.Comment;

import java.util.List;

public interface CommentsCallback {

    void onUpdate(List<Comment> list);
}
