package edu.education.myapplication;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FindMe {

    private DatabaseHandler databaseHandler;

    private static final String ACCESS_POINT_LOCATIONS_DATA_URL = "http://192.168.43.225/locationtracker/index.php/welcome/getAccessPoints";
    //Code to Upload location Access Points into Local database
    public void getAccessPointLocations(final Context context) {

        RequestQueue requestQueue;
        JsonObjectRequest objectRequest;

        databaseHandler = new DatabaseHandler(context);

        databaseHandler.deleteAccessPointTable();

        requestQueue = Volley.newRequestQueue(context);
        objectRequest = new JsonObjectRequest(Request.Method.POST, ACCESS_POINT_LOCATIONS_DATA_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //complete the code
                        double latitude = Double.parseDouble(jsonObject.getString("latitude"));
                        double longitude = Double.parseDouble(jsonObject.getString("longitude"));
                        String locationName = jsonObject.getString("name");
                        double minimumDistance = Double.parseDouble(jsonObject.getString("min"));
                        databaseHandler.uploadAccessPoints(latitude, longitude, locationName, minimumDistance);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Access point Error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(objectRequest);

    }

    private static final int MIN_USER_BLOCK_DISTANCE = 50;

    /*----------------------------------------------------------------------------------------------
                            GEO-LOCATION OF BLOCKS IN CAMPUS
    ----------------------------------------------------------------------------------------------*/
    private double[] CSE_BLOCK = new double[] {9.88282, 78.08374};
    private double[] FOOD_COURT = new double[] {9.88338, 78.08324};
    private double[] MAIN_BLOCK = new double[] {9.88282, 78.08254};
    //----------------------------------------------------------------------------------------------

    private int getLocationDistance(double latitude, double longitude) {
        if (calculateDistance(CSE_BLOCK[0], CSE_BLOCK[1], latitude, longitude) <= MIN_USER_BLOCK_DISTANCE) {
            return 0;
        } else if (calculateDistance(FOOD_COURT[0], FOOD_COURT[1], latitude, longitude) <= MIN_USER_BLOCK_DISTANCE) {
            return 1;
        } else if (calculateDistance(MAIN_BLOCK[0], MAIN_BLOCK[1], latitude, longitude) <= MIN_USER_BLOCK_DISTANCE) {
            return 2;
        } else {
            return -1;
        }
    }

    public String getLocationStatus(double latitude, double longitude) {

        switch (getLocationDistance(latitude, longitude)) {
            case 0:
                return "CSE Departmant";
            case 1:
                return "Food Court";
            case 2:
                return "Main Block";
            default:
                return "Some Other location";
        }

    }

    /*----------------------------------------------------------------------------------------------
                    get location attributes and calculate the distance
    ----------------------------------------------------------------------------------------------*/
    private int calculateDistance(double blockLatitude, double blockLongitude, double userLatitude, double userLongitude) {
        double theta = blockLongitude - userLongitude;
        double distance = Math.sin(deg2rad(blockLatitude)) * Math.sin(deg2rad(userLatitude)) + Math.cos(deg2rad(blockLatitude)) * Math.cos(deg2rad(userLatitude)) * Math.cos(deg2rad(theta));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;
        distance = distance * 1.609344;
        distance = distance * 1000;
        return (int) Math.round(distance);
    }

    //------------------------------ Mathematical Calculation Methods ------------------------------
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    //----------------------------------------------------------------------------------------------

}
