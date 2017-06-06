package com.helio.cypher.callbacks;

import java.util.List;

public interface DeleteCallback {

    void onSelectAll();

    void onUnSelectAll();

    void onDelete(List<String> items);
}
