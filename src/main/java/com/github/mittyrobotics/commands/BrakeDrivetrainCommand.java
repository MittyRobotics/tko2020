package com.github.mittyrobotics.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.github.mittyrobotics.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BrakeDrivetrainCommand extends CommandBase {
    public BrakeDrivetrainCommand() {
        addRequirements(DriveTrainSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        DriveTrainSubsystem.getInstance().setNeutralMode(NeutralMode.Brake);
        DriveTrainSubsystem.getInstance().tankDrive(0, 0);
    }

    @Override
    public void end(boolean interrupted) {
        DriveTrainSubsystem.getInstance().setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
