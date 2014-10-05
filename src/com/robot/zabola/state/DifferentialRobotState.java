package com.robot.zabola.state;

public abstract class DifferentialRobotState implements Statelike{

	private int wheelA;
	int wheelB;
	private Statelike nextState;
	public DifferentialRobotState (Statelike nextState) {
		this.setNextState(nextState);
	}
	
	public int getWheelB() {
		return wheelB;
	}
	public void setWheelB(int wheelB) {
		this.wheelB = wheelB;
	}

	public int getWheelA() {
		return wheelA;
	}

	public void setWheelA(int wheelA) {
		this.wheelA = wheelA;
	}

	public Statelike getNextState() {
		return nextState;
	}

	public void setNextState(Statelike nextState) {
		this.nextState = nextState;
	}

}
