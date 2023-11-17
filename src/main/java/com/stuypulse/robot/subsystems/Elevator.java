package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.ElevatorFeedforward;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.MotionProfile;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.stuypulse.robot.constants.Settings.Elevator.*;
import static com.stuypulse.robot.constants.Settings.Elevator.Encoder.*;
import static com.stuypulse.robot.constants.Settings.PID.*;

import static com.stuypulse.robot.constants.Settings.FeedForward.*;

public class Elevator extends SubsystemBase {
    // hardware
    public CANSparkMax leftMotor;
    public CANSparkMax rightMotor;

    public RelativeEncoder leftEncoder;
    public RelativeEncoder rightEncoder;

    public DigitalInput topLimit;
    public DigitalInput bottomLimit;

    // control
    public Controller position;

    public SmartNumber targetHeight;

    public Elevator() {
        // motors
        leftMotor = new CANSparkMax(Ports.Elevator.LEFT, MotorType.kBrushless);
        rightMotor = new CANSparkMax(Ports.Elevator.RIGHT, MotorType.kBrushless);

        // encoders
        leftEncoder = leftMotor.getEncoder();
        rightEncoder = rightMotor.getEncoder();
        
        // limit switches
        topLimit = new DigitalInput(Ports.Elevator.TOP_LIMIT_SWITCH);
        bottomLimit = new DigitalInput(Ports.Elevator.BOTTOM_LIMIT_SWITCH);

        // control
        position = new PIDController(kP, kI, kD)
            .add(new ElevatorFeedforward(kG))
            .setOutputFilter(new MotionProfile(VEL_LIMIT, ACC_LIMIT));
    
        targetHeight = new SmartNumber("Target Height", MIN_HEIGHT);
    }

    public boolean atTop() {
        return !topLimit.get();
    }

    public boolean atBottom() {
        return !bottomLimit.get();
    }

    public double getTargetHeight() {
        return targetHeight.get();
    }

    public void setTargetHeight(double height) {
        targetHeight.set(height);
    }

    public double getVelocity() { // average of the two? 
        return (rightEncoder.getVelocity() + leftEncoder.getVelocity()) / 2 * ENCODER_MULTIPLIER;
    }

    public double getHeight() {
        return (leftEncoder.getPosition() + rightEncoder.getPosition()) / 2 * ENCODER_MULTIPLIER;
    }

    public void setVoltage(double voltage) {
        if (atTop() && voltage > 0) {
            DriverStation.reportWarning("Top Limit Reached", false);
            
            voltage = 0.0;
            
            leftEncoder.setPosition(MAX_HEIGHT);
            rightEncoder.setPosition(MAX_HEIGHT);
        } else if (atBottom() && voltage < 0) {
            DriverStation.reportWarning("Bottom Limit Reached", false);

            voltage = 0.0;
            
            leftEncoder.setPosition(MIN_HEIGHT);
            rightEncoder.setPosition(MIN_HEIGHT);
        }

        rightMotor.setVoltage(voltage);
        leftMotor.setVoltage(voltage);
    }

    public final boolean isReady(double error) {
        return Math.abs(getTargetHeight() - getHeight()) < error;
    }

    public void addTargetHeight(double delta) {
        setTargetHeight(getTargetHeight() + delta);
    }

    public void periodic() {
        setVoltage(position.update(getHeight(), getTargetHeight()));
        
        SmartDashboard.putBoolean("Elevator/at Top", atTop());
        SmartDashboard.putBoolean("Elevator/at Bottom", atBottom());
        
        SmartDashboard.putNumber("Elevator/Height", getHeight());
        SmartDashboard.putNumber("Elevator/Velocity", getVelocity());
    }
}
