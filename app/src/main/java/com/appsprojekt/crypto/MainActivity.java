package com.appsprojekt.crypto;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * author:  Adrian Kuta
 * date:    16.06.2016.
 */
public class MainActivity extends LocalizerActivity implements LocationListener {

	Util util;
	private TextInputLayout inputLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inputLayout = (TextInputLayout) findViewById(R.id.current_position);

		util = new Util();

	}

	@Override
	public void onLocationChanged(Location location) {
		LatLng userPosition = new LatLng(location.getLatitude(), location.getLongitude());
		updateUserInDatabase(userPosition);
		updateUi(userPosition);
	}

	private void updateUi(LatLng userPosition) {
		EditText editText = inputLayout.getEditText();
		if (editText != null) {
			String text = userPosition.latitude + " " + userPosition.longitude;
			editText.setText(text);
		}
	}

	private void updateUserInDatabase(LatLng userPosition) {
		FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

		if (firebaseUser == null)
			return;

		DatabaseReference reference = User.getDatabaseReference().child(firebaseUser.getUid());

		int userSecretNumber = util.getUserSecretNumber(userPosition);
		User user = new User(userSecretNumber);

		reference.setValue(user);
	}

	@Override
	public LocationListener getLocationListener() {
		return this;
	}
}
