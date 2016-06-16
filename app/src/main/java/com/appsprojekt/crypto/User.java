package com.appsprojekt.crypto;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * author:  Adrian Kuta
 * date:    16.06.2016.
 */
@IgnoreExtraProperties
public class User {
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
