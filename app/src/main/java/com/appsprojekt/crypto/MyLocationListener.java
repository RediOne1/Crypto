package com.appsprojekt.crypto;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationListener;

/**
 * author:  Adrian Kuta
 * date:    15.06.2016
 */
public class MyLocationListener implements LocationListener {
	@Override
	public void onLocationChanged(Location location) {
		Log.d("DEBUG_TAG", location.getLatitude() + " " + location.getLongitude());
	}
}
