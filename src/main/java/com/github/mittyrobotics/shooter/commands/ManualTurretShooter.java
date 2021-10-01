package com.github.mittyrobotics.shooter.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ManualTurretShooter extends ParallelCommandGroup {

    public ManualTurretShooter() {
        addCommands(new ManualTurretCommand(), new ChangeManualShooterSetpoint());
    }
}
