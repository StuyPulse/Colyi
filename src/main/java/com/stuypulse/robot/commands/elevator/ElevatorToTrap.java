package com.stuypulse.robot.commands.elevator;

import static com.stuypulse.robot.constants.Settings.Elevator.*;


public class ElevatorToTrap extends ElevatorToHeight {
    public ElevatorToTrap() {
        super(TRAP_HEIGHT);
    }
}
