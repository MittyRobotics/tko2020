package com.github.mittyrobotics.drive;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrainSparks extends SubsystemBase {

    private CANSparkMax leftSpark1;
    private CANSparkMax leftSpark2;

    private CANSparkMax rightSpark1;
    private CANSparkMax rightSpark2 ;


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
        //setDefaultCommand(new sampleCommand());
    }

    @Override
    public void periodic(){

    }

    public void initHardware(){
        leftSpark1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSpark2 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);

        rightSpark1 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark2 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void tankDrive(double left, double right) {
        if (Math.abs(left) > 0.1){
            leftSpark1.set(left);
            leftSpark2.set(left);
        } else {
            leftSpark1.stopMotor();
            leftSpark2.stopMotor();

        }

        if (Math.abs(right) > 0.1){
            rightSpark1.set(right);
            rightSpark2.set(right);
        } else {
            rightSpark1.stopMotor();
            rightSpark2.stopMotor();
        }
    }

    public CANSparkMax getLeftSpark(){
        return leftSpark1;
    }

    public CANEncoder getLeftEncoder(){
        return leftSpark1.getEncoder();
    }
}