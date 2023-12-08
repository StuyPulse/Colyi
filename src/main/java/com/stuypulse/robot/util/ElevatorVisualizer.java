package com.stuypulse.robot.util;

import static com.stuypulse.robot.constants.Settings.Elevator.*;

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

    private double height;
    private double width;

    private double leftRootX;
    private double rightRootX;
    
    // colors
    private Color8Bit white = new Color8Bit(255,255,255);
    private Color8Bit blue = new Color8Bit(0, 0, 255);
    private Color8Bit red = new Color8Bit(255, 0, 0);

    //max height
    private double maxOuterHeight;
    private double maxInnerHeight;

    // increments
    double xincrement = 0.2;
    double yincrement = 0.15;//0.5;
    
    public ElevatorVisualizer() {
        maxOuterHeight = 16; //how much bottom travels 
        maxInnerHeight = 36; //how much bottom travels 

        width = 10;
        height = 8; 
        SmartDashboard.putNumber("Elevator/width", width);
        SmartDashboard.putNumber("Elevator/height", height);
        // width = new SmartNumber("Elevator/width", 10);
        // height = new SmartNumber("Elevator/height", 8);

        double xfromorigin = 3; //where we start elevator 

        leftRootX = xfromorigin;
        rightRootX = width-xfromorigin;
        elevator = new Mechanism2d(width,height); //width x height  //10 x 8

        // root nodes
        
        //outer shell
        leftRoot = elevator.getRoot("left root", leftRootX,0);
        rightRoot = elevator.getRoot("right root", rightRootX,0);

        //first stage 
        firstLeftBottomRoot = elevator.getRoot("first left bottom root", leftRootX+xincrement,0); //3.2
        firstRightBottomRoot = elevator.getRoot("first right bottom root", rightRootX-xincrement,0); //6.8
        firstTopRoot = elevator.getRoot("first top root", leftRootX+xincrement, height-xincrement*6); //3.2,6.5
        
        //second stage
        secondLeftBottomRoot = elevator.getRoot("second left bottom root", leftRootX+2*xincrement,yincrement); //3.4 .5
        secondRightBottomRoot = elevator.getRoot("second right bottom root", rightRootX-2*xincrement,yincrement); //6.6 .5
        secondTopRoot = elevator.getRoot("second top root", leftRootX+2*xincrement,leftRootX/2+yincrement); //3.4 2 //leftRootx/2 is the distance from bottom to top of carriage 
        
        // ligaments

        //outer shell
        rightLigament = new MechanismLigament2d("right ligament", rightRootX, 90, 8, red); //7
        leftLigament = new MechanismLigament2d("left ligament", rightRootX, 90, 8, red); //7

        // first stage
        firstTopLigament = new MechanismLigament2d("elevator top ligament first", leftRootX+3*xincrement, 0,8,blue); //3.6
        firstBottomLigament = new MechanismLigament2d("elevator bottom ligament first", leftRootX+3*xincrement, 0,8,blue); //3.6
        firstLeftLigament = new MechanismLigament2d("elevator left ligament first",rightRootX-xincrement,90, 8, blue); //6.5
        firstRightLigament = new MechanismLigament2d("elevator right ligament first",rightRootX-xincrement, 90, 8, blue); //6.5
        
        // second stage
        secondTopLigament = new MechanismLigament2d("elevator top ligament second", leftRootX+xincrement, 0,8, white); //3.2
        secondBottomLigament = new MechanismLigament2d("elevator bottom ligament second", leftRootX+xincrement, 0,8,white); //3.2
        secondLeftLigament = new MechanismLigament2d("elevator left ligament second", leftRootX/2, 90,8,white); //1.5
        secondRightLigament = new MechanismLigament2d("elevator right ligament second", leftRootX/2, 90,8,white); //1.5

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

    public void setTargetHeight(double newHeight) {
        double changeInHeight = newHeight-height;
        // double percentDone = height / MAX_HEIGHT;

        //outer
        firstLeftBottomRoot.setPosition(leftRootX + xincrement, changeInHeight);
        firstRightBottomRoot.setPosition(rightRootX - xincrement, changeInHeight);
        firstTopRoot.setPosition(leftRootX + xincrement, changeInHeight + (rightRootX- xincrement)); //offset of former legnth of ligament which was former height 

        //inner
        secondLeftBottomRoot.setPosition(leftRootX+2*xincrement, changeInHeight);
        secondRightBottomRoot.setPosition(rightRootX-2*xincrement,changeInHeight);
        secondTopRoot.setPosition(leftRootX+2*xincrement,leftRootX/2+changeInHeight); //former height + change

        /*
        THE FACTS:
        - carriage moves at the same speed RELATIVE to teh inner stage
        - carriage moves at twice the speed of the inner stage releative to teh observer
         (use twice the speed for observer since we have them as separate from each other)

        - carriage not connected to inner stage
        - carria*/
    }
}