/************************ PROJECT PHIL ************************/
/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved.*/
/* This work is licensed under the terms of the MIT license.  */
/**************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.network.SmartNumber;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public interface Settings {
    public interface Elevator {
        double DT = 0.02; // time between each simulation update
        double GEARING = 9; // ~9:1
        double CARRIAGE_MASS = 10; // kg
        double DRUM_RADIUS = 0.025; // meters 

        double MIN_HEIGHT = 0;
        double MAX_HEIGHT = 1.8475325; // meters 

        double MAX_HEIGHT_ERROR = 0.03;

        SmartNumber VEL_LIMIT = new SmartNumber("Velocity Limit", 3);
        SmartNumber ACC_LIMIT = new SmartNumber("Acceleration Limit", 2);

        public interface Encoder {
            double ENCODER_MULTIPLIER = 1;
        }
    }

    public interface PID {
        SmartNumber kP = new SmartNumber("kP", 3);
        SmartNumber kI = new SmartNumber("kI", 0.5);
        SmartNumber kD = new SmartNumber("kD", 0);
    }

    public interface FeedForward {
        SmartNumber kG = new SmartNumber("kG", 5.0);
        SmartNumber kS = new SmartNumber("kS", 0.5);
        SmartNumber kV = new SmartNumber("kV", 5.0);
        SmartNumber kA = new SmartNumber("kA", 0.1);
    }
}
