package com.appsprojekt.crypto;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyUsers extends Fragment implements ChildEventListener {

	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private List<User> userList;
	private DatabaseReference reference;

	public NearbyUsers() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_nearby_users, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		userList = new ArrayList<>();

		recyclerView = (RecyclerView) view.findViewById(R.id.users_recyclerView);
		adapter = new UsersAdapter(userList);
		layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

		reference = User.getDatabaseReference();

		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);

	}

	@Override
	public void onStart() {
		super.onStart();
		reference.addChildEventListener(this);

	}

	@Override
	public void onStop() {
		reference.removeEventListener(this);
		super.onStop();
	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
		FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
		String userId = dataSnapshot.getKey();

		if (firebaseUser.getUid().equals(userId))
			return;
		User user = dataSnapshot.getValue(User.class);
		user.userId = userId;
		userList.add(user);
		adapter.notifyItemInserted(userList.indexOf(user));
	}

	@Override
	public void onChildChanged(DataSnapshot dataSnapshot, String previousChild) {

	}

	@Override
	public void onChildRemoved(DataSnapshot dataSnapshot) {

	}

	@Override
	public void onChildMoved(DataSnapshot dataSnapshot, String previousChild) {

	}

	@Override
	public void onCancelled(DatabaseError databaseError) {

	}
}
