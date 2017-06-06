package com.helio.cypher.callbacks;

import com.helio.cypher.models.Category;

import java.util.List;

public interface HelperCategoryCallback {

    void onUpdate(List<Category> data);
}
