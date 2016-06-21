package com.appsprojekt.crypto;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * author:  Adrian Kuta
 * date:    16.06.2016.
 */
@IgnoreExtraProperties
public class User {

	@Exclude
	public String userId;
	public String publicValue;

	public User() {

	}

	public User(String publicValue) {
		this.publicValue = publicValue;
	}

	public static DatabaseReference getDatabaseReference() {
		return FirebaseDatabase.getInstance().getReference().child("users");
	}
}
