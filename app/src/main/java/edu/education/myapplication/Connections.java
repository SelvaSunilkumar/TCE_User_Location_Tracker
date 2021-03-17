package edu.education.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Connections {

    private Context context;

    public Connections(Context context) {
        this.context = context;
    }

    public boolean isMobileDataEnabled() {

        boolean isEnabled = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class mClass = Class.forName(connectivityManager.getClass().getName());
            Method method = mClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true);
            isEnabled = (boolean) method.invoke(connectivityManager);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return isEnabled;
    }

    public boolean isWiFiEnabled() {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            return true;
        } else {
            return false;
        }

    }

}
