package com.stuypulse.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.stuypulse.robot.constants.Ports;
import com.stuypulse.robot.constants.Settings.Elevator.Encoder;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import static com.stuypulse.robot.constants.Settings.Elevator.*;
import static com.stuypulse.robot.constants.Settings.Elevator.Encoder.*;

public class ElevatorImpl extends Elevator {
    // hardware
    public CANSparkMax leftMotor;
    public CANSparkMax rightMotor;
    public CANSparkMax motor;

    public RelativeEncoder leftEncoder;
    public RelativeEncoder rightEncoder;
    public RelativeEncoder encoder;

    public DigitalInput topLimit;
    public DigitalInput bottomLimit;

    public ElevatorImpl() {

        // motors
        motor = new CANSparkMax(Ports.Elevator.MOTOR, MotorType.kBrushless);
        
        encoder = motor.getEncoder();
        encoder.setPositionConversionFactor(Encoder.ENCODER_RATIO);
        encoder.setVelocityConversionFactor(Encoder.ENCODER_RATIO / 60.0);

        
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
        return encoder.getVelocity() * ENCODER_MULTIPLIER;
    }

    @Override
    public double getHeight() {
        return encoder.getPosition() * ENCODER_MULTIPLIER;
    }

    @Override
    public void setVoltage(double voltage) {
        if (atTop() && voltage > 0) {
            DriverStation.reportWarning("Top Limit Reached", false);
            
            voltage = 0.0;
            
            encoder.setPosition(MAX_HEIGHT);
        } else if (atBottom() && voltage < 0) {
            DriverStation.reportWarning("Bottom Limit Reached", false);

            voltage = 0.0;

            encoder.setPosition(MIN_HEIGHT);
        }

        motor.setVoltage(voltage);
    }

    public void addTargetHeight(double delta) {
        setTargetHeight(getTargetHeight() + delta);
    }
}
