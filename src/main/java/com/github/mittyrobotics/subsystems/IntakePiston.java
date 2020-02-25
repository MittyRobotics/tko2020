package com.github.mittyrobotics.subsystems;

import com.github.mittyrobotics.constants.IntakeConstants;
import com.github.mittyrobotics.util.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakePiston extends SubsystemBase implements ISubsystem {
    private static IntakePiston instance;
    private DoubleSolenoid intakePiston;

    private IntakePiston() {
        super();
        setName("Intake Piston");
    }

    public static IntakePiston getInstance() {
        if (instance == null) {
            instance = new IntakePiston();
        }
        return instance;
    }

    @Override
    public void updateDashboard() {

    }

    @Override
    public void initHardware() {
        intakePiston = new DoubleSolenoid(IntakeConstants.SOLENOID_FORWQRD_CHANNEL, IntakeConstants.SOLENOID_REVERSE_CHANNEL);
    }

    public boolean isExtended() {
        return intakePiston.get() == DoubleSolenoid.Value.kForward;
    }

    public void extendIntake() {
        intakePiston.set(DoubleSolenoid.Value.kForward);
    }

    public void retractIntake() {
        intakePiston.set(DoubleSolenoid.Value.kReverse);
    }
}
