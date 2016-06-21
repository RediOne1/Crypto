package com.appsprojekt.crypto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:  Adrian Kuta
 * date:    16.06.2016.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

	private final List<User> userList;

	public UsersAdapter(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.single_user_layout, parent, false);
		ViewHolder holder = new ViewHolder(view);
		holder.userId = (TextView) view.findViewById(R.id.user_id);
		holder.checkBox = (CheckBox) view.findViewById(R.id.nearbyCheckbox);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		User user = userList.get(position);
		holder.userId.setText(user.userId);

	}

	@Override
	public int getItemCount() {
		return userList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

		public TextView userId;
		public CheckBox checkBox;

		public ViewHolder(View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			Connection connection = new Connection();

			FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
			connection.userA = firebaseUser.getUid();
			connection.userB = userId.getText().toString();

			DatabaseReference databaseReference = Connection.getDatabaseReference();
			String key = databaseReference.push().getKey();

			databaseReference.child(key).setValue(connection);


			setPublicA(key, connection.userA);
			setPublicB(key, connection.userB);
			new MyAsyncTask(key, checkBox).execute();
		}

		public void setPublicA(final String connectionKey, String userId) {
			DatabaseReference reference = User.getDatabaseReference().child(userId);
			reference.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					User user = dataSnapshot.getValue(User.class);
					Map<String, Object> updatedChildren = new HashMap<>();
					updatedChildren.put("publicA", user.publicValue);

					DatabaseReference connectionRef = Connection.getDatabaseReference().child(connectionKey);
					connectionRef.updateChildren(updatedChildren);
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {

				}
			});
		}

		public void setPublicB(final String connectionKey, String userId) {
			DatabaseReference reference = User.getDatabaseReference().child(userId);
			reference.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					User user = dataSnapshot.getValue(User.class);
					Map<String, Object> updatedChildren = new HashMap<>();
					updatedChildren.put("publicB", user.publicValue);

					DatabaseReference connectionRef = Connection.getDatabaseReference().child(connectionKey);
					connectionRef.updateChildren(updatedChildren);
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {

				}
			});
		}
	}
}
