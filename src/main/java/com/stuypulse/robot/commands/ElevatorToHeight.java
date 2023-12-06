package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ElevatorImpl;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorToHeight extends CommandBase {
    private final ElevatorImpl elevator;
    private final double height; 

    private boolean instant;

    public ElevatorToHeight(ElevatorImpl elevator, double height) {
        this.elevator = elevator;
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
            elevator.isReady(MAX_HEIGHT_ERROR);
        }
        return true;
    }
}
