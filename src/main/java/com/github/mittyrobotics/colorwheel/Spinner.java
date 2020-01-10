package com.github.mittyrobotics.colorwheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
//Example Subsystem Code
public class Spinner extends SubsystemBase {
    private WPI_TalonSRX talon1;
    private double count = 0;
    //Making it a Singleton
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
        talon1 = new WPI_TalonSRX(talonDeviceNumber);


    }
}