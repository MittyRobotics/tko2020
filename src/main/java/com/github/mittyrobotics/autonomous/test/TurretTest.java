package com.github.mittyrobotics.autonomous.test;

import com.github.mittyrobotics.shooter.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretTest extends CommandBase {

    double speed;

    public TurretTest(double speed) {
        this.speed = speed;
    }

    @Override
    public void initialize() {
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void execute() {
        TurretSubsystem.getInstance().setMotor(speed);
    }

    @Override
    public void end(boolean interrupted) {
        TurretSubsystem.getInstance().stopMotor();
    }

    @Override
    public boolean isFinished() {
        return (TurretSubsystem.getInstance().getLeftSwitch() && speed < 0) ||
                (TurretSubsystem.getInstance().getRightSwitch() && speed > 0);
    }
}
