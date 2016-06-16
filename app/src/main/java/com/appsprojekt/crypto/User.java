package com.appsprojekt.crypto;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.maps.android.PolyUtil;

/**
 * author:  Adrian Kuta
 * date:    16.06.2016.
 */
@IgnoreExtraProperties
public class User {

	@Exclude
	public String userId;
	public int secretNumber;

	public User() {

	}

	public User(int secretNumber) {
		this.secretNumber = secretNumber;
	}

	public static DatabaseReference getDatabaseReference() {
		return FirebaseDatabase.getInstance().getReference().child("users");
	}
}
