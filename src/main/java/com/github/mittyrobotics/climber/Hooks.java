package com.github.mittyrobotics.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hooks extends SubsystemBase {
    private static Hooks ourInstance = new Hooks();
    private DoubleSolenoid leftPiston, rightPiston;

    private Hooks(){
        super();
    }

    public static Hooks getInstance() {
        return ourInstance;
    }

    public void initHardware(){
        leftPiston = new DoubleSolenoid(Constants.LEFT_PISTON_FORWARD_ID, Constants.LEFT_PISTON_REVERSE_ID);
        rightPiston = new DoubleSolenoid(Constants.RIGHT_PISTON_FORWARD_ID, Constants.RIGHT_PISTON_REVERSE_ID);
    }

    public void push(RobotSide side, PistonValue value) {
        if (side == RobotSide.LEFT) {
            if (value == PistonValue.UP) {
                leftPiston.set(DoubleSolenoid.Value.kForward);
            } else if (value == PistonValue.DOWN) {
                leftPiston.set(DoubleSolenoid.Value.kReverse);
            } else if (value == PistonValue.OFF) {
                leftPiston.set(DoubleSolenoid.Value.kOff);
            }
        }
        else if (side == RobotSide.RIGHT) {
            if (value == PistonValue.UP) {
                rightPiston.set(DoubleSolenoid.Value.kForward);
            } else if (value == PistonValue.DOWN) {
                rightPiston.set(DoubleSolenoid.Value.kReverse);
            } else if (value == PistonValue.OFF) {
                rightPiston.set(DoubleSolenoid.Value.kOff);
            }
        }
    }
}
