package com.appsprojekt.crypto;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * author:  Adrian Kuta
 * date:    21.06.2016
 */
@IgnoreExtraProperties
public class Connection {

	public String userA;
	public String userB;
	public String publicA;
	public String publicB;
	public String message;

	public Connection() {
	}

	@Exclude
	public static DatabaseReference getDatabaseReference() {
		return FirebaseDatabase.getInstance().getReference().child("connections");
	}

}
