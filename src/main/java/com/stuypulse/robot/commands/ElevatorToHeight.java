package com.stuypulse.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

import com.stuypulse.robot.subsystems.Elevator;

public class ElevatorToHeight extends CommandBase {
    private final Elevator elevator;
    private final double height; 

    private boolean instant;

    public ElevatorToHeight(double height) {
        elevator = Elevator.getInstance();
        this.height = height;

        instant = true;

        addRequirements(elevator);
    }

    public final ElevatorToHeight untilReady() {
        instant = false;
        return this;
    }

    @Override
    public void initialize() {
        elevator.setTargetHeight(height);
    }

    @Override
    public boolean isFinished() {
        if (!instant) {
            return elevator.isReady(MAX_HEIGHT_ERROR);
        }
        return true;
    }
}
