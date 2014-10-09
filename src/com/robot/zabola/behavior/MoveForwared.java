package com.robot.zabola.behavior;

import com.robot.zabola.state.RotateLeftState;
import com.robot.zabola.state.RotateRightState;
import com.robot.zabola.state.StateContext;
import com.robot.zabola.state.Statelike;
import com.robot.zabola.state.StopState;


public class MoveForwared extends RobotBehavior {
	StateContext stateContext;
	Statelike move, stop, endOperation;
	@Override
	public void setup() {
		sensorService.begin();		
		move = new RotateLeftState(500l);
		stop = new RotateRightState(500l, move);
		endOperation = new StopState(1000l);
		
		((RotateLeftState)move).setNextState(stop);
		stateContext = new StateContext();
		
	}

	@Override
	public void loop() {
		stateContext.update();
		lcd1 = String.valueOf(sensorService.getAzimuth());
		lcd2 = stateContext.getOtherData().get("AZIMUTH");
		if (stateContext.getDifferentialRobotState() != null) {
			int wheelA = stateContext.getDifferentialRobotState().getWheelA();
			int wheelB = stateContext.getDifferentialRobotState().getWheelB();
			robot.setWheelA(wheelA);
			robot.setWheelB(wheelB);
		}
		
	}
	
	@Override
	public void button1() {
		stateContext.setState(stop);
	}
	@Override	
	public void button2() {
		stateContext.setState(endOperation);
	}
	@Override
	public void button3() {
		stateContext.getOtherData().put("AZIMUTH", String.valueOf(sensorService.getAzimuth()));
	}
	
}
