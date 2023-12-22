package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.constants.Ports;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import static com.stuypulse.robot.constants.Settings.Elevator.*;
import static com.stuypulse.robot.constants.Settings.Elevator.Encoder.*;

public class ElevatorImpl extends Elevator {
    // hardware
    public CANSparkMax leftMotor;
    public CANSparkMax rightMotor;

    public RelativeEncoder leftEncoder;
    public RelativeEncoder rightEncoder;

    public DigitalInput topLimit;
    public DigitalInput bottomLimit;

    public ElevatorImpl() {
        // motors
        leftMotor = new CANSparkMax(Ports.Elevator.LEFT, MotorType.kBrushless);
        rightMotor = new CANSparkMax(Ports.Elevator.RIGHT, MotorType.kBrushless);

        // encoders
        leftEncoder = leftMotor.getEncoder();
        rightEncoder = rightMotor.getEncoder();
        
        // limit switches
        topLimit = new DigitalInput(Ports.Elevator.TOP_LIMIT_SWITCH);
        bottomLimit = new DigitalInput(Ports.Elevator.BOTTOM_LIMIT_SWITCH);
    }

    public boolean atTop() {
        return !topLimit.get();
    }

    public boolean atBottom() {
        return !bottomLimit.get();
    }

    @Override
    public double getVelocity() { // average of the two? 
        return (rightEncoder.getVelocity() + leftEncoder.getVelocity()) / 2 * ENCODER_MULTIPLIER;
    }

    @Override
    public double getHeight() {
        return (leftEncoder.getPosition() + rightEncoder.getPosition()) / 2 * ENCODER_MULTIPLIER;
    }

    @Override
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

    public void addTargetHeight(double delta) {
        setTargetHeight(getTargetHeight() + delta);
    }
}
