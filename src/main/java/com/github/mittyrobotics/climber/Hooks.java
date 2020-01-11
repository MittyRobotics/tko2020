package com.github.mittyrobotics.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hooks extends SubsystemBase {
    private static Hooks ourInstance = new Hooks();
    private DoubleSolenoid leftPiston, rightPiston;

    public Hooks(){
        super();
    }
    public static Hooks getInstance() {
        return ourInstance;
    }

    public void initHardware(){
        leftPiston = new DoubleSolenoid(Constants.LEFT_PISTON_FORWARD_ID, Constants.LEFT_PISTON_REVERSE_ID);
        rightPiston = new DoubleSolenoid(Constants.RIGHT_PISTON_FORWARD_ID, Constants.RIGHT_PISTON_REVERSE_ID);
    }

    public void pushRightUp(){
        rightPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void pushRightDown(){
        rightPiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void pushLeftUp(){
        leftPiston.set(DoubleSolenoid.Value.kForward);
    }

    public void pushLeftDown(){
        leftPiston.set(DoubleSolenoid.Value.kReverse);
    }
}
