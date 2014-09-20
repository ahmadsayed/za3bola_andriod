package com.robot.zabola.behavior;

import android.util.Log;

public class MoveStraight extends RobotBehavior {
	private static final String TAG = "RobotBehavior";
	float goal;
	float orientation;
	
	public float getOrientation() {
		return orientation;
	}


	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}


	public float getGoal() {
		return goal;
	}


	public void setGoal(float goal) {
		this.goal = goal;
	}


	private float integratedError = 0;

	@Override
	public void setup() {
		sensorService.begin();
	}
	@Override
	public void loop() {
		// update sensor output in GUI
		orientation = sensorService.getAzimuth() * 180f / (float) Math.PI;
		if (state == 1) {
			Log.d(TAG, "state 1");
			goal = orientation;
			integratedError = 0;
			state = 2;
		}

		if (state == 2) {
			// greater than 10 degree
			Log.d(TAG, "goal 2");
			Log.d(TAG, (goal + " : " + orientation));
			int rangeA = 255;
			int rangeB = rangeA;
			// 0 - mode straight mode,
			// 1 - correction mode
			float kp = 4;
			float ki = (10f / 1000f);
			float error = orientation - goal;
			if (Math.abs(error) >= 0.5) {
				mode = 1;
				integratedError += error;
				
				// P I Controller
				int diff = Math.round(kp * error + ki * integratedError);
				if (Math.abs(diff) >= 255) {
					diff = (diff > 0) ? 254: -254; 
				}
				rangeA = rangeB = 255 - (Math.abs(diff) / 2);

				rangeA += Math.floor(diff / 2.0);
				rangeB -= Math.ceil(diff / 2.0);
				Log.d(TAG, (rangeA + " : " + rangeB));
				robot.setWheelA((byte)rangeA);
				robot.setWheelB((byte)rangeB);

			} else {
				if (mode == 1) {
					robot.setWheelA((byte)255);
					robot.setWheelB((byte)255);
					mode = 0;
				}

			}
		}
		if (state == 0) {
			robot.setWheelA((byte)255);
			robot.setWheelB((byte)255);
			
		}

	}

}
