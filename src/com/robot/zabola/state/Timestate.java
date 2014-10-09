package com.robot.zabola.state;

import java.util.Date;

public abstract class Timestate extends DifferentialRobotState {
	long duration;
	
	public Timestate(long duration) {		
		super(null);
		setDuration(duration);		
	}
	public Timestate(long duration, Statelike nextState) {
		super(nextState);
		setDuration(duration);		
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	@Override
	public void update(StateContext stateContext) {
		
		if ((new Date().getTime() -  stateContext.getTimestamp().getTime()) >=duration) {
			stateContext.setTimestamp(new Date());
			stateContext.setState(getNextState());
		}
	}

}
