package com.sample.SampleApp;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.hardware.joystick.*;
import com.sample.SampleApp.R;

public class JoystickAppActivity extends Activity {

	private static final String TAG = "JoystickTest";
	private IJoystick api = null;
	private TextView tv;
	private ImageView imgbtnUp;
	private ImageView imgbtnDown;
	private ImageView imgbtnRight;
	private ImageView imgbtnLeft;
	private ImageView btn1;
	private ImageView btn2;
	private ImageView btn3;
	private ImageView btn4;
	private ImageView btn5;
	private ImageView btn6;
	private ImageView btn7;
	private ImageView btn8;
	private ImageView btn9;
	private ImageView btn10;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv = (TextView) findViewById(R.id.textview1);
		imgbtnDown = (ImageView)findViewById(R.id.imgbtnDown);
		imgbtnUp = (ImageView)findViewById(R.id.imgbtnUp);
		imgbtnLeft = (ImageView)findViewById(R.id.imgbtnLeft);
		imgbtnRight = (ImageView)findViewById(R.id.imgbtnRight);
		btn1 = (ImageView)findViewById(R.id.btn1);
		btn2 = (ImageView)findViewById(R.id.btn2);
		btn3 = (ImageView)findViewById(R.id.btn3);
		btn4 = (ImageView)findViewById(R.id.btn4);
		btn5 = (ImageView)findViewById(R.id.btn5);
		btn6 = (ImageView)findViewById(R.id.btn6);
		btn7 = (ImageView)findViewById(R.id.btn7);
		btn8 = (ImageView)findViewById(R.id.btn8);
		btn9 = (ImageView)findViewById(R.id.btn9);
		btn10 = (ImageView)findViewById(R.id.btn10);

		Intent intent = new Intent("com.sample.android.service.JoystickService");
		bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
		
		clearUI();
	}

	public void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection);
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "Service connection established");
			api = IJoystick.Stub.asInterface(service);
			try {
				api.setCallback(jsCallback);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void onServiceDisconnected(ComponentName name) {
			Log.i(TAG, "Service connection disconnected");
		}
	};
	private JoystickCallback.Stub jsCallback = new JoystickCallback.Stub() {

		public void onKeyPress(int arg0) throws RemoteException {
			// TODO Auto-generated method stub
			// update UI in GUI thread
		
			final int prevKeypress = arg0;
			runOnUiThread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					updateUI(prevKeypress);
				}
			});
		}
	};
	

	private void updateUI(int kp) {
		tv.setText("scan code : " + kp);
		
		clearUI();
		
		switch(kp){
			case 250:
					imgbtnLeft.setImageResource(R.drawable.left_alt);
				break;
	
			case 251:
				imgbtnRight.setImageResource(R.drawable.right_alt);
				break;

			case 252:
				imgbtnUp.setImageResource(R.drawable.up_alt);
				break;
		
			case 253:
				imgbtnDown.setImageResource(R.drawable.down_alt);
				break;
			
			case 288:
				btn1.setImageResource(R.drawable.btn_up);
				break;

			case 289:
				btn2.setImageResource(R.drawable.btn_up);
				break;

			case 290:
				btn3.setImageResource(R.drawable.btn_up);
				break;

			case 291:
				btn4.setImageResource(R.drawable.btn_up);
				break;

			case 292:
				btn5.setImageResource(R.drawable.btn_up);
				break;

			case 293:
				btn6.setImageResource(R.drawable.btn_up);
				break;

			case 294:
				btn7.setImageResource(R.drawable.btn_up);
				break;

			case 295:
				btn8.setImageResource(R.drawable.btn_up);
				break;

			case 296:
				btn9.setImageResource(R.drawable.btn_up);
				break;

			case 297:
				btn10.setImageResource(R.drawable.btn_up);
				break;

		}
	}
	
	private void clearUI(){
		
		imgbtnLeft.setImageResource(R.drawable.left);
		imgbtnRight.setImageResource(R.drawable.right);
		imgbtnUp.setImageResource(R.drawable.up);
		imgbtnDown.setImageResource(R.drawable.down);
		btn1.setImageResource(R.drawable.btn_down);
		btn2.setImageResource(R.drawable.btn_down);
		btn3.setImageResource(R.drawable.btn_down);
		btn4.setImageResource(R.drawable.btn_down);
		btn5.setImageResource(R.drawable.btn_down);
		btn6.setImageResource(R.drawable.btn_down);
		btn7.setImageResource(R.drawable.btn_down);
		btn8.setImageResource(R.drawable.btn_down);
		btn9.setImageResource(R.drawable.btn_down);
		btn10.setImageResource(R.drawable.btn_down);
	}
	
}
