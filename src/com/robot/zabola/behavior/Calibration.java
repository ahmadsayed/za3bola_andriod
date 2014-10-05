package com.robot.zabola.behavior;

import java.util.Date;

public class Calibration extends RobotBehavior {
	int a, b;
	float orientation;
	int numberOfCompeleteRotation = 0;
	long timeInMillis;
	boolean startCalibration = false;
	boolean buttonClicked = false;
	float wheelAVal = -1;
	float wheelBVal = -1;

	@Override
	public void setup() {
		a = 0;
		b = 0;
		sensorService.begin();

	}
	boolean PI_MATCH = false;
	int maxspeedA = 255;
	int maxspeedB = 255;
	final int numbers = 2;
	boolean startWheelA = false;
	boolean startWheelB = false;
	@Override
	// Execute this every fixed time period , 100millisecond :)
	public void loop() {
		// Wheel speed -255 -> 0 -> 255
		robot.setWheelA(a);
		robot.setWheelB(b);
		if (buttonClicked) {
			if (wheelAVal == -1) {
				if (!startWheelA) {
					startCalibration = false;
					numberOfCompeleteRotation=0;
					a =maxspeedA;
					b = 0;
					startWheelA = true;
				}
			} else if (wheelBVal == -1){
				if (!startWheelB) {
					startCalibration = false;
					numberOfCompeleteRotation=0;
					b = maxspeedB;
					a = 0;
					startWheelB = true;
				}
			} else {
				//WheelA is faster recalibrate with lower a speed
				if ((wheelAVal - wheelBVal) < -100) {
					startCalibration = false;
					numberOfCompeleteRotation=0;
					maxspeedA--;
					a = maxspeedA;
					b = 0;			
					startWheelA = false;
					wheelAVal = -1;
				} else if ((wheelBVal - wheelAVal) < -100) {
					startCalibration = false;
					numberOfCompeleteRotation=0;
					maxspeedB--;
					b = maxspeedB;
					a = 0;			
					startWheelB = false;
					wheelBVal = -1;

				}
			}
		}
		if (numberOfCompeleteRotation ==numbers) {
			startCalibration = false;
			numberOfCompeleteRotation=0;
			long currentTime = new Date().getTime();
			long totalTime = currentTime - timeInMillis;
			lcd3 = String.valueOf(totalTime);
			float timeofWCR = ((float)totalTime / (float)numbers) * (32.5f/110f);
			lcd1 = String.valueOf(timeofWCR);
			if (wheelAVal == -1) {					
				wheelAVal = timeofWCR;
				maxspeedA = a;
			} else if (wheelBVal == -1) {
				wheelBVal = timeofWCR;
				maxspeedB = b;
			}
			lcd4 = String.valueOf(maxspeedA) + ", " + String.valueOf(maxspeedB);
			a = b = 0;
			timeInMillis = new Date().getTime();
			

		}
		// insure that that a compelete 180 happen before considering the equal value a return
		if (buttonClicked) {
			lcd2 = String.valueOf(sensorService.getAzimuth());
			int error = Math.round(sensorService.getAzimuth()) - Math.round(orientation);
			if (Math.abs(error) <= 2) {
				if (PI_MATCH) {
					numberOfCompeleteRotation++;
					lcd4 = String.valueOf(numberOfCompeleteRotation);
					PI_MATCH = false;
				}
			}
			int errorDash = Math.round(sensorService.getAzimuth()*-1) - Math.round(orientation);
			if (Math.abs(errorDash) <= 2) {
				PI_MATCH = true;
			}

		}

	}

	@Override
	public void button1() {		
		orientation = sensorService.getAzimuth();
		lcd1 = String.valueOf(orientation);
		timeInMillis = new Date().getTime();

		buttonClicked = true;
		maxspeedA = 255;
		maxspeedB = 255;
	}

	public void button2() {
		a = b = 0;
		buttonClicked = false;
		startCalibration = false;
		numberOfCompeleteRotation=0;
		lcd1 = "Stopped";
	}
	public void button3() {
		a = 254;
		b = 255;
	}
}
