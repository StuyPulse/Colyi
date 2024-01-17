package com.stuypulse.robot.util;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class ElevatorClimberVisualizer {
    private final double WINDOW_WIDTH = 6;
    private final double WINDOW_HEIGHT = 15;
    private final double WINDOW_X_PADDING = 1;

    private final double OUTER_STAGE_HEIGHT = 6;

    private final int LINE_WIDTH = 8;

    private final Mechanism2d elevator;

    // ligaments
    private MechanismLigament2d leftLigament;
    private MechanismLigament2d rightLigament;
    
    private MechanismLigament2d firstTopLigament;
    private MechanismLigament2d firstBottomLigament;
    private MechanismLigament2d firstLeftLigament;
    private MechanismLigament2d firstRightLigament;

    // roots
    private MechanismRoot2d leftRoot;
    private MechanismRoot2d rightRoot;

    private MechanismRoot2d firstRightBottomRoot;
    private MechanismRoot2d firstLeftBottomRoot;
    private MechanismRoot2d firstTopRoot;

    private double leftRootX;
    private double rightRootX;
    
    // colors
    private Color8Bit blue = new Color8Bit(0, 0, 255);
    private Color8Bit red = new Color8Bit(255, 0, 0);

    private MechanismLigament2d getLigament(String name, double length, double angle, Color8Bit color) {
        return new MechanismLigament2d(name, length, angle, LINE_WIDTH, color);
    }

    public ElevatorClimberVisualizer() {
        elevator = new Mechanism2d(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        leftRootX = WINDOW_X_PADDING;
        rightRootX = WINDOW_WIDTH - WINDOW_X_PADDING;

        // root nodes
        
        // outer shell
        leftRoot = elevator.getRoot("left root", leftRootX,0);
        rightRoot = elevator.getRoot("right root", rightRootX,0);

        // first stage 
        firstLeftBottomRoot = elevator.getRoot("first left bottom root", leftRootX, 0);
        firstRightBottomRoot = elevator.getRoot("first right bottom root", rightRootX, 0);
        firstTopRoot = elevator.getRoot("first top root", leftRootX, OUTER_STAGE_HEIGHT);
        
        // ligaments

        //outer shell
        rightLigament = getLigament("right ligament", OUTER_STAGE_HEIGHT, 90, red);
        leftLigament = getLigament("left ligament", OUTER_STAGE_HEIGHT, 90, red);

        // first stage
        firstTopLigament = getLigament("elevator top ligament first", WINDOW_WIDTH - 2 * WINDOW_X_PADDING, 0, blue);
        firstBottomLigament = getLigament("elevator bottom ligament first", WINDOW_WIDTH - 2 * WINDOW_X_PADDING, 0, blue);
        firstLeftLigament = getLigament("elevator left ligament first", OUTER_STAGE_HEIGHT, 90, blue);
        firstRightLigament = getLigament("elevator right ligament first", OUTER_STAGE_HEIGHT, 90, blue);

        //outer shell
        leftRoot.append(leftLigament);
        rightRoot.append(rightLigament);
        
        //first shell 
        firstLeftBottomRoot.append(firstBottomLigament);
        firstLeftBottomRoot.append(firstLeftLigament);
        firstTopRoot.append(firstTopLigament);
        firstRightBottomRoot.append(firstRightLigament);

        SmartDashboard.putData("Elevator", elevator);
    }

    public void setHeight(double newHeight) {
        double percentDone = newHeight / MAX_HEIGHT;

        double firstStageBottomY = OUTER_STAGE_HEIGHT * percentDone;

        // first stage
        firstLeftBottomRoot.setPosition(leftRootX, firstStageBottomY);
        firstRightBottomRoot.setPosition(rightRootX, firstStageBottomY);
        firstTopRoot.setPosition(leftRootX, OUTER_STAGE_HEIGHT + firstStageBottomY);
    }
}