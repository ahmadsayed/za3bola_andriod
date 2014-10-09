package com.robot.zabola.state;

public class RotateLeftState extends Timestate {

	public RotateLeftState(long duration) {
		super(duration);	
	}
	
	public RotateLeftState(long duration, Statelike nextState) {
		super(duration, nextState);
	}
	
	@Override
	public void update(StateContext stateContext) {
		setWheelA(255);
		setWheelB(-255);
		super.update(stateContext);
	}

}
