package com.helio.cypher.checkers;

import com.helio.cypher.StaticObjectDTO.SecretCategoryDTO;
import com.helio.cypher.activities.MainActivity;

public class CategoryHelper {

    public static String returnCategory( String mood) {

        try {
            if(MainActivity.stataicObjectDTO != null)
            {
                if (MainActivity.stataicObjectDTO.getSecretCategoryDTOs() == null || mood == null)
                    return "light";
            }
            else
                return "light";

            for (SecretCategoryDTO item : MainActivity.stataicObjectDTO.getSecretCategoryDTOs())
            {
                for (String part : item.getFeel_array())
                {
                    if (mood.equals(part)) {
                        return item.getCat_name();
                    }
                }
            }
        } catch (NullPointerException e) {

        }

        return "happy";
    }
}
