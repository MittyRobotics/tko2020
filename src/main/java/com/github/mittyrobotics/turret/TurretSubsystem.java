package com.github.mittyrobotics.turret;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private WPI_TalonSRX talon1;
    private DigitalInput limitSwitch, limitSwitch2;


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
        if (limitSwitch.get()==true && limitSwitch2.get()==true) {
            talon1.setSelectedSensorPosition(position);
        } else {
            if ((talon1.getClosedLoopTarget() >= -90) && (talon1.getClosedLoopTarget() <= 90)) {
                talon1.setSelectedSensorPosition(position);
            } else {
                talon1.set(ControlMode.PercentOutput, 0);
            }
        }
    }

    public void setTurretSpeed(double speed) {
        if (!limitSwitch.get() && !limitSwitch2.get()) {
            talon1.set(ControlMode.PercentOutput, speed);
        } else {
            if(limitSwitch.get()){
                if (speed > 0) {
                    talon1.set(ControlMode.PercentOutput, speed);
                }
                else{
                    talon1.set(ControlMode.PercentOutput, 0);
                }
            }
            else{
                if(speed < 0){
                    talon1.set(ControlMode.PercentOutput, speed);
                }
                else{
                    talon1.set(ControlMode.PercentOutput, 0);
                }
            }
        }
    }
    public boolean limitSwitchValue() { return limitSwitch.get();}

}
