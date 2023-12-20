package com.stuypulse.robot.util;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class ElevatorVisualizer {
    private final double WINDOW_WIDTH = 6;
    private final double WINDOW_HEIGHT = 15;
    private final double WINDOW_X_PADDING = 1;

    private final double OUTER_STAGE_HEIGHT = 6;
    private final double INNER_STAGE_HEIGHT = 1.5;

    private final int LINE_WIDTH = 8;

    private final double STAGE_SPACING = 0.2;

    private final Mechanism2d elevator;

    // ligaments
    private MechanismLigament2d leftLigament;
    private MechanismLigament2d rightLigament;
    
    private MechanismLigament2d firstTopLigament;
    private MechanismLigament2d firstBottomLigament;
    private MechanismLigament2d firstLeftLigament;
    private MechanismLigament2d firstRightLigament;

    private MechanismLigament2d secondTopLigament;
    private MechanismLigament2d secondBottomLigament;
    private MechanismLigament2d secondLeftLigament;
    private MechanismLigament2d secondRightLigament;

    // roots
    private MechanismRoot2d leftRoot;
    private MechanismRoot2d rightRoot;

    private MechanismRoot2d firstRightBottomRoot;
    private MechanismRoot2d firstLeftBottomRoot;
    private MechanismRoot2d firstTopRoot;

    private MechanismRoot2d secondLeftBottomRoot;
    private MechanismRoot2d secondRightBottomRoot;
    private MechanismRoot2d secondTopRoot;

    private double leftRootX;
    private double rightRootX;
    
    // colors
    private Color8Bit white = new Color8Bit(255,255,255);
    private Color8Bit blue = new Color8Bit(0, 0, 255);
    private Color8Bit red = new Color8Bit(255, 0, 0);

    private MechanismLigament2d getLigament(String name, double length, double angle, Color8Bit color) {
        return new MechanismLigament2d(name, length, angle, LINE_WIDTH, color);
    }

    public ElevatorVisualizer() {
        elevator = new Mechanism2d(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        leftRootX = WINDOW_X_PADDING;
        rightRootX = WINDOW_WIDTH - WINDOW_X_PADDING;

        // root nodes
        
        // outer shell
        leftRoot = elevator.getRoot("left root", leftRootX,0);
        rightRoot = elevator.getRoot("right root", rightRootX,0);

        // first stage 
        firstLeftBottomRoot = elevator.getRoot("first left bottom root", leftRootX + STAGE_SPACING, 0);
        firstRightBottomRoot = elevator.getRoot("first right bottom root", rightRootX - STAGE_SPACING, 0);
        firstTopRoot = elevator.getRoot("first top root", leftRootX + STAGE_SPACING, OUTER_STAGE_HEIGHT);
        
        //second stage
        secondLeftBottomRoot = elevator.getRoot("second left bottom root", leftRootX + 2 * STAGE_SPACING, STAGE_SPACING);
        secondRightBottomRoot = elevator.getRoot("second right bottom root", rightRootX - 2 * STAGE_SPACING, STAGE_SPACING);
        secondTopRoot = elevator.getRoot("second top root", leftRootX + 2 * STAGE_SPACING, INNER_STAGE_HEIGHT + STAGE_SPACING);
        
        // ligaments

        //outer shell
        rightLigament = getLigament("right ligament", OUTER_STAGE_HEIGHT, 90, red);
        leftLigament = getLigament("left ligament", OUTER_STAGE_HEIGHT, 90, red);

        // first stage
        firstTopLigament = getLigament("elevator top ligament first", WINDOW_WIDTH - 2 * (WINDOW_X_PADDING + STAGE_SPACING), 0, blue);
        firstBottomLigament = getLigament("elevator bottom ligament first", WINDOW_WIDTH - 2 * (WINDOW_X_PADDING + STAGE_SPACING), 0, blue);
        firstLeftLigament = getLigament("elevator left ligament first", OUTER_STAGE_HEIGHT, 90, blue);
        firstRightLigament = getLigament("elevator right ligament first", OUTER_STAGE_HEIGHT, 90, blue);
        
        // second stage
        secondTopLigament = getLigament("elevator top ligament second", WINDOW_WIDTH - 2 * (WINDOW_X_PADDING + 2 * STAGE_SPACING), 0, white);
        secondBottomLigament = getLigament("elevator bottom ligament second", WINDOW_WIDTH - 2 * (WINDOW_X_PADDING + 2 * STAGE_SPACING), 0, white);
        secondLeftLigament = getLigament("elevator left ligament second", INNER_STAGE_HEIGHT, 90, white);
        secondRightLigament = getLigament("elevator right ligament second", INNER_STAGE_HEIGHT, 90, white);

        //outer shell
        leftRoot.append(leftLigament);
        rightRoot.append(rightLigament);
        
        //first shell 
        firstLeftBottomRoot.append(firstBottomLigament);
        firstLeftBottomRoot.append(firstLeftLigament);
        firstTopRoot.append(firstTopLigament);
        firstRightBottomRoot.append(firstRightLigament);
        
        // second shell
        secondLeftBottomRoot.append(secondBottomLigament);
        secondLeftBottomRoot.append(secondLeftLigament);
        secondTopRoot.append(secondTopLigament);
        secondRightBottomRoot.append(secondRightLigament);

        SmartDashboard.putData("Elevator", elevator);
    }

    public void setHeight(double newHeight) {
        double percentDone = newHeight / MAX_HEIGHT;

        double firstStageBottomY = OUTER_STAGE_HEIGHT * percentDone;

        // first stage
        firstLeftBottomRoot.setPosition(leftRootX + STAGE_SPACING, firstStageBottomY);
        firstRightBottomRoot.setPosition(rightRootX - STAGE_SPACING, firstStageBottomY);
        firstTopRoot.setPosition(leftRootX + STAGE_SPACING, OUTER_STAGE_HEIGHT + firstStageBottomY);
        
        double secondStageBottomY = firstStageBottomY + (OUTER_STAGE_HEIGHT - INNER_STAGE_HEIGHT) * percentDone + STAGE_SPACING;

        //second stage
        secondLeftBottomRoot.setPosition(leftRootX + 2 * STAGE_SPACING, secondStageBottomY);
        secondRightBottomRoot.setPosition(rightRootX - 2 * STAGE_SPACING, secondStageBottomY);
        secondTopRoot.setPosition(leftRootX + 2 * STAGE_SPACING, INNER_STAGE_HEIGHT + secondStageBottomY);
    }
}