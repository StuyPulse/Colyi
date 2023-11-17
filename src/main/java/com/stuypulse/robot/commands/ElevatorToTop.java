package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Elevator;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorToTop extends ElevatorToHeight {
    public ElevatorToTop(Elevator elevator) {
        super(elevator, MAX_HEIGHT);
    }
}
