package com.robot.zabola.state;

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

}
