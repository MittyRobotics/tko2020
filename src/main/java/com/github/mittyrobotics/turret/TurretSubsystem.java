package com.github.mittyrobotics.turret;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {

    private static TurretSubsystem instance;
    private WPI_TalonSRX talon1;
    private DigitalInput limitSwitch, limitSwitch2;

    private TurretSubsystem() {
        super();
        setName("Turret");
    }

    public static TurretSubsystem getInstance() {
        if (instance == null) {
            instance = new TurretSubsystem();
        }
        return instance;
    }

    public void initHardware() {
        talon1 = new WPI_TalonSRX(Constants.TalonID);
        talon1.config_kP(0, Constants.TurretP);
        talon1.config_kI(0, Constants.TurretI);
        talon1.config_kD(0, Constants.TurretD);
//        limitSwitch = new DigitalInput(Constants.TurretSwitchID);
//        limitSwitch2 = new DigitalInput(Constants.TurretSwitch2ID);
        talon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        //TODO maybe this will fix it:
//        talon1.configFeedbackNotContinuous(true, 0);

    }

    public void setAngle(double angle) {
        talon1.set(ControlMode.Position, angle*Constants.TICKS_PER_ANGLE);
//
//        if (!limitSwitch.get() && !limitSwitch2.get()) {
//            talon1.set(ControlMode.Position, angle*Constants.TICKS_PER_ANGLE);
//        } else {
//            if ((angle >= -90) && (angle <= 90)) {
//                talon1.set(ControlMode.Position, angle*Constants.TICKS_PER_ANGLE);
//            } else {
//                talon1.set(ControlMode.PercentOutput, 0);
//            }
//        }
    }
    public void changeAngle(double angle){
        setAngle(angle + getAngle());
    }

    public void setTurretSpeed(double speed) {
        if (!limitSwitch.get() && !limitSwitch2.get()) {
            talon1.set(ControlMode.PercentOutput, speed);
        } else {
            if (limitSwitch.get()) {
                if (speed > 0) {
                    talon1.set(ControlMode.PercentOutput, speed);
                } else {
                    talon1.set(ControlMode.PercentOutput, 0);
                }
            } else {
                if (speed < 0) {
                    talon1.set(ControlMode.PercentOutput, speed);
                } else {
                    talon1.set(ControlMode.PercentOutput, 0);
                }
            }
        }
    }
    public double getAngle(){
        return talon1.getSelectedSensorPosition() / Constants.TICKS_PER_ANGLE;
    }

    public void manualSetTurret(double speed) {
        if (Math.abs(speed)>0.05) {
            talon1.set(ControlMode.PercentOutput, speed);
            //System.out.println(talon1.getSelectedSensorVelocity());
//            System.out.println("pos: "+ talon1.getSelectedSensorPosition());
        } else {
            talon1.set(ControlMode.PercentOutput, 0);
        }
    }

    public void zeroEncoder() {
//        talon1.setSelectedSensorPosition(0);
    }

    public double getEncoderValue() {
        return talon1.getSelectedSensorPosition();
    }

    public boolean limitSwitchValue() {
        return limitSwitch.get();
    }
    public double getError(){
        return (talon1.getClosedLoopTarget() - talon1.getSelectedSensorPosition())/Constants.TICKS_PER_ANGLE;
    }

}
