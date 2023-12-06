package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ElevatorImpl;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorToTop extends ElevatorToHeight {
    public ElevatorToTop(ElevatorImpl elevator) {
        super(elevator, MAX_HEIGHT);
    }
}
