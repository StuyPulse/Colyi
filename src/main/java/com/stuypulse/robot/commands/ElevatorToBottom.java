package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Elevator;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorToBottom extends ElevatorToHeight {
    public ElevatorToBottom(Elevator elevator) {
        super(elevator, MIN_HEIGHT);
    }
}
