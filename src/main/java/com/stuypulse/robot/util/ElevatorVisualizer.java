package com.stuypulse.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class ElevatorVisualizer {
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
    
    // colors
    private Color8Bit white = new Color8Bit(255,255,255);
    private Color8Bit blue = new Color8Bit(0, 0, 255);
    private Color8Bit red = new Color8Bit(255, 0, 0);

    //max height
    private double maxOuterHeight;
    private double maxInnerHeight;
    
    public ElevatorVisualizer() {
        elevator = new Mechanism2d(10,8); //width x height  //30 x 46 
        maxOuterHeight = 16; //how much bottom travels 
        maxInnerHeight = 36; //how much bottom travels 

        // root nodes
        
        //outer shell
        leftRoot = elevator.getRoot("left root", 3,0);
        rightRoot = elevator.getRoot("right root", 7,0);

        //first stage 
        firstLeftBottomRoot = elevator.getRoot("first left bottom root", 3.2,0);
        firstRightBottomRoot = elevator.getRoot("first right bottom root", 6.8,0);
        firstTopRoot = elevator.getRoot("first top root", 3.2,6.5);
        
        //second stage
        secondLeftBottomRoot = elevator.getRoot("second left bottom root", 3.4,.5);
        secondRightBottomRoot = elevator.getRoot("second right bottom root", 6.6,.5);
        secondTopRoot = elevator.getRoot("second top root", 3.4,2);
        
        // ligaments

        //outer shell
        rightLigament = new MechanismLigament2d("right ligament", 7, 90, 8, red);
        leftLigament = new MechanismLigament2d("left ligament", 7, 90, 8, red);

        // first stage
        firstTopLigament = new MechanismLigament2d("elevator top ligament first", 3.6, 0,8,blue);
        firstBottomLigament = new MechanismLigament2d("elevator bottom ligament first", 3.6, 0,8,blue);
        firstLeftLigament = new MechanismLigament2d("elevator left ligament first",6.5,90, 8, blue);
        firstRightLigament = new MechanismLigament2d("elevator right ligament first",6.5, 90, 8, blue);
        
        // second stage
        secondTopLigament = new MechanismLigament2d("elevator top ligament second", 3.2, 0,8, white);
        secondBottomLigament = new MechanismLigament2d("elevator bottom ligament second", 3.2, 0,8,white);
        secondLeftLigament = new MechanismLigament2d("elevator left ligament second", 1.5, 90,8,white);
        secondRightLigament = new MechanismLigament2d("elevator right ligament second", 1.5, 90,8,white);

        //outer shell
        leftRoot.append(leftLigament);
        rightRoot.append(rightLigament);
        
        //first shell 
        firstLeftBottomRoot.append(firstBottomLigament); //bottom horizontal
        firstLeftBottomRoot.append(firstLeftLigament); //left vertical
        firstTopRoot.append(firstTopLigament); //top horiontal
        firstRightBottomRoot.append(firstRightLigament); // right vertical
        
        // second shell
        secondLeftBottomRoot.append(secondBottomLigament);
        secondLeftBottomRoot.append(secondLeftLigament);
        secondTopRoot.append(secondTopLigament);
        secondRightBottomRoot.append(secondRightLigament);

        SmartDashboard.putData("Elevator", elevator);
    }

    public void setTargetHeight(double height) {
        //outer
        // int double percentdone = 
        //inner
    }
}