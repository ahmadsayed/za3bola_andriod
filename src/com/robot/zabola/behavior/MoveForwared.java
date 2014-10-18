package com.robot.zabola.behavior;

import com.robot.zabola.state.RotateLeftState;
import com.robot.zabola.state.RotateRightState;
import com.robot.zabola.state.StateContext;
import com.robot.zabola.state.Statelike;
import com.robot.zabola.state.StopState;


public class MoveForwared extends RobotBehavior {
	StateContext stateContext;
	Statelike left, right, endOperation;
	@Override
	public void setup() {
		sensorService.begin();		
		left = new RotateLeftState(1000l);
		right = new RotateRightState(1000l, left);
		endOperation = new StopState(1000l);		
		((RotateLeftState)left).setNextState(right);
		stateContext = new StateContext();
		
	}

	@Override
	public void loop() {
		stateContext.update();
		if (stateContext.getDifferentialRobotState() != null) {
			int wheelA = stateContext.getDifferentialRobotState().getWheelA();
			int wheelB = stateContext.getDifferentialRobotState().getWheelB();
			robot.setWheelA(wheelA);
			robot.setWheelB(wheelB);
		}
		
	}
	
	@Override
	public void button1() {
		stateContext.setState(left);
	}
	@Override	
	public void button2() {
		stateContext.setState(endOperation);
	}
	
}
