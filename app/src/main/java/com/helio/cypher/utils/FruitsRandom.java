package com.helio.cypher.utils;

import java.util.Random;

public class FruitsRandom {
    public static String getFruit(Random rnd) {
        return "fruits/" + (rnd.nextInt(8) + 1) + ".png";
    }
}
