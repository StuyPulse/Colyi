package com.stuypulse.robot.subsystems;

import static com.stuypulse.robot.constants.Settings.Elevator.*;
import static com.stuypulse.robot.constants.Settings.FeedForward.kG;
import static com.stuypulse.robot.constants.Settings.PID.*;

import com.stuypulse.robot.util.ElevatorVisualizer;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.ElevatorFeedforward;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.MotionProfile;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Elevator extends SubsystemBase {
    // singleton
    private static final Elevator instance;

    static {
        if (RobotBase.isReal()) {
            instance = new ElevatorImpl();
        } else {
            instance = new SimElevator();
        }
    }    

    public static Elevator getInstance() {
        return instance;
    }

    // elevator visualizer
    ElevatorVisualizer elevatorVisualizer = new ElevatorVisualizer();

    // control
    public Controller position;
    public SmartNumber targetHeight;

    public Elevator() {
        // control
        position = new PIDController(kP, kI, kD)
            .add(new ElevatorFeedforward(kG))
            .setOutputFilter(new MotionProfile(VEL_LIMIT, ACC_LIMIT));
    
        targetHeight = new SmartNumber("Target Height", MIN_HEIGHT);
    }

    public double getTargetHeight() {
        return targetHeight.get();
    }

    public void setTargetHeight(double height) {
        targetHeight.set(height);
    }

    public final boolean isReady(double error) {
        return Math.abs(getTargetHeight() - getHeight()) < error;
    }

    public abstract double getVelocity();
    public abstract double getHeight();

    @Override
    public void periodic() {
        elevatorVisualizer.setHeight(targetHeight.getAsDouble());
    }
}
