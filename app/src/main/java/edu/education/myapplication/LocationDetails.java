package edu.education.myapplication;

public class LocationDetails {

    public String currentTime;
    public double latitude;
    public double longitude;
    public String position;

    public LocationDetails(String currentTime, double latitude, double longitude, String position) {
        this.currentTime = currentTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.position = position;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
