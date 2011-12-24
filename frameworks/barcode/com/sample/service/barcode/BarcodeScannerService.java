package com.sample.service.barcode;

import java.util.concurrent.ArrayBlockingQueue;

import com.sample.hardware.barcode.BarcodeScanner;
import com.sample.hardware.barcode.IBarcodeScanner;
import com.sample.hardware.barcode.BarcodeCallback;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import android.os.RemoteException;
import android.content.pm.PackageManager;

public class BarcodeScannerService extends Service {

	private static final String TAG = BarcodeScannerService.class.getSimpleName();
	private BarcodeCallback callback = null;
	private int ownerPid = 0;


	private IBarcodeScanner.Stub apiEndpoint = new IBarcodeScanner.Stub() {

		public boolean setCallback(BarcodeCallback cb) {
			if((ownerPid==0) || (ownerPid == getCallingPid())) {
				ownerPid = getCallingPid();
				callback = cb;
				Log.i(TAG, "Barcode scanner owner = "+ownerPid);
			}
			return false;
		}

		public boolean getBarcode() {
			if(ownerPid != getCallingPid()) {
				Log.i(TAG, "getBarcode - fail non-owner...");
				return false;
			}
			Thread t = new Thread(new Runnable() {
				public void run() {
					Log.i(TAG, "Scanning barcode...");
					String bcs = BarcodeScanner.getBarcode();
					try {
						Log.i(TAG, "Returning barcode = "+ bcs);
						callback.onBarcode((bcs!=null), bcs);
					} catch (RemoteException e) {
						Log.e(TAG, "Failed to return barcode...");
					}
				}
			});
			t.start();

			return true;
		}

		public boolean clearCallback() {
			if(ownerPid != getCallingPid())
				return false;
			callback = null;
			ownerPid = 0;
			return true;
		}

	};

	public IBinder onBind(Intent intent) {

		return apiEndpoint;
	}

	public void onCreate() {

		super.onCreate();

		Log.i(TAG, "Service starting...");
		if(BarcodeScanner.open()){
			Log.i(TAG, "device successfully opened...");
		}else {
			Log.i(TAG, "failed to open device...");
		}

	}

	public void onDestroy() {
		super.onDestroy();
		BarcodeScanner.close();
		Log.i(TAG, "Service exiting...");
	}
}
