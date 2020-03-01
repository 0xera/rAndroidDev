package ru.aydarov.randroid.presentation.ui.view.snow;


import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Aydarov Askhar 2020
 */

public class RandomSnow {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();


    public double getRandom(double lower, double upper) {
        double min = Math.min(lower, upper);
        double max = Math.max(lower, upper);
        return RANDOM.nextDouble(min, max);
    }

    public double getRandom(double upper) {
        return RANDOM.nextDouble(upper);
    }

    public int getRandom(int upper) {
        return RANDOM.nextInt(upper);
    }
}
