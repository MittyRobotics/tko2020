package com.github.mittyrobotics.turret;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private WPI_TalonSRX talon1;
    private DigitalInput limitSwitch, limitSwitch2; //TODO will have two limit switches, unsure if they are plugged in through roborio though


    private static TurretSubsystem instance;
    public static TurretSubsystem getInstance(){
        if(instance == null){
            instance = new TurretSubsystem();
        }
        return instance;
    }
    private TurretSubsystem(){
        super();
        setName("Turret");
    }

    public void initHardware(){
        talon1 = new WPI_TalonSRX(Constants.TalonID);
        talon1.config_kP(0, Constants.TurretP);
        talon1.config_kI(0, Constants.TurretI);
        talon1.config_kD(0, Constants.TurretD);
        limitSwitch = new DigitalInput(Constants.TurretSwitchID);
        limitSwitch2 = new DigitalInput(Constants.TurretSwitch2ID);
    }

    public void setPosition(int position){
        talon1.setSelectedSensorPosition(position);
    }

    public void setTurretSpeed(double speed) {
        talon1.set(ControlMode.PercentOutput, speed);
    }
    public boolean limitSwitchValue() { return limitSwitch.get();}

}
