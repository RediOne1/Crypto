package com.appsprojekt.crypto;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class LocalizerActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {

	private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 123;

	private GoogleApiClient googleApiClient;
	private LocationRequest locationRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		googleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addApi(LocationServices.API)
				.build();

		locationRequest = new LocationRequest();
		locationRequest.setInterval(TimeUnit.SECONDS.toMillis(10));
		locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

	}

	@Override
	protected void onStart() {
		googleApiClient.connect();
		super.onStart();
	}

	@Override
	protected void onStop() {
		googleApiClient.disconnect();
		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (googleApiClient.isConnected()) {
			startLocationUpdates();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		stopLocationUpdates();
	}

	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(
				googleApiClient, getLocationListener());
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		startLocationUpdates();
	}

	private void startLocationUpdates() {
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			checkPermissions();
			return;
		}
		LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, getLocationListener());
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@TargetApi(Build.VERSION_CODES.M)
	private void checkPermissions() {
		List<String> permissions = new ArrayList<>();
		if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
			permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);

		if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
			permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (!permissions.isEmpty()) {
			String[] params = permissions.toArray(new String[permissions.size()]);
			requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
		} // else: We already have permissions, so handle as normal
	}

	public abstract LocationListener getLocationListener();

}
