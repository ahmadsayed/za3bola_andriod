package com.robot.zabola.state;

import java.util.Date;

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
		if ((new Date().getTime() -  stateContext.getTimestamp().getTime()) >=duration) {
			stateContext.setTimestamp(new Date());
			stateContext.setState(getNextState());
		}
	}

}
