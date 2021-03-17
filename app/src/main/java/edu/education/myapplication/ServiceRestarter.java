package edu.education.myapplication;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

public class ServiceRestarter extends JobService {

    private static RestartServiceBroadcasrReciever restartServiceBroadcasrReciever;
    private static ServiceRestarter instance;
    private static JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters params) {

        ProcessMainClass processMainClass = new ProcessMainClass();
        processMainClass.launchService(this);
        registerRestarterReciever();
        instance = this;
        ServiceRestarter.jobParameters = jobParameters;

        return false;
    }

    public void registerRestarterReciever() {
        if (restartServiceBroadcasrReciever == null) {
            restartServiceBroadcasrReciever = new RestartServiceBroadcasrReciever();
        } else try {
            unregisterReceiver(restartServiceBroadcasrReciever);
        } catch (Exception e) {

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter();
                filter.addAction(Globals.RESTART_INTENT);

                try {
                    registerReceiver(restartServiceBroadcasrReciever,filter);
                } catch (Exception e) {
                    try {
                        getApplicationContext().registerReceiver(restartServiceBroadcasrReciever,filter);
                    } catch (Exception ex) {

                    }
                }
            }
        },1000);
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        Intent broadcastIntent = new Intent(Globals.RESTART_INTENT);
        sendBroadcast(broadcastIntent);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unregisterReceiver(restartServiceBroadcasrReciever);
            }
        },1000);

        return false;
    }
}
