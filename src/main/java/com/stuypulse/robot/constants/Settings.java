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
        double GEARING = 0.0;
        double CARRIAGE_MASS = 0.0; // kg
        double DRUM_RADIUS = 0.0; // meters

        double MIN_HEIGHT = 0.0;
        double MAX_HEIGHT = 0.0;

        double MAX_HEIGHT_ERROR = 0.0;

        SmartNumber VEL_LIMIT = new SmartNumber("Velocity Limit", 0);
        SmartNumber ACC_LIMIT = new SmartNumber("Acceleration Limit",0);

        public interface Encoder {
            double ENCODER_MULTIPLIER = 1;
        }
    }

    public interface PID {
        SmartNumber kP = new SmartNumber("kP", 0);
        SmartNumber kI = new SmartNumber("kI", 0);
        SmartNumber kD = new SmartNumber("kD", 0);
    }

    public interface FeedForward {
        SmartNumber kG = new SmartNumber("kG", 0);
        SmartNumber kS = new SmartNumber("kS", 0);
        SmartNumber kV = new SmartNumber("kV", 0);
        SmartNumber kA = new SmartNumber("kA", 0);
    }
}
