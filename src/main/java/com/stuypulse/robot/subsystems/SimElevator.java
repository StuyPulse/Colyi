package com.stuypulse.robot.subsystems;

import static com.stuypulse.robot.constants.Settings.Elevator.*;
import static com.stuypulse.robot.constants.Settings.FeedForward.*;
import static com.stuypulse.robot.constants.Settings.PID.*;

import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.ElevatorFeedforward;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.MotionProfile;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

public class SimElevator {
    private ElevatorSim sim;

    private double height;
    private double velocity;

    private Controller position;
    private SmartNumber targetHeight;

    public SimElevator() {
        height = 0.0;
        velocity = 0.0;

        targetHeight = new SmartNumber("Target Height", MIN_HEIGHT);

        position = new PIDController(kP, kI, kD)
            .add(new ElevatorFeedforward(kG))
            .setOutputFilter(new MotionProfile(VEL_LIMIT, ACC_LIMIT));

        // sim = new ElevatorSim(DCMotor.getCIM(4), 106.94, 2.5, 1.435, MIN_HEIGHT, MAX_HEIGHT, false);
    }
}
