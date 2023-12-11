package com.stuypulse.robot.commands;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorToBottom extends ElevatorToHeight {
    public ElevatorToBottom() {
        super(MIN_HEIGHT);
    }
}
