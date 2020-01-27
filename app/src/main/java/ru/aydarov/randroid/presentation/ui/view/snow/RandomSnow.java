package ru.aydarov.randroid.presentation.ui.view.snow;

/**
 * @author Aydarov Askhar 2020
 */

import java.util.Random;

public class RandomSnow {
    private static final Random RANDOM = new Random();

    public double getRandom(double lower, double upper) {
        double min = Math.min(lower, upper);
        return getRandom(Math.max(lower, upper) - min) + min;
    }

    public double getRandom(double upper) {
        return RANDOM.nextDouble() * upper;
    }

    public int getRandom(int upper) {
        return RANDOM.nextInt(upper);
    }
}

