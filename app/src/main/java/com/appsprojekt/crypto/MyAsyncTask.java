package com.appsprojekt.crypto;

import android.os.AsyncTask;
import android.widget.CheckBox;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * author:  Adrian Kuta
 * date:    21.06.2016
 */
public class MyAsyncTask extends AsyncTask<String, String, String> {

	private String connectionKey;
	private CheckBox checkBox;

	public MyAsyncTask(String connectionKey, CheckBox checkBox) {
		this.connectionKey = connectionKey;
		this.checkBox = checkBox;
		checkBox.setChecked(false);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... strings) {
		DatabaseReference reference = Connection.getDatabaseReference().child(connectionKey);
		try {
			Thread.sleep(4000);
			Map<String, Object> update = new HashMap<>();
			update.put("message", getRandomMessage());
			reference.updateChildren(update);

			Thread.sleep(5000);
			reference.removeValue();
			publishProgress();

		} catch (InterruptedException e) {

		}
		return null;
	}

	private String getRandomMessage() {
		Random r = new Random();
		byte[] randomBytes = new byte[32];
		r.nextBytes(randomBytes);
		return new String(randomBytes);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
		checkBox.setChecked(true);
	}

	@Override
	protected void onPostExecute(String s) {
		super.onPostExecute(s);
	}
}
