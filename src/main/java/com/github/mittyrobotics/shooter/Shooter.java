package com.github.mittyrobotics.shooter;


import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Joystick;
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
    private Shooter(){
        super();
        setName("Shooter");
    }

    public void initHardware(){
        spark1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        spark1.restoreFactoryDefaults();
    }

    private void ManualControl(double speed){
        if(Math.abs(speed) > 0.05){
            spark1.set(speed);
        }
        else{
            spark1.stopMotor();
        }

    }
    public CANSparkMax getspark1(){
        return spark1;
    }
}
