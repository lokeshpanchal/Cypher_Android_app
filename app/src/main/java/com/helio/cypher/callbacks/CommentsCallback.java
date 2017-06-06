package com.helio.cypher.callbacks;

import com.helio.cypher.models.Comment;

import java.util.List;

public interface CommentsCallback {

    void onUpdate(List<Comment> list);
}
