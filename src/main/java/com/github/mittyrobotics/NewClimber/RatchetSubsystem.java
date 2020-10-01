package com.github.mittyrobotics.NewClimber;

import com.github.mittyrobotics.constants.ColorWheelConstants;
import com.github.mittyrobotics.subsystems.ColorPistonSubsystem;
import com.github.mittyrobotics.util.interfaces.IMotorSubsystem;
import com.github.mittyrobotics.util.interfaces.IPistonSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RatchetSubsystem extends SubsystemBase {
    private static RatchetSubsystem instance;
    private DoubleSolenoid piston1, piston2;

    private RatchetSubsystem() {
        super();
        setName("Ratchet");
    }

    public static RatchetSubsystem getInstance() {
        if (instance == null) {
            instance = new RatchetSubsystem();
        }
        return instance;
    }

    public void initHardware() {
        // TODO CHANGE THE COLOR WHEEL CONSTANTS
        piston1 = new DoubleSolenoid(ClimberConstants.SOLENOID_FOWARD_CHANNEL,
                ClimberConstants.SOLENOID_REVERSE_CHANNEL);
        piston2 = new DoubleSolenoid(ClimberConstants.SOLENOID_FOWARD_CHANNEL,
                ClimberConstants.SOLENOID_REVERSE_CHANNEL);
    }

    /*@Override
    public void updateDashboard() {
        SmartDashboard.putBoolean("Color Spinner Up", isPistonExtended());
    }*/

    public void extendPiston1() { piston1.set(DoubleSolenoid.Value.kReverse); }

    public void extendPiston2() { piston2.set(DoubleSolenoid.Value.kReverse); }

    public void retractPiston1() {
        piston1.set(DoubleSolenoid.Value.kForward);
    }
    public void retractPiston2() {
        piston2.set(DoubleSolenoid.Value.kForward);
    }

    public void lockWinch() {
        extendPiston1();
        extendPiston2();
    }

    public void unlockWinch() {
        retractPiston1();
        retractPiston2();
    }

    //same thing as extended
    public boolean isPiston1Locked() {
        return piston1.get() != DoubleSolenoid.Value.kForward;
    }
    public boolean isPiston2Locked() {
        return piston2.get() != DoubleSolenoid.Value.kForward;
    }

    //TODO DETERMINE IF THIS ACTUALLY MAKES IT LOCKED
    public boolean isWinchLocked() { return (isPiston1Locked() && isPiston2Locked()); }

}
