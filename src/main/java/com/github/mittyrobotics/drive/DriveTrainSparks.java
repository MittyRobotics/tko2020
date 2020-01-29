package com.github.mittyrobotics.drive;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrainSparks extends SubsystemBase {

    public CANSparkMax leftSpark1;
    public CANSparkMax leftSpark2;

    public CANSparkMax rightSpark1;
    public CANSparkMax rightSpark2 ;


    private static DriveTrainSparks instance;
    public static DriveTrainSparks getInstance(){
        if(instance == null){
            instance = new DriveTrainSparks();
        }
        return instance;
    }

    private DriveTrainSparks(){
        super();
        setName("DriveTrainSparks");

    }

    @Override
    public void periodic(){

    }

    public void initHardware(){
        leftSpark1 = new CANSparkMax(Constants.LEFT_SPARK_1_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSpark2 = new CANSparkMax(Constants.LEFT_SPARK_2_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        rightSpark1 = new CANSparkMax(Constants.RIGHT_SPARK_1_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark2 = new CANSparkMax(Constants.RIGHT_SPARK_2_ID, CANSparkMaxLowLevel.MotorType.kBrushless);


        leftSpark1.restoreFactoryDefaults();
        leftSpark2.restoreFactoryDefaults();
        rightSpark1.restoreFactoryDefaults();
        rightSpark2.restoreFactoryDefaults();
        rightSpark1.follow(leftSpark1);
        rightSpark2.follow(rightSpark1);
        leftSpark1.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSpark2.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark1.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark2.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark1.setInverted(true);
        rightSpark2.setInverted(true);
        setDefaultCommand(new Drive());
    }

    public void tankDrive(double left, double right) {
        if (Math.abs(left) < 0.1){
            leftSpark1.stopMotor();
            leftSpark2.stopMotor();
        } else {
            leftSpark1.set(left);
            leftSpark2.set(left);
        }

        if (Math.abs(right) < 0.1){
            rightSpark1.stopMotor();
            rightSpark2.stopMotor();
        } else {
            rightSpark1.set(right);
            rightSpark2.set(right);
        }
    }

    public CANSparkMax getLeftSpark(){
        return leftSpark1;
    }

    public CANEncoder getLeftEncoder(){
        return leftSpark1.getEncoder();
    }
}