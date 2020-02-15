package com.github.mittyrobotics.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.github.mittyrobotics.interfaces.ISubsystem;
import com.github.mittyrobotics.conveyor.Conveyor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase implements ISubsystem {
    private static Intake instance;
    private WPI_TalonSRX intakeWheel;
    private DoubleSolenoid extendIntake;

    private Intake() {
        super();
        setName("Intake");
    }

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    @Override
    public void initHardware() {

        intakeWheel = new WPI_TalonSRX(Constants.Talon2ID);
        extendIntake = new DoubleSolenoid(Constants.solenoidForwqrdChannel, Constants.solenoidReverseChallenge);

    }

    @Override
    public void updateDashboard() {

    }

    public void intakeBall(double speed) {
        intakeWheel.set(ControlMode.Velocity, speed);

    }

    public void extendIntake() {
        extendIntake.set(DoubleSolenoid.Value.kForward);
    }

    public void retractIntake() {
        extendIntake.set(DoubleSolenoid.Value.kReverse);
    }

}
