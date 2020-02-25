package com.github.mittyrobotics.subsystems;

import com.github.mittyrobotics.constants.ClimberConstants;
import com.github.mittyrobotics.constants.RobotSide;
import com.github.mittyrobotics.interfaces.ISubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HooksSubsystem extends SubsystemBase implements ISubsystem {
    private static HooksSubsystem ourInstance = new HooksSubsystem();
    private DoubleSolenoid leftPiston, rightPiston;

    private HooksSubsystem() {
        super();
    }

    public static HooksSubsystem getInstance() {
        return ourInstance;
    }

    @Override
    public void initHardware() {
        leftPiston = new DoubleSolenoid(ClimberConstants.LEFT_PISTON_FORWARD_ID, ClimberConstants.LEFT_PISTON_REVERSE_ID);
        rightPiston = new DoubleSolenoid(ClimberConstants.RIGHT_PISTON_FORWARD_ID, ClimberConstants.RIGHT_PISTON_REVERSE_ID);
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
