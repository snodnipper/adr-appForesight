package com.livenation.foresight.formatters;

import android.location.Address;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;

public class AddressFormatter {
    public static @NonNull String format(@NonNull Address address) {
        String locality = address.getLocality();
        if (!TextUtils.isEmpty(locality))
            return locality;

        String adminArea = address.getAdminArea();
        if (!TextUtils.isEmpty(adminArea))
            return adminArea;

        String country = address.getCountryName();
        if (!TextUtils.isEmpty(country))
            return country;

        return "Current Location";
    }
}
