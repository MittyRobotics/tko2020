package com.github.mittyrobotics.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopFlywheel extends CommandBase {
    public StopFlywheel() {
        addRequirements(Shooter.getInstance());
    }

    @Override
    public void initialize() {
        Shooter.getInstance().setShooterPercent(0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
