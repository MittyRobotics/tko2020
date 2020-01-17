package com.github.mittyrobotics.turret;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.shooter.Constants;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.DigitalGlitchFilter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private WPI_TalonSRX talon1;
    private DigitalInput limitSwitch;

    private static TurretSubsystem instance;
    public static TurretSubsystem getInstance(){
        if(instance == null){
            instance = new TurretSubsystem();
        }
        return instance;
    }
    public TurretSubsystem(){
        super();
        setName("Turret");
    }

    public void initHardware(){
        talon1 = new WPI_TalonSRX(com.github.mittyrobotics.turret.Constants.TalonID);
        talon1.config_kP(0, com.github.mittyrobotics.turret.Constants.TurretP);
        talon1.config_kI(0, com.github.mittyrobotics.turret.Constants.TurretI);
        talon1.config_kD(0, com.github.mittyrobotics.turret.Constants.TurretD);
        limitSwitch = new DigitalInput(3);
    }

    public void setPosition(int position){
        talon1.setSelectedSensorPosition(position);
    }

    public WPI_TalonSRX getTalon(){
        return talon1;
    }
    public DigitalInput getLimitSwitch(){ return limitSwitch; }
}
