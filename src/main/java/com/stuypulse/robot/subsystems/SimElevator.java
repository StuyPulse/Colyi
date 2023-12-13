package com.stuypulse.robot.subsystems;

import static com.stuypulse.robot.constants.Settings.Elevator.*;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

public class SimElevator extends Elevator {
    private ElevatorSim sim;

    private double height;
    private double velocity;

    public SimElevator() {
        height = 0.0;
        velocity = 0.0;
        sim = new ElevatorSim(DCMotor.getNEO(2), GEARING,  CARRIAGE_MASS, DRUM_RADIUS, MIN_HEIGHT, MAX_HEIGHT, true);
    }

    @Override
    public double getVelocity() {
        return sim.getVelocityMetersPerSecond();
    }

    @Override
    public double getHeight() {
        return sim.getPositionMeters();
    }

    @Override
    public void simulationPeriodic() {
        sim.update(DT);
        
        height = sim.getPositionMeters();
        velocity = sim.getVelocityMetersPerSecond();
    }
}
