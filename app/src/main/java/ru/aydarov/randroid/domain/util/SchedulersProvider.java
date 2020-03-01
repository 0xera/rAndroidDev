package ru.aydarov.randroid.domain.util;


import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SchedulersProvider {
    public static Scheduler io() {
        return Schedulers.io();
    }
}
