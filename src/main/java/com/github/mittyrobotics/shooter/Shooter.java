package com.github.mittyrobotics.shooter;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    private CANSparkMax spark1;


    private static Shooter instance;
    public static Shooter getInstance(){
        if(instance == null){
            instance = new Shooter();
        }
        return instance;
    }
    public Shooter(){
        super();
        setName("Shooter");
    }

    public void initHardware(){
        spark1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        spark1.restoreFactoryDefaults();
        spark1.getPIDController().setP(Constants.ShooterP);
        spark1.getPIDController().setI(Constants.ShooterI);
        spark1.getPIDController().setD(Constants.ShooterD);
        spark1.getPIDController().setOutputRange(Constants.ShooterOutputMin, Constants.ShooterOutputMax);
    }

    public void manualControl(double speed){
        if(Math.abs(speed) >= 0.05){
            spark1.set(speed);
        }
        else{
            spark1.stopMotor();
        }
    }

    public void setShooterSpeed(double speed){
        spark1.getPIDController().setReference(speed, ControlType.kVelocity);
    }

//    public CANSparkMax getSpark1(){ //TODO return a CANPIDController that uses spark1 instead
//        return spark1;
//    }
}
