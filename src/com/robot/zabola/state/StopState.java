package com.robot.zabola.state;


public class StopState extends Timestate{
	public StopState(long duration) {
		super(duration);
	}
	
	public StopState(long duration, Statelike nextState) {
		super(duration, nextState);
	}

	@Override
	public void update(StateContext stateContext) {
		setWheelA(wheelB = 0);
		super.update(stateContext);
	}

}
