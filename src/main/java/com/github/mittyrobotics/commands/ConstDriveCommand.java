package com.github.mittyrobotics.commands;

import com.github.mittyrobotics.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ConstDriveCommand extends InstantCommand {
    public ConstDriveCommand(double leftSpeed, double rightSpeed){
        super(() -> DriveTrainSubsystem.getInstance().tankDrive(leftSpeed, rightSpeed), DriveTrainSubsystem.getInstance());
    }

    @Override
    public void end(boolean interrupted){
        DriveTrainSubsystem.getInstance().tankDrive(0, 0);
    }
}