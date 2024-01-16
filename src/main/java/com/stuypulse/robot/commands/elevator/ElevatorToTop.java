package com.stuypulse.robot.commands.elevator;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorToTop extends ElevatorToHeight {

    public ElevatorToTop() {
        super(MAX_HEIGHT);
    }
}
