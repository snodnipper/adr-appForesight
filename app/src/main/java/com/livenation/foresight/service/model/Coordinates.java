package com.livenation.foresight.service.model;

import android.location.Location;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java8.util.Optional;

public class Coordinates {
    public final double latitude;
    public final double longitude;

    public static final Coordinates DEFAULT = new Coordinates(37.8267, -122.423);
    public static Coordinates fromLocation(Location location) {
        return new Coordinates(location.getLatitude(), location.getLongitude());
    }

    public static Optional<Coordinates> fromString(@Nullable String string) {
        if (TextUtils.isEmpty(string))
            return Optional.empty();

        String[] pieces = TextUtils.split(string, ",");
        if (pieces.length != 2)
            return Optional.empty();

        Double latitude, longitude;
        try {
            latitude = Double.valueOf(pieces[0]);
            longitude = Double.valueOf(pieces[1]);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        return Optional.of(new Coordinates(latitude, longitude));
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
        return latitude + "," + longitude;
    }
}
