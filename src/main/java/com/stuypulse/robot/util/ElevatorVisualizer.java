package com.stuypulse.robot.util;

import com.stuypulse.stuylib.network.SmartNumber;

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

    private SmartNumber height;
    private SmartNumber width;

    private double leftRootx;
    private double rightRootx;
    
    // colors
    private Color8Bit white = new Color8Bit(255,255,255);
    private Color8Bit blue = new Color8Bit(0, 0, 255);
    private Color8Bit red = new Color8Bit(255, 0, 0);

    //max height
    private double maxOuterHeight;
    private double maxInnerHeight;
    
    public ElevatorVisualizer() {
        maxOuterHeight = 16; //how much bottom travels 
        maxInnerHeight = 36; //how much bottom travels 
        width = new SmartNumber("Elevator/width", 10);
        height = new SmartNumber("Elevator/height", 8);
        double xincrement = 0.2;
        double yincrement = 0.5;
        double xfromorigin = 3; //where we start elevator 
        leftRootx = xfromorigin;
        rightRootx = width.getAsDouble()-xfromorigin;
        elevator = new Mechanism2d(width.getAsDouble(),height.getAsDouble()); //width x height  //10 x 8

        // root nodes
        
        //outer shell
        leftRoot = elevator.getRoot("left root", leftRootx,0);
        rightRoot = elevator.getRoot("right root", rightRootx,0);

        //first stage 
        firstLeftBottomRoot = elevator.getRoot("first left bottom root", leftRootx+xincrement,0); //3.2
        firstRightBottomRoot = elevator.getRoot("first right bottom root", rightRootx-xincrement,0); //6.8
        firstTopRoot = elevator.getRoot("first top root", leftRootx+xincrement,height.getAsDouble()-yincrement); //3.2,6.5
        
        //second stage
        secondLeftBottomRoot = elevator.getRoot("second left bottom root", leftRootx+2*xincrement,yincrement); //3.4 .5
        secondRightBottomRoot = elevator.getRoot("second right bottom root", rightRootx-2*xincrement,yincrement); //6.6 .5
        secondTopRoot = elevator.getRoot("second top root", leftRootx+2*xincrement,yincrement*4); //3.4 2
        
        // ligaments

        //outer shell
        rightLigament = new MechanismLigament2d("right ligament", rightRootx, 90, 8, red); //7
        leftLigament = new MechanismLigament2d("left ligament", rightRootx, 90, 8, red); //7

        // first stage
        firstTopLigament = new MechanismLigament2d("elevator top ligament first", leftRootx+3*xincrement, 0,8,blue); //3.6
        firstBottomLigament = new MechanismLigament2d("elevator bottom ligament first", leftRootx+3*xincrement, 0,8,blue); //3.6
        firstLeftLigament = new MechanismLigament2d("elevator left ligament first",rightRootx-xincrement,90, 8, blue); //6.5
        firstRightLigament = new MechanismLigament2d("elevator right ligament first",rightRootx-xincrement, 90, 8, blue); //6.5
        
        // second stage
        secondTopLigament = new MechanismLigament2d("elevator top ligament second", leftRootx+xincrement, 0,8, white); //3.2
        secondBottomLigament = new MechanismLigament2d("elevator bottom ligament second", leftRootx+xincrement, 0,8,white); //3.2
        secondLeftLigament = new MechanismLigament2d("elevator left ligament second", leftRootx/2, 90,8,white); //1.5
        secondRightLigament = new MechanismLigament2d("elevator right ligament second", leftRootx/2, 90,8,white); //1.5

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
        // SmartDashboard.putNumber("Elevator/width", width);
        // SmartDashboard.putNumber("Elevator/height", height);
        SmartDashboard.putNumber("Elevator/starting x", xfromorigin);
    }

    public void setTargetHeight(double height) {
        //outer
        // int double percentdone = 
        //inner
    }
}