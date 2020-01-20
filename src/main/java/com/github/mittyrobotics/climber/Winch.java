package com.github.mittyrobotics.climber;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Winch extends SubsystemBase {
    private static Winch instance = new Winch();

    private CANSparkMax leftSpark, rightSpark;
    private CANPIDController controller;
    private RobotSide side;

    private Winch() {
        super();
    }

    public static Winch getInstance() {
        return instance;
    }

    public void initHardware(RobotSide side) {
        this.side = side;
        leftSpark = new CANSparkMax(Constants.LEFT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark = new CANSparkMax(Constants.RIGHT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftSpark.restoreFactoryDefaults();
        rightSpark.restoreFactoryDefaults();

        if (side == RobotSide.LEFT) {
            controller = new CANPIDController(leftSpark);
        }
        else {
            controller = new CANPIDController(rightSpark);
        }

        // setup PID
        controller.setP(Constants.WINCH_PID_VALUES[0]);
        controller.setI(Constants.WINCH_PID_VALUES[1]);
        controller.setD(Constants.WINCH_PID_VALUES[2]);
        controller.setOutputRange(-1*Constants.PID_OUTPUT_RANGE, Constants.PID_OUTPUT_RANGE);
    }

    //TO DO change to use the getPIDController() function instead of making a new one
    //public CANPIDController getLeftController(){
//        return leftController;
//    }

    public CANEncoder getLeftEncoder(){
        return leftSpark.getEncoder();
    }

    //TO DO change to use the getPIDController() function instead of making a new one
//    public CANPIDController getRightController(){
//        return rightController;
//    }

    public CANPIDController getPIDController() {
        return controller;
    }

    public CANEncoder getRightEncoder(){
        return rightSpark.getEncoder();
    }

    public void setReference(double tempPos) {
        controller.setReference(tempPos, ControlType.kPosition);
    }

    public CANEncoder getEncoder() {
        if(side == RobotSide.LEFT) {
            return leftSpark.getEncoder();
        }
        else {
            return rightSpark.getEncoder();
        }
    }
}

