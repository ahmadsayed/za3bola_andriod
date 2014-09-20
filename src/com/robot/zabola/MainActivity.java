package com.robot.zabola;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.robot.zabola.behavior.MoveForwared;
import com.test.ledonoff.R;

public class MainActivity extends Activity {
	private SensorManager mSensorManager = null;
	public Handler mHandler;
	public static final int TIME_CONSTANT = 30;
	SensorService sensorService;

	private Timer fuseTimer = new Timer();
	
	private static final String TAG = "LEDOnOff";
	int state = 0;
	private MoveForwared robotBehavior;
	Button b1, b2, b3, b4, b5, b6, b7, b8;
	TextView lcd1, lcd2, lcd3, lcd4;

	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	private BluetoothService mChatService = null;

	// Insert your server's MAC address
	private static String address = "00:14:03:18:22:15";
	int direction = 0;
	HashMap<String, Byte> sensorDataHandler = new HashMap<String, Byte>();

	// This function registers sensor listeners for the accelerometer,
	// magnetometer and gyroscope.
	public void initListeners() {
		mSensorManager.registerListener(sensorService, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);

		mSensorManager.registerListener(sensorService, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_FASTEST);

		mSensorManager.registerListener(sensorService, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new Handler();
		sensorService = new SensorService();
		robotBehavior = new MoveForwared();
		robotBehavior.setState(state);
		robotBehavior.setSensorService(sensorService);
		Log.d(TAG, "In onCreate()");
		mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		initListeners();
		setContentView(R.layout.activity_main);

		b1 = (Button) findViewById(R.id.b1);
		b2 = (Button) findViewById(R.id.b2);
		b3 = (Button) findViewById(R.id.b3);
		b4 = (Button) findViewById(R.id.b4);
		b5 = (Button) findViewById(R.id.b5);
		b6 = (Button) findViewById(R.id.b6);
		b7 = (Button) findViewById(R.id.b7);
		b8 = (Button) findViewById(R.id.b8);
		lcd1 = (TextView) findViewById(R.id.lcd1);
		lcd2 = (TextView) findViewById(R.id.lcd2);
		lcd3 = (TextView) findViewById(R.id.lcd3);
		lcd4 = (TextView) findViewById(R.id.lcd4);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		// If the adapter is null, then Bluetooth is not supported
		fuseTimer.scheduleAtFixedRate(new calculateFusedOrientationTask(), 100, TIME_CONSTANT);

		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		b1.setOnClickListener(robotBehavior);
		b2.setOnClickListener(robotBehavior);
		b3.setOnClickListener(robotBehavior);
		b4.setOnClickListener(robotBehavior);
		b5.setOnClickListener(robotBehavior);
		b6.setOnClickListener(robotBehavior);
		b7.setOnClickListener(robotBehavior);

	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mChatService == null)
				setupChat();
			robotBehavior.setBluetoothService(mChatService);
		}
	}

	private StringBuffer mOutStringBuffer;

	private void setupChat() {
		Log.d(TAG, "setupChat()");

		// Initialize the array adapter for the conversation thread
		mChatService = new BluetoothService(this, sensorDataHandler);
		connectDevice(null, false);
		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if (D)
			Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		// unregister the sensor listener
		mSensorManager.unregisterListener(sensorService);

		if (D)
			Log.e(TAG, "-- ON STOP --");
	}

	private static final boolean D = true;

	
	@Override
	public synchronized void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+ ON RESUME +");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mChatService.getState() == BluetoothService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
				connectDevice(null, false);

			}
		}
	}


	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE_SECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, true);
			}
			break;
		case REQUEST_CONNECT_DEVICE_INSECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, false);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupChat();
			} else {
				// User did not enable Bluetooth or an error occurred
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, "BT not enabled Finishing", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	private void connectDevice(Intent data, boolean secure) {

		// Get the BluetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		// Attempt to connect to the device
		mChatService.connect(device, secure);
	}

	class calculateFusedOrientationTask extends TimerTask {

		public void run() {
			// update sensor output in GUI\
			mHandler.post(robotBehavior);			
			mHandler.post(new Runnable() {				
				@Override
				public void run() {
					lcd1.setText(robotBehavior.getLcd1());
					lcd2.setText(robotBehavior.getLcd2());
					lcd3.setText(robotBehavior.getLcd3());
					lcd4.setText(robotBehavior.getLcd4());
				}
			});			

		}
	}

}
