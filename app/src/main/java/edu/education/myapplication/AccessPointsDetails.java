package edu.education.myapplication;

public class AccessPointsDetails {

    public double latitude;
    public double longitude;
    public String locationName;
    public double minimumAccuracy;

    public AccessPointsDetails(double latitude, double longitude, String locationName, double minimumAccuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
        this.minimumAccuracy = minimumAccuracy;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getMinimumAccuracy() {
        return minimumAccuracy;
    }

    public void setMinimumAccuracy(double minimumAccuracy) {
        this.minimumAccuracy = minimumAccuracy;
    }
}
