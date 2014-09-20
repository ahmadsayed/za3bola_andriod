package com.robot.zabola;

public class RobotSpec {

	private int wheelA;
	private int wheelB;
	public RobotSpec () {		
	}
	
	public RobotSpec(byte wheelA, byte wheelB) {
		this.wheelA = wheelA;
		this.wheelB = wheelB;
	}
	
	public int getWheelA() {
		return wheelA;
	}
	public void setWheelA(int wheelA) {
		this.wheelA = wheelA;
	}
	public int getWheelB() {
		return wheelB;
	}
	public void setWheelB(int wheelB) {
		this.wheelB = wheelB;
	}

}
