package com.robot.zabola.state;

import java.util.Date;

public class StateContext {
	
	private Date timestamp = new Date(); 
	
	private Statelike myState;
    
    public StateContext () {
    }
    
    
    public StateContext(Statelike state) {    	
    	setState(state);
    }
    
    public  void setState(Statelike myState) {
    	this.myState = myState;
    }
    
    public  Statelike getState() {
    	return myState;
    }
    public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
    
	public void update() {
		myState.update(this);
	}
	
	public DifferentialRobotState getDifferentialRobotState() {
		if (myState instanceof DifferentialRobotState) {
			return (DifferentialRobotState) myState;
		} else {
			return null;
		}
	}
}
