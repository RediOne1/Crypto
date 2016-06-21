package com.appsprojekt.crypto;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

/**
 * author:  Adrian Kuta
 * date:    16.06.2016.
 */
public class MainActivity extends LocalizerActivity implements LocationListener, View.OnClickListener {

	Util util;
	private TextInputLayout inputLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.generateButton);
		button.setOnClickListener(this);

		inputLayout = (TextInputLayout) findViewById(R.id.current_position);

		util = new Util();

	}

	@Override
	public void onLocationChanged(Location location) {
		LatLng userPosition = new LatLng(location.getLatitude(), location.getLongitude());
		updateUi(userPosition);
	}

	private void updateUi(LatLng userPosition) {
		EditText editText = inputLayout.getEditText();
		if (editText != null) {
			String text = userPosition.latitude + " " + userPosition.longitude;
			editText.setText(text);
		}
	}

	@Override
	public LocationListener getLocationListener() {
		return this;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.generateButton) {
			generate();
		}
	}

	private void generate() {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < 300; i++) {
			int randomInt = r.nextInt(10);
			sb.append(randomInt);
		}
		String publicValue = sb.toString();
		FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
		DatabaseReference reference = User.getDatabaseReference().child(firebaseUser.getUid());
		User user = new User(publicValue);
		reference.setValue(user);
	}
}
