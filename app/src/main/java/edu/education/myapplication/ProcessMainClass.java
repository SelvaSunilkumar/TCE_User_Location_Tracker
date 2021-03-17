package edu.education.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class ProcessMainClass {

    private static Intent serviceIntent = null;

    public ProcessMainClass() {

    }

    private void setServiceIntent(Context context) {
        if (serviceIntent == null) {
            serviceIntent = new Intent(context, BackgroundService.class);
        }
    }

    public void launchService(Context context) {

        if (context == null) {
            return;
        }

        Toast.makeText(context,"Session starting", Toast.LENGTH_LONG).show();

        setServiceIntent(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        } else {
            context.startService(serviceIntent);
        }

        Toast.makeText(context,"Session started",Toast.LENGTH_LONG).show();

    }

}
