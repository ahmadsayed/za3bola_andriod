package com.robot.zabola.behavior;

import com.robot.zabola.state.MoveState;
import com.robot.zabola.state.StateContext;
import com.robot.zabola.state.Statelike;
import com.robot.zabola.state.StopState;


public class MoveForwared extends RobotBehavior {
	StateContext stateContext;
	Statelike move, stop;
	@Override
	public void setup() {
		move = new MoveState(2000l);
		stop = new StopState(2000l, move);
		((MoveState)move).setNextState(stop);
		stateContext = new StateContext();
		stateContext.setState(stop);
	}

	@Override
	public void loop() {
		stateContext.update();
		int wheelA = stateContext.getDifferentialRobotState().getWheelA();
		int wheelB = stateContext.getDifferentialRobotState().getWheelB();
		robot.setWheelA(wheelA);
		robot.setWheelB(wheelB);
		
	}
	
	
}
