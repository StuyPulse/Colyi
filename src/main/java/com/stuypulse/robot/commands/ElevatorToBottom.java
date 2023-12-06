package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ElevatorImpl;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorToBottom extends ElevatorToHeight {
    public ElevatorToBottom(ElevatorImpl elevator) {
        super(elevator, MIN_HEIGHT);
    }
}
