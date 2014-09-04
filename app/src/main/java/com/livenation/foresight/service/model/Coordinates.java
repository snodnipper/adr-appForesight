package com.livenation.foresight.service.model;

import android.location.Location;

public class Coordinates {
    public final double latitude;
    public final double longitude;

    public static final Coordinates DEFAULT = new Coordinates(37.8267, -122.423);
    public static Coordinates fromLocation(Location location) {
        return new Coordinates(location.getLatitude(), location.getLongitude());
    }

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean isDefault() {
        return equals(DEFAULT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;

        Coordinates that = (Coordinates) o;

        return (Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
