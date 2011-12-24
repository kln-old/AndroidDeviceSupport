package com.sample.android.service;

import java.util.concurrent.ArrayBlockingQueue;

import com.sample.hardware.joystick.Joystick;
import com.sample.hardware.joystick.IJoystick;
import com.sample.hardware.joystick.JoystickCallback;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import android.os.RemoteException;
import android.content.pm.PackageManager;

public class JoystickService extends Service {

	private static final String TAG = JoystickService.class.getSimpleName();
	private Joystick js;
	private JoystickCallback jsCallback = null;
	private int ownerPid = 0;
	
	private Thread workerThread = new Thread( new Runnable(){
		
		public void run(){
			Log.i(TAG, "Worker running");
			while(true) {	
				int key = Joystick.getKey();
				if(key==-1)
					return;
				if(jsCallback != null) {
					Log.i(TAG, "Reporting key "+ key+ " to app");
					try{
						jsCallback.onKeyPress(key);
					}catch (RemoteException e){
						break;
					}
				} else {
					Log.i(TAG, "Ignoring key "+ key);
				}
			}
		}
	});
	

	private IJoystick.Stub apiEndpoint = new IJoystick.Stub() {

		public boolean setCallback(JoystickCallback callback) {
			// enable setting callback if nobody owns the device
			// or if the owner calls it
			if((ownerPid==0) || (ownerPid == getCallingPid())) {
				ownerPid = getCallingPid();
				jsCallback = callback;
				Log.i(TAG, "Joystick owner = "+ownerPid);			
			}
			return false;
		}

		public boolean clearCallback() {
			if(ownerPid != getCallingPid())
				return false;
			jsCallback = null;
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
		if(Joystick.open()){
			Log.i(TAG, "device successfully opened...");
		}else {
			Log.i(TAG, "failed to open device...");
		}
		
		workerThread.start();

	}

	public void onDestroy() {
		super.onDestroy();
		Joystick.close();
		try {
			workerThread.join();
		} catch (InterruptedException e) {
		}
		Log.i(TAG, "Service exiting...");
	}
} 
