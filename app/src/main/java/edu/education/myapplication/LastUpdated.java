package edu.education.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class LastUpdated {

    private static final String PREFERENCE = "lastUploaded";
    private static final String IMAGE_BITMAP_STRING = "image";
    private static final String LAST_UPLOAD_DATE_TIME = "dateTimeStamp";
    private static final String LAST_LOCATION_LATITUDE = "latitude";
    private static final String LAST_LOCATION_LONGITUDE = "longitude";
    private static final String LAST_LOCATION_STATUS = "locationStatus";
    private static final String LAST_IMAGE_UPLOAD_MODE = "mode";
    private static final String IS_PREFERENCE_SET = "isPreference";
    private SharedPreferences sharedPreferences;
    private Context context;

    public String imageString;
    public String dateTimeStamp;
    public String latitude;
    public String longitude;
    public String status;
    public String mode;

    public LastUpdated(Context context) {
        this.context = context;
        this.imageString = null;
        this.dateTimeStamp = null;
        this.latitude = null;
        this.longitude = null;
        this.status = null;
        this.mode = null;
        sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    public void editSharedPreference(String image, String dateTime, double latitude, double longitude, String status, String mode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IMAGE_BITMAP_STRING, image);
        editor.putString(LAST_UPLOAD_DATE_TIME, dateTime);
        editor.putString(LAST_LOCATION_LATITUDE, String.valueOf(latitude));
        editor.putString(LAST_LOCATION_LONGITUDE, String.valueOf(longitude));
        editor.putString(LAST_LOCATION_STATUS, status);
        editor.putString(LAST_IMAGE_UPLOAD_MODE, mode);
        editor.putBoolean(IS_PREFERENCE_SET, true);
        editor.apply();
    }

    public boolean getSharedPreference() {
        this.imageString = sharedPreferences.getString(IMAGE_BITMAP_STRING,null);
        this.dateTimeStamp = sharedPreferences.getString(LAST_UPLOAD_DATE_TIME,null);
        this.latitude = sharedPreferences.getString(LAST_LOCATION_LATITUDE, null);
        this.longitude = sharedPreferences.getString(LAST_LOCATION_LONGITUDE, null);
        this.status = sharedPreferences.getString(LAST_LOCATION_STATUS, null);
        this.mode = sharedPreferences.getString(LAST_IMAGE_UPLOAD_MODE, null);
        return sharedPreferences.getBoolean(IS_PREFERENCE_SET, false);
    }

    public String getImageBitmapString() {
        return imageString;
    }

    public String getDateTimeStamp() {
        return dateTimeStamp;
    }

    public String getLastLocationLatitude() {
        return latitude;
    }

    public String getLastLocationLongitude() {
        return longitude;
    }

    public String getLastLocationStatus() {
        return status;
    }

    public String getMode() {
        return mode;
    }

    public Bitmap getImage() {
        byte[] encodeByte = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }
}
