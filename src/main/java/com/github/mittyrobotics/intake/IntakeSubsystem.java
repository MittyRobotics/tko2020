package com.github.mittyrobotics.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private WPI_TalonSRX talon2;
    private DigitalInput ballSensor;

    private static IntakeSubsystem instance;
    public static IntakeSubsystem getInstance(){
        if(instance == null){
            instance = new IntakeSubsystem();
        }
        return instance;
    }
    private IntakeSubsystem(){
        super();
        setName("Intake");
    }

    public void initHardware(){

        talon2 = new WPI_TalonSRX(Constants.Talon2ID);
        ballSensor = new DigitalInput (Constants.DigitalInputID);
    }
    public void intakeBall(){
        if(ConveyorSubsystem.getInstance().getTotalBallCount() < 5){
            talon2.set(ControlMode.Velocity, Constants.Intakespeed);
        }

    }
    public boolean hasBall(){
        return ballSensor.get();
    }

}
