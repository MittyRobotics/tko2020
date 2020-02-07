package com.GitHub.mittyrobotics.climber;

import com.GitHub.mittyrobotics.Robot;
import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Winch extends SubsystemBase {
    private static Winch instance;

    private CANSparkMax leftSpark, rightSpark;

    private Winch() {
        super();
    }

    public static Winch getInstance() {
        if(instance == null){
            instance = new Winch();
        }
        return instance;
    }

    public void initHardware() {

        leftSpark = new CANSparkMax(Constants.LEFT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark = new CANSparkMax(Constants.RIGHT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftSpark.restoreFactoryDefaults();
        rightSpark.restoreFactoryDefaults();


        // setup PID
        /*controller.setP(Constants.WINCH_PID_VALUES[0]);
        controller.setI(Constants.WINCH_PID_VALUES[1]);
        controller.setD(Constants.WINCH_PID_VALUES[2]);
        controller.setOutputRange(-1*Constants.PID_OUTPUT_RANGE, Constants.PID_OUTPUT_RANGE);*/

        leftSpark.getPIDController().setP(Constants.WINCH_PID_VALUES[0]);
        leftSpark.getPIDController().setI(Constants.WINCH_PID_VALUES[1]);
        leftSpark.getPIDController().setD(Constants.WINCH_PID_VALUES[2]);
        leftSpark.getPIDController().setOutputRange(-1*Constants.PID_OUTPUT_RANGE, Constants.PID_OUTPUT_RANGE);

        rightSpark.getPIDController().setP(Constants.WINCH_PID_VALUES[0]);
        rightSpark.getPIDController().setI(Constants.WINCH_PID_VALUES[1]);
        rightSpark.getPIDController().setD(Constants.WINCH_PID_VALUES[2]);
        leftSpark.getPIDController().setOutputRange(-1*Constants.PID_OUTPUT_RANGE, Constants.PID_OUTPUT_RANGE);

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

/*    public CANPIDController getPIDController() {
        return controller;
    }*/

    public CANEncoder getRightEncoder(){
        return rightSpark.getEncoder();
    }

    public void setReference(double tempPos, RobotSide side) {
        if(side == RobotSide.LEFT) {
            leftSpark.getPIDController().setReference(tempPos, ControlType.kPosition);
        } else {
            rightSpark.getPIDController().setReference(tempPos, ControlType.kPosition);
        }
    }

    public CANEncoder getEncoder(RobotSide side) {
        if(side == RobotSide.LEFT) {
            return leftSpark.getEncoder();
        }
        else {
            return rightSpark.getEncoder();
        }
    }

    public double getEncoderTicks(RobotSide side) {
        if(side == RobotSide.LEFT) {
            return leftSpark.getEncoder().getPosition();
        } else {
            return rightSpark.getEncoder().getPosition();
        }
    }


    public void setSpeed(double speed, RobotSide side) {
        if(side == RobotSide.LEFT) {
            leftSpark.set(speed);
        } else {
            rightSpark.set(speed);
        }
    }

}

