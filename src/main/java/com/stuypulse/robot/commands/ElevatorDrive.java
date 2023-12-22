package com.stuypulse.robot.commands;

import com.stuypulse.robot.subsystems.Elevator;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.IStream;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

public class ElevatorDrive extends CommandBase {
    private final Elevator elevator;
    private IStream velocity;

    public ElevatorDrive(Gamepad gamepad) {
        elevator = Elevator.getInstance();

        velocity = IStream.create(gamepad::getLeftY)
            .filtered(x -> x * VEL_LIMIT.get()); // left Y is elevator in controls 

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setTargetHeight(elevator.getHeight() + velocity.get());
        SmartDashboard.putNumber("Gamepad Velocity", velocity.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
