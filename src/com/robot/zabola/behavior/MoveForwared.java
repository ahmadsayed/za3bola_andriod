package com.robot.zabola.behavior;

public class MoveForwared extends RobotBehavior {
	int a, b;
	@Override
	public void setup() {
		a = 0;
		b = 0;
	}

	@Override
	//Speed for both Wheel to max
	public void button1() {		
		a = b = 255;
	}

	@Override
	//Speed for both Wheel to zero (electric brake)
	public void button2() {
		a = b = 0;
	}
	@Override
	// Execute this every clock cycle :)
	public void loop() {
		robot.setWheelA(a);
		robot.setWheelB(b);
		lcd1 = "Test";
		lcd2 = "test2";
	}

}
