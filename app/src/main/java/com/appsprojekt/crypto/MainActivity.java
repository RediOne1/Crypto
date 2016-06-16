package com.appsprojekt.crypto;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.google.android.gms.location.LocationListener;

/**
 * author:  Adrian Kuta
 * date:    16.06.2016.
 */
public class MainActivity extends LocalizerActivity implements LocationListener {

	private TextInputLayout inputLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inputLayout = (TextInputLayout) findViewById(R.id.current_position);
	}

	@Override
	public void onLocationChanged(Location location) {
		EditText editText = inputLayout.getEditText();
		if (editText != null) {
			String loc = location.getLatitude() + " " + location.getLongitude();
			editText.setText(loc);
		}
	}

	@Override
	public LocationListener getLocationListener() {
		return this;
	}
}
