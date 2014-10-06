package com.robot.zabola.state;

import java.util.Date;
import java.util.HashMap;

public class StateContext {
	private HashMap<String, String> otherData = new HashMap<String, String>();
	
	public HashMap<String, String> getOtherData() {
		return otherData;
	}


	public void setOtherData(HashMap<String, String> otherData) {
		this.otherData = otherData;
	}

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
		if (myState != null) {
			myState.update(this);
		}
	}
	
	public DifferentialRobotState getDifferentialRobotState() {
		if (myState instanceof DifferentialRobotState) {
			return (DifferentialRobotState) myState;
		} else {
			return null;
		}
	}
}
