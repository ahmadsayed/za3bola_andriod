package com.robot.zabola.state;

import java.util.Date;

public class StopState extends Timestate{

	public StopState(long duration, Statelike nextState) {
		super(duration, nextState);
	}

	@Override
	public void update(StateContext stateContext) {
		setWheelA(wheelB = 0);
		if ((new Date().getTime() - stateContext.getTimestamp().getTime()) >= duration) {
			stateContext.setTimestamp(new Date());
			stateContext.setState(getNextState());
		}
	}

}
