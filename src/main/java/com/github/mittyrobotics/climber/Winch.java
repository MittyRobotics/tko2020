package com.github.mittyrobotics.climber;

import com.github.mittyrobotics.interfaces.ISubsystem;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class  Winch extends SubsystemBase implements ISubsystem {
    private static Winch instance;

    private CANSparkMax leftSpark, rightSpark;
    private Servo leftActuator, rightActuator;

    private Winch() {
        super();
    }

    public static Winch getInstance() {
        if (instance == null) {
            instance = new Winch();
        }
        return instance;
    }

    @Override
    public void initHardware() {
        leftSpark = new CANSparkMax(Constants.LEFT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark = new CANSparkMax(Constants.RIGHT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSpark.restoreFactoryDefaults();
        rightSpark.restoreFactoryDefaults();

        leftActuator = new Servo(0);
        rightActuator = new Servo(0);
    }

    @Override
    public void updateDashboard() {

    }

    public void setReference(double tempPos, RobotSide side) {
        if (side == RobotSide.LEFT) {
            leftSpark.getPIDController().setReference(tempPos, ControlType.kPosition);
        } else {
            rightSpark.getPIDController().setReference(tempPos, ControlType.kPosition);
        }
    }

    public CANEncoder getEncoder(RobotSide side) {
        if (side == RobotSide.LEFT) {
            return leftSpark.getEncoder();
        } else {
            return rightSpark.getEncoder();
        }
    }

    public double getEncoderTicks(RobotSide side) {
        if (side == RobotSide.LEFT) {
            return leftSpark.getEncoder().getPosition();
        } else {
            return rightSpark.getEncoder().getPosition();
        }
    }


    public void setSpeed(double speed, RobotSide side) {
        if (side == RobotSide.LEFT) {
            leftSpark.set(speed);
        } else {
            rightSpark.set(speed);
        }
    }
    public void unlockWinch(RobotSide side){
        if(side == RobotSide.LEFT){
            leftActuator.set(1);
        } else {
            rightActuator.set(1);
        }
    }
    public void lockWinch(RobotSide side){
        if(side == RobotSide.LEFT){
            leftActuator.set(0);
        } else {
            rightActuator.set(0);
        }
    }

}

