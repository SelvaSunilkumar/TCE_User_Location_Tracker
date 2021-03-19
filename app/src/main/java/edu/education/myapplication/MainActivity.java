package edu.education.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private Date date;

    private TextView systemUId;
    private TextView currentDateAndTime;
    private TextView latitude;
    private TextView longitude;

    private TextView dateTimeStamp;
    private TextView lastLongitude;
    private TextView lastLatitude;
    private TextView lastPosition;
    private TextView lastMode;
    private ImageView lastImage;

    private LinearLayout openCamera;
    private LinearLayout adminAdmin;

    private GPSTracker gpsTracker;
    private LastUpdated lastUpdate;

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FindMe findMe = new FindMe(this);
        findMe.getAccessPointLocations(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        systemUId = findViewById(R.id.uniqueId);
        currentDateAndTime = findViewById(R.id.currentDateTIme);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);

        dateTimeStamp = findViewById(R.id.dateTimeStamp);
        lastLongitude = findViewById(R.id.lastLongitude);
        lastLatitude = findViewById(R.id.lastLatitude);
        lastPosition = findViewById(R.id.lastPosition);
        lastMode = findViewById(R.id.lastMode);
        lastImage = findViewById(R.id.lastUploadedImage);

        openCamera = findViewById(R.id.openCamera);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CameraActivity.class);
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.fade_out,R.anim.fade_in);
                startActivity(intent,activityOptions.toBundle());
            }
        });

        adminAdmin = findViewById(R.id.admin);
        adminAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, adminAuth.class);
                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.fade_out,R.anim.fade_in);
                startActivity(intent,activityOptions.toBundle());
            }
        });

        gpsTracker = new GPSTracker(this);
        lastUpdate = new LastUpdated(this);

        systemUId.setText(getSystemUniqueId());

        runTimer();

    }

    @Override
    protected void onResume() {
        super.onResume();

        runTimer();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RestartServiceBroadcasrReciever.scheduleJob(getApplicationContext());
        } else {
            ProcessMainClass processMainClass = new ProcessMainClass();
            processMainClass.launchService(getApplicationContext());
        }

    }

    private String getCurrentDateAndTime() {
        date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public String getSystemUniqueId() {
        String systemUniqueId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return systemUniqueId;
    }

    private void runTimer() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                Location location = gpsTracker.getLocation();

                currentDateAndTime.setText(getCurrentDateAndTime());
                latitude.setText(String.valueOf(gpsTracker.getLatitude()));
                longitude.setText(String.valueOf(gpsTracker.getLongitude()));

                if (lastUpdate.getSharedPreference()) {
                    dateTimeStamp.setText(lastUpdate.getDateTimeStamp());
                    lastLatitude.setText(lastUpdate.getLastLocationLatitude());
                    lastLongitude.setText(lastUpdate.getLastLocationLongitude());
                    lastPosition.setText(lastUpdate.getLastLocationStatus());
                    lastMode.setText(lastUpdate.getMode());
                    if (lastUpdate.getMode().equals("Server")) {
                        lastMode.setTextColor(getResources().getColor(R.color.darkGreen));
                    } else {
                        lastMode.setTextColor(getResources().getColor(R.color.darkred));
                    }
                    lastImage.setImageBitmap(lastUpdate.getImage());
                }

                handler.postDelayed(runnable,1000);
            }
        };

        handler.postDelayed(runnable,1000);

    }
}
