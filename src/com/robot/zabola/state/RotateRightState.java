package com.robot.zabola.state;

public class RotateRightState extends Timestate {

	public RotateRightState(long duration) {
		super(duration);	
	}
	
	public RotateRightState(long duration, Statelike nextState) {
		super(duration, nextState);
	}
	
	@Override
	public void update(StateContext stateContext) {
		setWheelA(-255);
		setWheelB(255);
		super.update(stateContext);
	}

}
