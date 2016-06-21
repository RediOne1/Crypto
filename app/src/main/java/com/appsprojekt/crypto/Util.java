package com.appsprojekt.crypto;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * author:  Adrian Kuta
 * date:    16.06.2016.
 */
public class Util {
	public List<LatLng> getMyArea(LatLng userPosition) {
		Random r = new Random();
		int a = r.nextInt()%100 +1;
		int lngInt, latInt;
		LatLng p1, p2, p3, p4;
		List<LatLng> area = new ArrayList<>();
		double lng = new BigDecimal(userPosition.longitude).setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
		lngInt = ((int) lng);
		double lat = new BigDecimal(userPosition.latitude).setScale(3, BigDecimal.ROUND_DOWN).doubleValue();
		latInt = ((int) lat);
		p1 = new LatLng(latInt, lngInt);
		p2 = new LatLng(latInt + 0.001d, lngInt);
		p3 = new LatLng(latInt, lngInt + 0.001d);
		p4 = new LatLng(latInt + 0.001d, lngInt + 0.001d);
		area.add(p1);
		area.add(p2);
		area.add(p3);
		area.add(p4);
		return area;
	}
	public String DH (int publicValue){
		Random r = new Random();
		int  p = r.nextInt()%1000000+1, a = r.nextInt()%10000000+1;
		String key;
		key =  Integer.toHexString((publicValue^a) % p);
		Log.d("klucz", key);
		return key;
	}
	private static byte[] encrypt(byte[] raw, byte[] clear, String variable) throws Exception {
		String secrectID = "1001231";
		byte[] key = (variable).getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] encrypted = cipher.doFinal((secrectID).getBytes());
		return encrypted;
	}
}
