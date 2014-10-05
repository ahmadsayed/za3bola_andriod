package com.robot.zabola.behavior;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.robot.zabola.BluetoothService;
import com.robot.zabola.RobotSpec;
import com.robot.zabola.SensorService;

public abstract class RobotBehavior implements Runnable, OnClickListener {
	int lastA, lastB;
	protected String lcd1, lcd2, lcd3, lcd4;
	


	public String getLcd4() {
		return lcd4;
	}



	public void setLcd4(String lcd4) {
		this.lcd4 = lcd4;
	}



	public String getLcd1() {
		return lcd1;
	}



	public void setLcd1(String lcd1) {
		this.lcd1 = lcd1;
	}



	public String getLcd2() {
		return lcd2;
	}



	public void setLcd2(String lcd2) {
		this.lcd2 = lcd2;
	}



	public String getLcd3() {
		return lcd3;
	}



	public void setLcd3(String lcd3) {
		this.lcd3 = lcd3;
	}



	

	public  RobotBehavior() {
	}
	
	public void init() {
		setup();
	}
	
	
	@Override
	public void onClick(View v) {
		Button b = (Button)v;
		String buttonName = b.getText().toString();
		try {
			Method method = this.getClass().getMethod("button" + buttonName);
			method.invoke(this);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void button1() {
		
	}

	public void button2() {
		
	}

	public void button3() {
		
	}

	public void button4() {
		
	}

	public void button5() {
		
	}
	public void button6() {
		
	}

	public void button7() {
		
	}
	public void button8() {
		
	}

	protected int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	private static final String TAG = "RobotBehavior";
	protected SensorService sensorService;
	public SensorService getSensorService() {
		return sensorService;
	}

	public void setSensorService(SensorService sensorService) {
		this.sensorService = sensorService;
	}

	protected RobotSpec robot = new RobotSpec();
	
	private BluetoothService mChatService = null;
	


	public void setBluetoothService (BluetoothService mChatService) {
		this.mChatService = mChatService;
	}


	protected int mode = 0;



	public void run() {
		loop();
		sendCommand();
	}

	abstract public void setup();
	abstract public void loop();

	public void sendCommand() {
		if(robot != null) {
			if (robot.getWheelA() == lastA && robot.getWheelB() == lastB) {
				//do nothing
			} else {
				lastA = robot.getWheelA();
				lastB = robot.getWheelB();
				sendControlMessage(robot);
			}
		}
	}
	private byte getDirectionFromSpeed(int speed) {
		if (speed > 0) {
			return (byte)'F';
		} else if (speed < 0) {
			return (byte)'B';
		} else  {
			return (byte)'R';
		}
	}
	private void sendControlMessage(RobotSpec spec) {
		byte[] msg = { 'A', getDirectionFromSpeed(spec.getWheelA()), (byte)Math.abs(spec.getWheelA()), 'B', getDirectionFromSpeed(spec.getWheelB()), (byte)Math.abs(spec.getWheelB()) };
		sendMessage(msg);
	}

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            A string of text to send.
	 */
	private synchronized void sendMessage(byte[] message) {
		// Check that we're actually connected before trying anything
		if (mChatService.getState() != BluetoothService.STATE_CONNECTED) {
			return;
		}

		// Check that there's actually something to send

		// Get the message bytes and tell the BluetoothChatService to write
		if ((message[0] == 'A' || message[0] == 'B') && (message[1] == 'R' || message[1] == 'F' || message[1] == 'B'))
			mChatService.write(message);

		// Reset out string buffer to zero and clear the edit text field
		
	}	
}
