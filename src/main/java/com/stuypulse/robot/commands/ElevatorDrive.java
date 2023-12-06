package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.ElevatorImpl;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.IStream;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorDrive extends CommandBase {
    private final ElevatorImpl elevator;
    private IStream velocity;

    public ElevatorDrive(ElevatorImpl elevator, Gamepad gamepad) {
        this.elevator = elevator;

        velocity = IStream.create(gamepad::getLeftY); // left Y is elevator in controls 

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.addTargetHeight(velocity.get());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
