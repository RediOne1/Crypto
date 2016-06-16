package com.appsprojekt.crypto;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

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

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView userId;
		public CheckBox checkBox;

		public ViewHolder(View itemView) {
			super(itemView);
		}
	}
}
