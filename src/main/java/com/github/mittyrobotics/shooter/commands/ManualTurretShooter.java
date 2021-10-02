package com.github.mittyrobotics.shooter.commands;

import com.github.mittyrobotics.util.OI;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ManualTurretShooter extends ParallelCommandGroup {

    public ManualTurretShooter() {
        addCommands(new ManualTurretCommand(), new ChangeManualShooterSetpoint());
    }

    @Override
    public boolean isFinished() {
        return !OI.getInstance().getXboxController2().getAButton();
    }
}
