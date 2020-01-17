package com.github.mittyrobotics.turret;


import com.github.mittyrobotics.shooter.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private CANSparkMax spark1;


    private static TurretSubsystem instance;
    public static TurretSubsystem getInstance(){
        if(instance == null){
            instance = new TurretSubsystem();
        }
        return instance;
    }
    public TurretSubsystem(){
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

    public void resetEncoder(){

    }

    public void setShooterSpeed(double speed){
        spark1.getPIDController().setReference(speed, ControlType.kVelocity);
    }

    public CANSparkMax getSpark1(){
        return spark1;
    }
}
