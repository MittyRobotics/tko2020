package com.github.mittyrobotics.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.OI;
import com.github.mittyrobotics.conveyor.Constants;
import com.github.mittyrobotics.conveyor.ConveyorSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private WPI_TalonSRX talon2;
    private DigitalInput intakesensor;

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

        talon2 = new WPI_TalonSRX(Constants2.Talon2ID);
        intakesensor = new DigitalInput (Constants2.DigitalInputID);
    }
    public void IntakeBall(){
        if(ConveyorSubsystem.getInstance().totalBallCount < 5){
            talon2.set(ControlMode.Velocity, Constants2.Intakespeed);
        }

    }
    public DigitalInput getintakesensor(){
        return intakesensor;
    }

}
