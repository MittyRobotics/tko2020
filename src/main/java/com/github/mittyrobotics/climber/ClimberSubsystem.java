package com.github.mittyrobotics.climber;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ClimberSubsystem extends SubsystemBase implements IMotorSubsystem {

    private static ClimberSubsystem instance;

    private PWM actuatorLock;
    private WPI_TalonSRX motor;
    private boolean drumUnlocked = false;

    public ClimberSubsystem() {
        setName("ClimberSubsystem");
    }

    public static ClimberSubsystem getInstance() {
        if(instance == null) {
            instance = new ClimberSubsystem();
        }
        return instance;
    }

    public void initHardware() {
        actuatorLock = new PWM(ClimberConstants.ACTUATOR_ID);

        motor = new WPI_TalonSRX(ClimberConstants.MOTOR_ID);
        motor.configFactoryDefault();

        motor.setNeutralMode(ClimberConstants.CLIMBER_NEUTRAL_MODE);
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        resetEncoder();

        brake();

    }

    public void resetEncoder() {
        motor.setSelectedSensorPosition(0);
    }


    public double getPosition() {
        return motor.getSelectedSensorPosition();
    }

    @Override
    public void overrideSetMotor(double percent) {
        if(drumUnlocked) {
            motor.set(percent);
        } else {
            brake();
        }
    }

    @Override
    public void updateDashboard() {

    }

    public void brake() {
        motor.set(0);
    }

    public void unlockDrum() {
        drumUnlocked = true;
        actuatorLock.setRaw(1);
    }

    public void lockDrum() {
        drumUnlocked = false;
        actuatorLock.setRaw(-1);
    }

}