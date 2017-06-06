package com.helio.cypher.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.helio.cypher.creator.ObjectMaker;
import com.helio.cypher.utils.Preference;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoHelper {

    public static String returnAddressString(Context context) {
        List<Address> addresses = null;
        String address = ObjectMaker.EMPTY;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(
                    Double.parseDouble(Preference.getUserLat()),
                    Double.parseDouble(Preference.getUserLon()),
                    1);
        } catch (IOException ioException) {
            address = ObjectMaker.EMPTY;
        } catch (IllegalArgumentException illegalArgumentException) {
            address = ObjectMaker.EMPTY;
        }

        if (addresses == null || addresses.size() == 0) {
            address = ObjectMaker.EMPTY;
        } else {
            Address addressesYeah = addresses.get(0);
            if (addressesYeah.getAddressLine(0) != null)
                address = address + addressesYeah.getAddressLine(0);
            if (addressesYeah.getLocality() != null)
                address = address + ObjectMaker.DEFAULT_SPLIT + addressesYeah.getLocality();
            if (addressesYeah.getCountryName() != null)
                address = address + ObjectMaker.DEFAULT_SPLIT + addressesYeah.getCountryName();
        }

        return address;
    }
}
