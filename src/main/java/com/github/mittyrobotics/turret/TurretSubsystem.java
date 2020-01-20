package com.github.mittyrobotics.turret;


import com.ctre.phoenix.motorcontrol.ControlMode;
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
        limitSwitch = new DigitalInput(Constants.TurretSwitchID);
        limitSwitch2 = new DigitalInput(Constants.TurretSwitch2ID);
        //TODO: Make sure to configure the talon's encoder. We use a digital absolute encoder for the turret, so to
        // set that use talon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute)
    }

    //TODO: I am assuming you want setPosition to set the ANGLE of the turret instead of the encoder position, to do
    // that make sure to scale everything by a "ticks per angle" constant (that can be figured out later, just have a
    // placeholder for now) to get from angles to encoder ticks. The talon takes in encoder ticks, so if you pass in
    // angle it won't work.
    public void setPosition(int position) {
        if (limitSwitch.get() && limitSwitch2.get()) { //TODO shouldn't it be !limistSwitch.get() & !limitSwitch2.get()?
            //TODO: talon1.setSelectedSensorPosition() just calibrates the value of the encoder... it doesn't actually move
            // anything. In order to move the talon, do talon1.set(ControlMode.Position, position).
            // Remember, make sure to multiply position by a "ticks per angle" constant!
            talon1.setSelectedSensorPosition(position);
        } else {
            //TODO: talon1.getClosedLoopTarget() returns the current setpoint of the talon, it seems like what you
            // want here is to just check if -90 < position < 90
            if ((talon1.getClosedLoopTarget() >= -90) && (talon1.getClosedLoopTarget() <= 90)) {
                //TODO: talon1.setSelectedSensorPosition() just calibrates the value of the encoder... it doesn't
                // actually move anything. In order to move the talon, do talon1.set(ControlMode.Position, position).
                // Remember, make sure to multiply position by a "ticks per angle" constant!
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

    public boolean limitSwitchValue() {
        return limitSwitch.get();
    }

}
