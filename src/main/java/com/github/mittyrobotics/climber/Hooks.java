package com.github.mittyrobotics.climber;

import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hooks extends SubsystemBase implements ISubsystem {
    private static Hooks ourInstance = new Hooks();
    private DoubleSolenoid leftPiston, rightPiston;

    private Hooks() {
        super();
    }

    public static Hooks getInstance() {
        return ourInstance;
    }

    @Override
    public void initHardware() {
        leftPiston = new DoubleSolenoid(Constants.LEFT_PISTON_FORWARD_ID, Constants.LEFT_PISTON_REVERSE_ID);
        rightPiston = new DoubleSolenoid(Constants.RIGHT_PISTON_FORWARD_ID, Constants.RIGHT_PISTON_REVERSE_ID);
    }

    @Override
    public void updateDashboard() {

    }

    public void pushHooks(RobotSide side) {
        if (side == RobotSide.LEFT) {
            leftPiston.set(DoubleSolenoid.Value.kForward);
        } else {
            rightPiston.set(DoubleSolenoid.Value.kForward);
        }
    }

    public void pushHooks() {
        pushHooks(RobotSide.LEFT);
        pushHooks(RobotSide.RIGHT);
    }

    public void pullHooks(RobotSide side) {
        if (side == RobotSide.LEFT) {
            leftPiston.set(DoubleSolenoid.Value.kReverse);
        } else {
            rightPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void pullHooks() {
        pullHooks(RobotSide.LEFT);
        pullHooks(RobotSide.RIGHT);
    }

    public DoubleSolenoid.Value getSolenoidValue() {
        return leftPiston.get();
    }


}
