package com.github.mittyrobotics.shooter;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private CANSparkMax spark1;


    private static ShooterSubsystem instance;
    public static ShooterSubsystem getInstance(){
        if(instance == null){
            instance = new ShooterSubsystem();
        }
        return instance;
    }
    private ShooterSubsystem(){
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

    //TODO build another function that used bang-bang control (we will test both to see which works better)
    public void setShooterSpeed(double speed){ //in rpm of the motor
        spark1.getPIDController().setReference(speed, ControlType.kVelocity);
    }

}
