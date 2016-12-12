package com.ZP.main;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

/**
 * Created by C0dEr on 2016/12/9.
 */
@SuppressWarnings("resource")
public class Start {
    public static void main(String[] args) throws IOException {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
