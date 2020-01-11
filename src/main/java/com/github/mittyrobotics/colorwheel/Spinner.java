package com.github.mittyrobotics.colorwheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.github.mittyrobotics.colorwheel.Constants.*;


public class Spinner extends SubsystemBase {
    private WPI_TalonSRX talon1;
    private static Spinner instance;

    public static Spinner getInstance() {
        if (instance == null) {
            instance = new Spinner();
        }
        return instance;
    }

    private Spinner() {
        super();
        setName("Spinner");
    }

    //Function that runs continuously regardless of any commands being run
    @Override
    public void periodic() {

    }

    //Function to initialize the hardware
    public void initHardware() {
        talon1 = new WPI_TalonSRX(TALON_DEVICE_NUMBER);
    }

    public void setMotorFast() {
        talon1.set(ControlMode.Velocity, FAST_VELOCITY);

    }

    public void setMotorSlow() {
        talon1.set(ControlMode.Velocity, SLOW_VELOCITY);

    }

    public void setMotorOff() {
        talon1.set(ControlMode.PercentOutput, 0);
    }
}