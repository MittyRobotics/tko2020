package com.github.mittyrobotics.intake;

import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakePiston extends SubsystemBase implements ISubsystem {
    private static IntakePiston instance;
    private DoubleSolenoid intakePiston;
    public static IntakePiston getInstance(){
        if(instance == null){
            instance = new IntakePiston();
        }
        return instance;
    }
    private IntakePiston(){
        super();
        setName("Intake Piston");
    }

    @Override
    public void updateDashboard() {

    }

    @Override
    public void initHardware() {
        intakePiston = new DoubleSolenoid(Constants.SOLENOID_FORWQRD_CHANNEL, Constants.SOLENOID_REVERSE_CHANNEL);
    }
    public boolean isExtended(){
        return intakePiston.get() == DoubleSolenoid.Value.kForward;
    }

    public void extendIntake(){
        intakePiston.set(DoubleSolenoid.Value.kForward);
    }

    public void retractIntake(){
        intakePiston.set(DoubleSolenoid.Value.kReverse);
    }
}
