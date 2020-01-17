package com.github.mittyrobotics.turret;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private WPI_TalonSRX talon1;
    private DigitalInput limitSwitch; //TODO will have two limit switches, unsure if they are plugged in through roborio though

    private static TurretSubsystem instance;
    public static TurretSubsystem getInstance(){
        if(instance == null){
            instance = new TurretSubsystem();
        }
        return instance;
    }
    //TODO make this private
    public TurretSubsystem(){
        super();
        setName("Turret");
    }

    public void initHardware(){
        talon1 = new WPI_TalonSRX(Constants.TalonID);
        talon1.config_kP(0, Constants.TurretP);
        talon1.config_kI(0, Constants.TurretI);
        talon1.config_kD(0, Constants.TurretD);
        limitSwitch = new DigitalInput(3); //TODO make this a constant
    }

    public void setPosition(int position){
        talon1.setSelectedSensorPosition(position);
    }

    //TODO dont return the talon, make functions for different needs
    public WPI_TalonSRX getTalon(){
        return talon1;
    }
    //TODO dont return the limit switches, make functions for different needs
    public DigitalInput getLimitSwitch(){ return limitSwitch; }
}