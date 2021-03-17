package edu.education.myapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;


public class RestartServiceBroadcasrReciever extends BroadcastReceiver {

    private static JobScheduler jobScheduler;
    private RestartServiceBroadcasrReciever restartServiceBroadcasrReciever;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP) {
            scheduleJob(context);
        } else {
            registerRestarterReciever(context);
            ProcessMainClass processMainClass = new ProcessMainClass();
            processMainClass.launchService(context);
        }

    }

    private void registerRestarterReciever( final Context context) {
        if (restartServiceBroadcasrReciever == null) {
            restartServiceBroadcasrReciever = new RestartServiceBroadcasrReciever();
        } else try {
            context.unregisterReceiver(restartServiceBroadcasrReciever);
        } catch (Exception e) {

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter();
                filter.addAction(Globals.RESTART_INTENT);
                try {
                    context.registerReceiver(restartServiceBroadcasrReciever,filter);
                } catch (Exception e) {
                    try {
                        context.getApplicationContext().registerReceiver(restartServiceBroadcasrReciever,filter);
                    } catch (Exception e1) {

                    }
                }
            }
        },1000);
    }

    public static void scheduleJob(Context context) {
        if (jobScheduler ==  null) {
            jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        }

        ComponentName componentName = new ComponentName(context, ServiceRestarter.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setOverrideDeadline(0)
                .setPersisted(true)
                .build();
        jobScheduler.schedule(jobInfo);
    }

}
