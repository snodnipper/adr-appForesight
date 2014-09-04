package com.livenation.foresight.formatters;

import android.location.Address;
import android.text.TextUtils;

import java.util.ArrayList;

public class AddressFormatter {
    public static String format(Address address) {
        ArrayList<String> pieces = new ArrayList<>();

        String locality = address.getLocality();
        if (!TextUtils.isEmpty(locality))
            pieces.add(locality);

        String adminArea = address.getAdminArea();
        if (!TextUtils.isEmpty(adminArea))
            pieces.add(adminArea);

        return TextUtils.join(", ", pieces);
    }
}
