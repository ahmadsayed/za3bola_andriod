package com.robot.zabola.state;


public class MoveState extends Timestate {
	
	public MoveState(long duration) {
		super(duration);
	}
	
	public MoveState(long duration, Statelike nextState) {
		super(duration, nextState);
	}

	@Override
	public void update(StateContext stateContext) {
		setWheelA(wheelB = 255);
		super.update(stateContext);

	}

}
