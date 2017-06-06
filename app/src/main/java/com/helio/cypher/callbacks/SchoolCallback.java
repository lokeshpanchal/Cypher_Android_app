package com.helio.cypher.callbacks;

import com.helio.cypher.models.School;

import java.util.List;

public interface SchoolCallback {

    void onUpdate(List<School> data);
}
